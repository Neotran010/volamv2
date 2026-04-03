package neo.volam2.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import neo.volam2.data.MonPhai;
import neo.volam2.data.PlayerData;
import neo.volam2.data.PlayerDataManager;
import neo.volam2.data.TocHe;
import neo.volam2.main.U;

public class ChonMonPhaiGUI {

    public static final String TITLE = "§0§l✦ §6§lChọn Môn Phái §0§l✦";

    public static void open(Player p) {
        PlayerData data = PlayerDataManager.get(p);
        if (data == null || !data.hasTocHe()) return;

        TocHe tocHe = data.getTocHe();
        MonPhai[] monPhais = MonPhai.getByTocHe(tocHe);

        Inventory inv = Bukkit.createInventory(null, 27, TITLE);

        // Fill border
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, U.buildItem(Material.BLACK_STAINED_GLASS_PANE, " ", null, false, 1));
        }

        // Info item
        inv.setItem(4, U.buildItem(Material.BOOK, "§e§lTộc Hệ: " + tocHe.getDisplayName(),
            Arrays.asList("", "§7Hãy chọn môn phái bạn muốn gia nhập!"), false, 1));

        // Place mon phai items
        int[] slots = {11, 13, 15};
        for (int i = 0; i < monPhais.length && i < slots.length; i++) {
            MonPhai mp = monPhais[i];
            if (mp.isMaintenance()) {
                inv.setItem(slots[i], U.buildItem(mp.getIcon(), mp.getDisplayName(),
                    Arrays.asList("", "§c§l⚠ Đang bảo trì!", "", "§7Môn phái này đang được phát triển.",
                        "§7Vui lòng chọn môn phái khác."), false, 1));
            } else {
                inv.setItem(slots[i], U.buildItem(mp.getIcon(), mp.getDisplayName(),
                    Arrays.asList("", "§7Tộc: " + tocHe.getDisplayName(),
                        "", "§eClick để gia nhập!"), true, 1));
            }
        }

        // Back button
        inv.setItem(22, U.buildItem(Material.ARROW, "§c§lQuay lại", Arrays.asList("§7Quay lại chọn tộc hệ"), false, 1));

        p.openInventory(inv);
    }
}
