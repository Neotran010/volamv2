package neo.volam2.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import neo.volam2.data.PlayerData;
import neo.volam2.data.PlayerDataManager;
import neo.volam2.main.U;

public class ThongTinNhanVatGUI {

    public static final String TITLE = "§0§l✦ §6§lThông Tin Nhân Vật §0§l✦";

    public static void open(Player p) {
        PlayerData data = PlayerDataManager.get(p);
        if (data == null) return;

        Inventory inv = Bukkit.createInventory(null, 45, TITLE);

        // Fill border
        for (int i = 0; i < 45; i++) {
            inv.setItem(i, U.buildItem(Material.BLACK_STAINED_GLASS_PANE, " ", null, false, 1));
        }

        // Player head info - slot 13
        inv.setItem(13, U.buildItem(Material.PLAYER_HEAD, "§e§l" + p.getName(),
            Arrays.asList(
                "",
                "§7Cấp độ: §f" + data.getLevel(),
                "§7Tộc hệ: " + (data.hasTocHe() ? data.getTocHe().getDisplayName() : "§cChưa chọn"),
                "§7Môn phái: " + (data.hasMonPhai() ? data.getMonPhai().getDisplayName() : "§cChưa chọn"),
                "§7Nhánh: " + (data.getNhanh() != null ? data.getNhanh().getDisplayName() : "§cChưa chọn"),
                "",
                "§7Sức Mạnh: §c" + data.getSucManh(),
                "§7Thân Pháp: §a" + data.getThanPhap(),
                "§7Nội Công: §9" + data.getNoiCong(),
                "§7Thể Lực: §e" + data.getTheLuc(),
                "",
                "§7Điểm tiềm năng: §b" + data.getAvailableDiemTiemNang(),
                "§7Điểm kỹ năng: §b" + data.getAvailableDiemKyNang()
            ), false, 1));

        // Ky Nang button - slot 29
        inv.setItem(29, U.buildItem(Material.ENCHANTED_BOOK, "§b§l⚡ Kỹ Năng",
            Arrays.asList("", "§7Xem và nâng cấp kỹ năng", "", "§eClick để mở!"), true, 1));

        // Tiem Nang button - slot 31
        inv.setItem(31, U.buildItem(Material.EXPERIENCE_BOTTLE, "§a§l✦ Tiềm Năng",
            Arrays.asList("", "§7Phân bổ điểm tiềm năng", "", "§eClick để mở!"), true, 1));

        // Chuyen Nhanh button - slot 33
        inv.setItem(33, U.buildItem(Material.IRON_SWORD, "§6§l⚔ Chuyển Nhánh",
            Arrays.asList("", "§7Đổi nhánh vũ khí", "§c(Có tính phí)", "", "§eClick để mở!"), false, 1));

        // Chuyen Phai button - slot 35
        inv.setItem(35, U.buildItem(Material.COMPASS, "§d§l✿ Chuyển Phái",
            Arrays.asList("", "§7Đổi môn phái (cùng hệ)", "§c(Có tính phí)", "", "§eClick để mở!"), false, 1));

        // Close
        inv.setItem(40, U.getQuitItem());

        p.openInventory(inv);
    }
}
