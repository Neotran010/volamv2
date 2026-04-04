package neo.volam2.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import neo.volam2.data.MonPhai;
import neo.volam2.data.Nhanh;
import neo.volam2.data.PlayerData;
import neo.volam2.data.PlayerDataManager;
import neo.volam2.data.TocHe;
import neo.volam2.main.U;

public class ChuyenPhaiGUI {

    public static final String TITLE = "§0§l✦ §d§lChuyển Phái §0§l✦";
    public static final int COST = 5000;

    public static void open(Player p) {
        PlayerData data = PlayerDataManager.get(p);
        if (data == null || !data.hasTocHe()) return;

        TocHe tocHe = data.getTocHe();
        MonPhai[] monPhais = MonPhai.getByTocHe(tocHe);

        Inventory inv = Bukkit.createInventory(null, 27, TITLE);

        // Fill
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, U.buildItem(Material.BLACK_STAINED_GLASS_PANE, " ", null, false, 1));
        }

        // Header
        inv.setItem(4, U.buildItem(Material.COMPASS, "§e§lChuyển Phái §7(Cùng hệ)",
            Arrays.asList("",
                "§7Môn phái hiện tại: " + (data.hasMonPhai() ? data.getMonPhai().getDisplayName() : "§cChưa chọn"),
                "§7Tộc hệ: " + tocHe.getDisplayName(),
                "§7Phí chuyển phái: §6" + COST + " xu",
                "", "§c⚠ Chuyển phái sẽ reset nhánh vũ khí!"), false, 1));

        // Place factions
        int[] slots = {11, 13, 15};
        for (int i = 0; i < monPhais.length && i < slots.length; i++) {
            MonPhai mp = monPhais[i];
            boolean isCurrent = mp == data.getMonPhai();
            if (mp.isMaintenance() && !isCurrent) {
                inv.setItem(slots[i], U.buildItem(mp.getIcon(), mp.getDisplayName(),
                    Arrays.asList("", "§c§l⚠ Đang bảo trì!"), false, 1));
            } else {
                inv.setItem(slots[i], U.buildItem(mp.getIcon(),
                    (isCurrent ? "§a§l" : "") + mp.getDisplayName(),
                    Arrays.asList("",
                        isCurrent ? "§a✔ Đang ở phái này!" : "§eClick để chuyển! §7(Phí: §6" + COST + " xu§7)"),
                    isCurrent, 1));
            }
        }

        // Back
        inv.setItem(22, U.buildItem(Material.ARROW, "§c§lQuay lại", Arrays.asList("§7Quay lại thông tin nhân vật"), false, 1));

        p.openInventory(inv);
    }
}
