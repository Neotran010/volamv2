package neo.volam2.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import neo.volam2.main.Main;

public class PlayerDataManager {

    private static final Map<UUID, PlayerData> cache = new HashMap<>();
    private static DatabaseManager db;

    public static void init(DatabaseManager database) {
        db = database;
        // Auto-save every 60 minutes (72000 ticks)
        new BukkitRunnable() {
            @Override
            public void run() {
                saveAll();
            }
        }.runTaskTimerAsynchronously(Main.pl, 72000L, 72000L);
    }

    public static PlayerData get(Player p) {
        return get(p.getUniqueId(), p.getName());
    }

    public static PlayerData get(UUID uuid) {
        return cache.get(uuid);
    }

    public static PlayerData get(UUID uuid, String name) {
        PlayerData data = cache.get(uuid);
        if (data == null) {
            data = db.loadPlayerData(uuid, name);
            cache.put(uuid, data);
        }
        data.setPlayerName(name);
        return data;
    }

    public static void load(Player p) {
        PlayerData data = db.loadPlayerData(p.getUniqueId(), p.getName());
        cache.put(p.getUniqueId(), data);
    }

    public static void save(Player p) {
        PlayerData data = cache.get(p.getUniqueId());
        if (data != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    db.savePlayerData(data);
                }
            }.runTaskAsynchronously(Main.pl);
        }
    }

    public static void saveSync(PlayerData data) {
        if (data != null) {
            db.savePlayerData(data);
        }
    }

    public static void saveAll() {
        for (PlayerData data : cache.values()) {
            db.savePlayerData(data);
        }
        Main.pl.getLogger().info("Saved all player data (" + cache.size() + " players).");
    }

    public static void remove(UUID uuid) {
        PlayerData data = cache.get(uuid);
        if (data != null) {
            db.savePlayerData(data);
            cache.remove(uuid);
        }
    }

    public static int getSkillLevel(Player p, String skillName) {
        PlayerData data = get(p);
        if (data == null) return 0;
        return data.getSkillLevel(skillName);
    }
}
