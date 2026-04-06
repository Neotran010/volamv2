package neo.volam2.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import neo.volam2.abilities.SkillInfo;
import neo.volam2.abilities.ThieuLamSkills;
import neo.volam2.data.MonPhai;
import neo.volam2.data.Nhanh;
import neo.volam2.data.PlayerData;
import neo.volam2.data.PlayerDataManager;
import neo.volam2.main.U;

public class KyNangGUI {

    public static final String TITLE = "§0§l✦ §b§lKỹ Năng §0§l✦";

    public static void open(Player p) {
        PlayerData data = PlayerDataManager.get(p);
        if (data == null) return;

        if (!data.hasMonPhai()) {
            p.sendMessage("§c§l[KỸ NĂNG] §cBạn chưa gia nhập môn phái nào!");
            return;
        }

        List<SkillInfo> skills = getSkillsForPlayer(data);
        int size = Math.min(54, ((skills.size() / 9) + 2) * 9);
        if (size < 27) size = 27;

        Inventory inv = Bukkit.createInventory(null, size, TITLE);

        // Fill border
        for (int i = 0; i < size; i++) {
            inv.setItem(i, U.buildItem(Material.BLACK_STAINED_GLASS_PANE, " ", null, false, 1));
        }

        // Info header
        inv.setItem(4, U.buildItem(Material.NETHER_STAR, "§e§lKỹ Năng - " + data.getMonPhai().getDisplayName(),
            Arrays.asList("",
                "§7Nhánh: " + (data.getNhanh() != null ? data.getNhanh().getDisplayName() : "§cMặc định"),
                "§7Điểm kỹ năng: §b" + data.getAvailableDiemKyNang() + "§7/§f" + data.getTotalDiemKyNang(),
                "", "§7Click vào kỹ năng để xem chi tiết/nâng cấp"), false, 1));

        // Place skill items
        int slot = 9;
        for (SkillInfo skill : skills) {
            if (slot >= size - 9) break;
            // Skip border slots
            if (slot % 9 == 0 || slot % 9 == 8) {
                slot++;
                continue;
            }

            int currentLevel = data.getSkillLevel(skill.getId());
            boolean unlocked = data.getLevel() >= skill.getRequiredLevel();

            Material mat;
            if (!unlocked) mat = Material.GRAY_DYE;
            else if (currentLevel > 0) mat = Material.LIME_DYE;
            else mat = Material.YELLOW_DYE;

            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§7Loại: " + skill.getType());
            lore.add("§7Cấp kỹ năng: §f" + currentLevel + "§7/§f10");
            lore.add("§7Cấp yêu cầu: §f" + skill.getRequiredLevel());
            if (skill.getManaCost() > 0) lore.add("§7Nội lực: §b" + skill.getManaCost());
            if (skill.getCooldown() > 0) lore.add("§7Hồi chiêu: §f" + U.lamTronString(skill.getCooldown()) + "s");
            if (skill.getComboKey() != null) {
                lore.add("§7Chiêu thức: " + U.formatComboKey(skill.getComboKey()));
            }
            if (skill.getBranch() != null) {
                lore.add("§7Nhánh: " + skill.getBranch().getDisplayName());
            } else {
                lore.add("§7Nhánh: §fChung (tất cả nhánh)");
            }
            lore.add("");
            for (String desc : skill.getDescription()) {
                lore.add("§7" + desc);
            }
            lore.add("");
            if (!unlocked) {
                lore.add("§c✘ Chưa đủ cấp độ!");
            } else if (currentLevel < 10 && data.getAvailableDiemKyNang() > 0) {
                lore.add("§a✔ Click để nâng cấp!");
            } else if (currentLevel >= 10) {
                lore.add("§6★ Đã đạt cấp tối đa!");
            } else {
                lore.add("§c✘ Không đủ điểm kỹ năng!");
            }

            inv.setItem(slot, U.buildItem(mat, skill.getDisplayName(), lore, currentLevel > 0, 1));
            slot++;
        }

        // Close button
        inv.setItem(size - 5, U.getQuitItem());

        p.openInventory(inv);
    }

    /**
     * Returns skills filtered by the player's current branch.
     * Only shared skills (branch == null) and skills matching the player's branch are included.
     */
    public static List<SkillInfo> getSkillsForPlayer(PlayerData data) {
        List<SkillInfo> allSkills;
        if (data.getMonPhai() == MonPhai.THIEU_LAM) {
            allSkills = ThieuLamSkills.getAllSkillInfos();
        } else {
            return new ArrayList<>();
        }

        Nhanh playerBranch = data.getNhanh();
        List<SkillInfo> filtered = new ArrayList<>();
        for (SkillInfo skill : allSkills) {
            // Include shared skills (branch == null) and skills matching the player's branch
            if (skill.getBranch() == null || skill.getBranch() == playerBranch) {
                filtered.add(skill);
            }
        }
        return filtered;
    }
}
