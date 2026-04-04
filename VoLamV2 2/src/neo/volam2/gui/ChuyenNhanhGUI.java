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
import neo.volam2.main.U;

public class ChuyenNhanhGUI {

    public static final String TITLE = "§0§l✦ §6§lChuyển Nhánh §0§l✦";
    public static final int COST = 1000;

    public static void open(Player p) {
        PlayerData data = PlayerDataManager.get(p);
        if (data == null || !data.hasMonPhai()) return;

        MonPhai mp = data.getMonPhai();
        Nhanh[] branches = Nhanh.getByMonPhai(mp);

        Inventory inv = Bukkit.createInventory(null, 27, TITLE);

        // Fill
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, U.buildItem(Material.BLACK_STAINED_GLASS_PANE, " ", null, false, 1));
        }

        // Header
        inv.setItem(4, U.buildItem(Material.ANVIL, "§e§lChuyển Nhánh Vũ Khí",
            Arrays.asList("",
                "§7Nhánh hiện tại: " + (data.getNhanh() != null ? data.getNhanh().getDisplayName() : "§cChưa chọn"),
                "§7Phí chuyển nhánh: §6" + COST + " xu",
                "", "§7Chọn nhánh bạn muốn đổi:"), false, 1));

        // Place branches
        int[] slots = {10, 13, 16};
        for (int i = 0; i < branches.length && i < slots.length; i++) {
            Nhanh n = branches[i];
            boolean isCurrent = n == data.getNhanh();
            inv.setItem(slots[i], U.buildItem(n.getWeaponIcon(),
                (isCurrent ? "§a§l" : "§6") + n.getName(),
                Arrays.asList("",
                    n.getDescription(),
                    "",
                    isCurrent ? "§a✔ Đang sử dụng!" : "§eClick để chuyển! §7(Phí: §6" + COST + " xu§7)"),
                isCurrent, 1));
        }

        // Back
        inv.setItem(22, U.buildItem(Material.ARROW, "§c§lQuay lại", Arrays.asList("§7Quay lại thông tin nhân vật"), false, 1));

        p.openInventory(inv);
    }
}
