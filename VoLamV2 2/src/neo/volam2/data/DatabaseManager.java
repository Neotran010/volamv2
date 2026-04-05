package neo.volam2.data;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public class DatabaseManager {

    private final JavaPlugin plugin;
    private Connection connection;

    public DatabaseManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        try {
            File dataFolder = plugin.getDataFolder();
            if (!dataFolder.exists()) dataFolder.mkdirs();
            String url = "jdbc:sqlite:" + dataFolder.getAbsolutePath() + "/playerdata.db";
            connection = DriverManager.getConnection(url);
            createTables();
            plugin.getLogger().info("SQLite database connected successfully.");
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not connect to SQLite database!", e);
        }
    }

    private void createTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS player_data (" +
                "uuid TEXT PRIMARY KEY, " +
                "player_name TEXT, " +
                "toc_he TEXT, " +
                "mon_phai TEXT, " +
                "nhanh TEXT, " +
                "level INTEGER DEFAULT 1, " +
                "exp INTEGER DEFAULT 0, " +
                "diem_tiem_nang_add INTEGER DEFAULT 0, " +
                "suc_manh INTEGER DEFAULT 0, " +
                "than_phap INTEGER DEFAULT 0, " +
                "noi_cong INTEGER DEFAULT 0, " +
                "the_luc INTEGER DEFAULT 0, " +
                "diem_ky_nang_add INTEGER DEFAULT 0, " +
                "trigger_choose_class INTEGER DEFAULT 0" +
                ")"
            );
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS player_skills (" +
                "uuid TEXT, " +
                "skill_name TEXT, " +
                "skill_level INTEGER DEFAULT 0, " +
                "PRIMARY KEY (uuid, skill_name)" +
                ")"
            );
        }

        // Migration: add exp column if not exists (for existing databases)
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("ALTER TABLE player_data ADD COLUMN exp INTEGER DEFAULT 0");
        } catch (SQLException ignored) {
            // Column already exists
        }
    }

    public PlayerData loadPlayerData(UUID uuid, String playerName) {
        PlayerData data = new PlayerData(uuid, playerName);
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM player_data WHERE uuid = ?")) {
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                data.setPlayerName(rs.getString("player_name"));
                String tocHeStr = rs.getString("toc_he");
                if (tocHeStr != null && !tocHeStr.isEmpty()) {
                    try { data.setTocHe(TocHe.valueOf(tocHeStr)); } catch (Exception ignored) {}
                }
                String monPhaiStr = rs.getString("mon_phai");
                if (monPhaiStr != null && !monPhaiStr.isEmpty()) {
                    try { data.setMonPhai(MonPhai.valueOf(monPhaiStr)); } catch (Exception ignored) {}
                }
                String nhanhStr = rs.getString("nhanh");
                if (nhanhStr != null && !nhanhStr.isEmpty()) {
                    try { data.setNhanh(Nhanh.valueOf(nhanhStr)); } catch (Exception ignored) {}
                }
                data.setLevel(rs.getInt("level"));
                data.setExp(rs.getInt("exp"));
                data.setDiemTiemNangAdd(rs.getInt("diem_tiem_nang_add"));
                data.setSucManh(rs.getInt("suc_manh"));
                data.setThanPhap(rs.getInt("than_phap"));
                data.setNoiCong(rs.getInt("noi_cong"));
                data.setTheLuc(rs.getInt("the_luc"));
                data.setDiemKyNangAdd(rs.getInt("diem_ky_nang_add"));
                data.setTriggerChooseClass(rs.getInt("trigger_choose_class") == 1);
            }

            // Load skills
            try (PreparedStatement ps2 = connection.prepareStatement(
                    "SELECT skill_name, skill_level FROM player_skills WHERE uuid = ?")) {
                ps2.setString(1, uuid.toString());
                ResultSet rs2 = ps2.executeQuery();
                Map<String, Integer> skills = new HashMap<>();
                while (rs2.next()) {
                    skills.put(rs2.getString("skill_name"), rs2.getInt("skill_level"));
                }
                data.setSkillLevels(skills);
            }

            data.setDirty(false);
        } catch (SQLException e) {
            plugin.getLogger().log(Level.WARNING, "Could not load player data for " + uuid, e);
        }
        return data;
    }

    public void savePlayerData(PlayerData data) {
        try {
            // Upsert player_data
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT OR REPLACE INTO player_data " +
                    "(uuid, player_name, toc_he, mon_phai, nhanh, level, exp, " +
                    "diem_tiem_nang_add, suc_manh, than_phap, noi_cong, the_luc, " +
                    "diem_ky_nang_add, trigger_choose_class) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                ps.setString(1, data.getUuid().toString());
                ps.setString(2, data.getPlayerName());
                ps.setString(3, data.getTocHe() != null ? data.getTocHe().name() : null);
                ps.setString(4, data.getMonPhai() != null ? data.getMonPhai().name() : null);
                ps.setString(5, data.getNhanh() != null ? data.getNhanh().name() : null);
                ps.setInt(6, data.getLevel());
                ps.setInt(7, data.getExp());
                ps.setInt(8, data.getDiemTiemNangAdd());
                ps.setInt(9, data.getSucManh());
                ps.setInt(10, data.getThanPhap());
                ps.setInt(11, data.getNoiCong());
                ps.setInt(12, data.getTheLuc());
                ps.setInt(13, data.getDiemKyNangAdd());
                ps.setInt(14, data.isTriggerChooseClass() ? 1 : 0);
                ps.executeUpdate();
            }

            // Save skills: delete old + insert new
            try (PreparedStatement psDel = connection.prepareStatement(
                    "DELETE FROM player_skills WHERE uuid = ?")) {
                psDel.setString(1, data.getUuid().toString());
                psDel.executeUpdate();
            }
            if (!data.getSkillLevels().isEmpty()) {
                try (PreparedStatement psIns = connection.prepareStatement(
                        "INSERT INTO player_skills (uuid, skill_name, skill_level) VALUES (?, ?, ?)")) {
                    for (Map.Entry<String, Integer> entry : data.getSkillLevels().entrySet()) {
                        psIns.setString(1, data.getUuid().toString());
                        psIns.setString(2, entry.getKey());
                        psIns.setInt(3, entry.getValue());
                        psIns.addBatch();
                    }
                    psIns.executeBatch();
                }
            }

            data.setDirty(false);
        } catch (SQLException e) {
            plugin.getLogger().log(Level.WARNING, "Could not save player data for " + data.getUuid(), e);
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            plugin.getLogger().log(Level.WARNING, "Could not close SQLite connection", e);
        }
    }
}
