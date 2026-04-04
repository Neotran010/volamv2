package neo.volam2.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import neo.volam2.data.TocHe;
import neo.volam2.main.U;

public class ChonTocGUI {

    public static final String TITLE = "§0§l✦ §6§lChọn Tộc Hệ §0§l✦";

    public static void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, TITLE);

        // Fill border with glass panes
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, U.buildItem(Material.BLACK_STAINED_GLASS_PANE, " ", null, false, 1));
        }

        // Kim - slot 10
        inv.setItem(10, U.buildItem(Material.GOLD_INGOT, TocHe.KIM.getIconName(),
            Arrays.asList("", "§7Tộc hệ: " + TocHe.KIM.getDisplayName(),
                "", "§7Môn phái: §6Thiếu Lâm§7, §6Thiên Vương",
                "", "§eClick để chọn!"), true, 1));

        // Moc - slot 11
        inv.setItem(11, U.buildItem(Material.OAK_SAPLING, TocHe.MOC.getIconName(),
            Arrays.asList("", "§7Tộc hệ: " + TocHe.MOC.getDisplayName(),
                "", "§7Môn phái: §2Đường Môn§7, §2Ngũ Độc",
                "", "§eClick để chọn!"), true, 1));

        // Thuy - slot 13
        inv.setItem(13, U.buildItem(Material.HEART_OF_THE_SEA, TocHe.THUY.getIconName(),
            Arrays.asList("", "§7Tộc hệ: " + TocHe.THUY.getDisplayName(),
                "", "§7Môn phái: §9Nga My",
                "", "§eClick để chọn!"), true, 1));

        // Hoa - slot 15
        inv.setItem(15, U.buildItem(Material.BLAZE_POWDER, TocHe.HOA.getIconName(),
            Arrays.asList("", "§7Tộc hệ: " + TocHe.HOA.getDisplayName(),
                "", "§7Môn phái: §cCái Bang§7, §cThiên Nhẫn",
                "", "§eClick để chọn!"), true, 1));

        // Tho - slot 16
        inv.setItem(16, U.buildItem(Material.TERRACOTTA, TocHe.THO.getIconName(),
            Arrays.asList("", "§7Tộc hệ: " + TocHe.THO.getDisplayName(),
                "", "§7Môn phái: §eVõ Đang§7, §eCôn Lôn",
                "", "§eClick để chọn!"), true, 1));

        p.openInventory(inv);
    }
}
