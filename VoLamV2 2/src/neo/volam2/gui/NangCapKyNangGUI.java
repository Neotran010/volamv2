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
import neo.volam2.data.PlayerData;
import neo.volam2.data.PlayerDataManager;
import neo.volam2.main.U;

public class NangCapKyNangGUI {

    public static final String TITLE_PREFIX = "§0§l✦ §a§lNâng Cấp: ";

    public static void open(Player p, String skillId) {
        PlayerData data = PlayerDataManager.get(p);
        if (data == null) return;

        SkillInfo skill = findSkill(data, skillId);
        if (skill == null) return;

        int currentLevel = data.getSkillLevel(skillId);
        String title = TITLE_PREFIX + skill.getDisplayName() + " §0§l✦";
        // Truncate title if too long
        if (title.length() > 32) {
            title = TITLE_PREFIX + "§a§lKỹ Năng §0§l✦";
        }

        Inventory inv = Bukkit.createInventory(null, 27, title);

        // Fill
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, U.buildItem(Material.BLACK_STAINED_GLASS_PANE, " ", null, false, 1));
        }

        // Skill info - center (with full detail)
        List<String> skillLore = new ArrayList<>();
        skillLore.add("");
        skillLore.add("§7Cấp hiện tại: §f" + currentLevel + "§7/§f10");
        skillLore.add("§7Cấp yêu cầu: §f" + skill.getRequiredLevel());
        skillLore.add("§7Loại: " + skill.getType());
        if (skill.getManaCost() > 0) {
            skillLore.add("§7Mana: §b" + skill.getManaCost());
        }
        if (skill.getCooldown() > 0) {
            skillLore.add("§7Hồi chiêu: §f" + U.lamTronString(skill.getCooldown()) + "s");
        }
        if (skill.getComboKey() != null) {
            skillLore.add("§7Combo: §e" + skill.getComboKey());
        }
        if (skill.getBranch() != null) {
            skillLore.add("§7Nhánh: " + skill.getBranch().getDisplayName());
        } else {
            skillLore.add("§7Nhánh: §fChung (tất cả nhánh)");
        }
        skillLore.add("");
        skillLore.add("§e§lMô tả:");
        for (String desc : skill.getDescription()) {
            skillLore.add("§7 " + desc);
        }
        inv.setItem(4, U.buildItem(Material.ENCHANTED_BOOK, skill.getDisplayName(), skillLore, true, 1));

        // Upgrade button
        boolean canUpgrade = currentLevel < 10 && data.getAvailableDiemKyNang() > 0
            && data.getLevel() >= skill.getRequiredLevel();

        if (canUpgrade) {
            inv.setItem(13, U.buildItem(Material.LIME_CONCRETE, "§a§l✔ Nâng Cấp",
                Arrays.asList("",
                    "§7Cấp: §f" + currentLevel + " §7→ §a" + (currentLevel + 1),
                    "§7Chi phí: §b1 điểm kỹ năng",
                    "§7Điểm còn lại: §b" + data.getAvailableDiemKyNang(),
                    "", "§eClick để nâng cấp!"), true, 1));
        } else {
            String reason = currentLevel >= 10 ? "§c§lĐã đạt cấp tối đa!" :
                data.getAvailableDiemKyNang() <= 0 ? "§c§lKhông đủ điểm kỹ năng!" :
                "§c§lChưa đủ cấp độ!";
            inv.setItem(13, U.buildItem(Material.RED_CONCRETE, reason,
                Arrays.asList("",
                    "§7Cấp hiện tại: §f" + currentLevel + "§7/§f10",
                    "§7Điểm kỹ năng: §b" + data.getAvailableDiemKyNang()), false, 1));
        }

        // Back button
        inv.setItem(22, U.buildItem(Material.ARROW, "§c§lQuay lại",
            Arrays.asList("§7Quay lại danh sách kỹ năng"), false, 1));

        p.openInventory(inv);
    }

    private static SkillInfo findSkill(PlayerData data, String skillId) {
        if (data.getMonPhai() == MonPhai.THIEU_LAM) {
            for (SkillInfo s : ThieuLamSkills.getAllSkillInfos()) {
                if (s.getId().equals(skillId)) return s;
            }
        }
        return null;
    }
}
