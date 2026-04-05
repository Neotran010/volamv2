package neo.volam2.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import neo.volam2.data.PlayerData;
import neo.volam2.data.PlayerDataManager;
import neo.volam2.main.U;
import neo.volam2.tiemnang.TiemNangManager;

public class ThongTinNhanVatGUI {

    public static final String TITLE = "§0§l✦ §6§lThông Tin Nhân Vật §0§l✦";

    public static void open(Player p) {
        PlayerData data = PlayerDataManager.get(p);
        if (data == null) return;

        Inventory inv = Bukkit.createInventory(null, 54, TITLE);

        // Fill border
        for (int i = 0; i < 54; i++) {
            inv.setItem(i, U.buildItem(Material.BLACK_STAINED_GLASS_PANE, " ", null, false, 1));
        }

        // Player head info - slot 4
        inv.setItem(4, U.buildItem(Material.PLAYER_HEAD, "§e§l" + p.getName(),
            Arrays.asList(
                "",
                "§6§l▸ Thông tin cơ bản",
                "§7Tên: §f" + p.getName(),
                "§7Cấp độ: §f" + data.getLevel(),
                "§7Kinh nghiệm: §f" + data.getExp() + "§7/§f" + data.getMaxExp(),
                "",
                "§6§l▸ Môn phái",
                "§7Tộc hệ: " + (data.hasTocHe() ? data.getTocHe().getDisplayName() : "§cChưa chọn"),
                "§7Môn phái: " + (data.hasMonPhai() ? data.getMonPhai().getDisplayName() : "§cChưa chọn"),
                "§7Nhánh: " + (data.getNhanh() != null ? data.getNhanh().getDisplayName() : "§cChưa chọn"),
                "",
                "§6§l▸ Điểm",
                "§7Điểm tiềm năng: §b" + data.getAvailableDiemTiemNang() + "§7/§f" + data.getTotalDiemTiemNang(),
                "§7Điểm kỹ năng: §b" + data.getAvailableDiemKyNang() + "§7/§f" + data.getTotalDiemKyNang()
            ), false, 1));

        // Stats display - slot 20 (Tiềm năng stats)
        inv.setItem(20, U.buildItem(Material.DIAMOND, "§b§l⚡ Chỉ Số Tiềm Năng",
            Arrays.asList(
                "",
                "§7Sức Mạnh: §c" + data.getSucManh(),
                "§7Thân Pháp: §a" + data.getThanPhap(),
                "§7Nội Công: §9" + data.getNoiCong(),
                "§7Thể Lực: §e" + data.getTheLuc()
            ), true, 1));

        // Combat stats - slot 22 (Computed stats)
        double physDmg = TiemNangManager.getPhysicalDamage(data);
        double magicDmg = TiemNangManager.getMagicDamage(data);
        double defense = TiemNangManager.getDefense(data);
        double dodge = TiemNangManager.getDodge(data);
        double critChance = TiemNangManager.getCritChance(data);
        double bonusHP = TiemNangManager.getMaxHP(data);
        double bonusMana = TiemNangManager.getMaxMana(data);

        inv.setItem(22, U.buildItem(Material.IRON_SWORD, "§c§l⚔ Chỉ Số Chiến Đấu",
            Arrays.asList(
                "",
                "§c⚔ Sát thương vật lý: §f+" + U.lamTronString(physDmg),
                "§9✧ Sát thương nội công: §f+" + U.lamTronString(magicDmg),
                "§e♦ Giáp: §f+" + U.lamTronString(defense),
                "§a✦ Né tránh: §f+" + U.lamTronString(dodge) + "%",
                "§6★ Chí mạng: §f+" + U.lamTronString(critChance) + "%",
                "",
                "§c♥ HP bonus: §f+" + U.lamTronString(bonusHP),
                "§b✦ Mana bonus: §f+" + U.lamTronString(bonusMana)
            ), true, 1));

        // Level info - slot 24
        inv.setItem(24, U.buildItem(Material.EXPERIENCE_BOTTLE, "§a§l✦ Cấp Độ & Kinh Nghiệm",
            Arrays.asList(
                "",
                "§7Cấp độ hiện tại: §f" + data.getLevel(),
                "§7Kinh nghiệm: §f" + data.getExp() + "§7/§f" + data.getMaxExp(),
                "",
                "§7Mỗi cấp nhận:",
                "§7  +5 điểm tiềm năng",
                "§7  +1 điểm kỹ năng / 5 cấp"
            ), false, 1));

        // Ky Nang button - slot 38
        inv.setItem(38, U.buildItem(Material.ENCHANTED_BOOK, "§b§l⚡ Kỹ Năng",
            Arrays.asList("", "§7Xem và nâng cấp kỹ năng", "", "§eClick để mở!"), true, 1));

        // Tiem Nang button - slot 40
        inv.setItem(40, U.buildItem(Material.EXPERIENCE_BOTTLE, "§a§l✦ Tiềm Năng",
            Arrays.asList("", "§7Phân bổ điểm tiềm năng", "", "§eClick để mở!"), true, 1));

        // Chuyen Nhanh button - slot 42
        inv.setItem(42, U.buildItem(Material.IRON_SWORD, "§6§l⚔ Chuyển Nhánh",
            Arrays.asList("", "§7Đổi nhánh vũ khí", "§c(Có tính phí)", "", "§eClick để mở!"), false, 1));

        // Chuyen Phai button - slot 44
        inv.setItem(44, U.buildItem(Material.COMPASS, "§d§l✿ Chuyển Phái",
            Arrays.asList("", "§7Đổi môn phái (cùng hệ)", "§c(Có tính phí)", "", "§eClick để mở!"), false, 1));

        // Close
        inv.setItem(49, U.getQuitItem());

        p.openInventory(inv);
    }
}
