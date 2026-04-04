package neo.volam2.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import neo.volam2.data.PlayerData;
import neo.volam2.data.PlayerDataManager;
import neo.volam2.main.U;
import neo.volam2.tiemnang.TiemNangType;

public class TiemNangGUI {

    public static final String TITLE = "§0§l✦ §a§lTiềm Năng §0§l✦";

    public static void open(Player p) {
        PlayerData data = PlayerDataManager.get(p);
        if (data == null) return;

        Inventory inv = Bukkit.createInventory(null, 36, TITLE);

        // Fill border
        for (int i = 0; i < 36; i++) {
            inv.setItem(i, U.buildItem(Material.BLACK_STAINED_GLASS_PANE, " ", null, false, 1));
        }

        // Header info
        inv.setItem(4, U.buildItem(Material.EXPERIENCE_BOTTLE, "§e§lĐiểm Tiềm Năng",
            Arrays.asList("",
                "§7Điểm còn lại: §b" + data.getAvailableDiemTiemNang(),
                "§7Tổng điểm: §f" + data.getTotalDiemTiemNang(),
                "§7Đã sử dụng: §f" + data.getUsedDiemTiemNang(),
                "",
                "§7Công thức: §fLevel × 5 + Bonus",
                "§7Level: §f" + data.getLevel() + " §7| Bonus: §f" + data.getDiemTiemNangAdd()
            ), false, 1));

        // Suc Manh - slot 10
        inv.setItem(10, U.buildItem(Material.IRON_SWORD, TiemNangType.SUC_MANH.getDisplayName(),
            Arrays.asList("",
                "§7Cấp hiện tại: §c" + data.getSucManh(),
                "", TiemNangType.SUC_MANH.getDesc1(), TiemNangType.SUC_MANH.getDesc2(),
                "", "§7+2 Sát thương vật lý / điểm",
                "", data.getAvailableDiemTiemNang() > 0 ? "§eClick để nâng!" : "§c✘ Không đủ điểm"
            ), data.getSucManh() > 0, 1));

        // Than Phap - slot 12
        inv.setItem(12, U.buildItem(Material.FEATHER, TiemNangType.THAN_PHAP.getDisplayName(),
            Arrays.asList("",
                "§7Cấp hiện tại: §a" + data.getThanPhap(),
                "", TiemNangType.THAN_PHAP.getDesc1(), TiemNangType.THAN_PHAP.getDesc2(),
                "", "§7+0.5% Né tránh / điểm",
                "§7+0.3% Chí mạng / điểm",
                "", data.getAvailableDiemTiemNang() > 0 ? "§eClick để nâng!" : "§c✘ Không đủ điểm"
            ), data.getThanPhap() > 0, 1));

        // Noi Cong - slot 14
        inv.setItem(14, U.buildItem(Material.LAPIS_LAZULI, TiemNangType.NOI_CONG.getDisplayName(),
            Arrays.asList("",
                "§7Cấp hiện tại: §9" + data.getNoiCong(),
                "", TiemNangType.NOI_CONG.getDesc1(), TiemNangType.NOI_CONG.getDesc2(),
                "", "§7+5 Mana / điểm",
                "§7+1.5 Sát thương nội công / điểm",
                "", data.getAvailableDiemTiemNang() > 0 ? "§eClick để nâng!" : "§c✘ Không đủ điểm"
            ), data.getNoiCong() > 0, 1));

        // The Luc - slot 16
        inv.setItem(16, U.buildItem(Material.GOLDEN_APPLE, TiemNangType.THE_LUC.getDisplayName(),
            Arrays.asList("",
                "§7Cấp hiện tại: §e" + data.getTheLuc(),
                "", TiemNangType.THE_LUC.getDesc1(), TiemNangType.THE_LUC.getDesc2(),
                "", "§7+10 HP / điểm",
                "§7+0.5 Giáp / điểm",
                "", data.getAvailableDiemTiemNang() > 0 ? "§eClick để nâng!" : "§c✘ Không đủ điểm"
            ), data.getTheLuc() > 0, 1));

        // Close
        inv.setItem(31, U.getQuitItem());

        p.openInventory(inv);
    }
}
