package neo.volam2.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import neo.volam2.data.PlayerData;
import neo.volam2.data.PlayerDataManager;
import neo.volam2.main.U;
import neo.volam2.tiemnang.TiemNangManager;
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
                "§e§lHướng dẫn:",
                "§7Mỗi level nhận §b5 §7điểm tiềm năng.",
                "§7Phân bổ điểm vào chỉ số phù hợp",
                "§7để tăng sức mạnh nhân vật.",
                "",
                "§7Công thức: §fLevel × 5 + Bonus",
                "§7Level: §f" + data.getLevel() + " §7| Bonus: §f" + data.getDiemTiemNangAdd()
            ), false, 1));

        String clickAction = data.getAvailableDiemTiemNang() > 0 ? "§a▸ Click để nâng!" : "§c✘ Không đủ điểm";

        // Suc Manh - slot 10
        double physDmg = TiemNangManager.getPhysicalDamage(data);
        List<String> sucManhLore = new ArrayList<>();
        sucManhLore.add("");
        sucManhLore.add("§7Cấp hiện tại: §c" + data.getSucManh());
        sucManhLore.add("");
        sucManhLore.add("§e§lMô tả:");
        sucManhLore.add(TiemNangType.SUC_MANH.getDesc1());
        if (!TiemNangType.SUC_MANH.getDesc2().isEmpty()) sucManhLore.add(TiemNangType.SUC_MANH.getDesc2());
        sucManhLore.add("");
        sucManhLore.add("§e§lHiệu ứng mỗi điểm:");
        sucManhLore.add("§7  +2 Sát thương vật lý");
        sucManhLore.add("");
        sucManhLore.add("§e§lBonus hiện tại:");
        sucManhLore.add("§c  ⚔ Sát thương vật lý: §f+" + U.lamTronString(physDmg));
        sucManhLore.add("");
        sucManhLore.add(clickAction);
        inv.setItem(10, U.buildItem(Material.IRON_SWORD, TiemNangType.SUC_MANH.getDisplayName(),
            sucManhLore, data.getSucManh() > 0, 1));

        // Than Phap - slot 12
        double dodge = TiemNangManager.getDodge(data);
        double crit = TiemNangManager.getCritChance(data);
        List<String> thanPhapLore = new ArrayList<>();
        thanPhapLore.add("");
        thanPhapLore.add("§7Cấp hiện tại: §a" + data.getThanPhap());
        thanPhapLore.add("");
        thanPhapLore.add("§e§lMô tả:");
        thanPhapLore.add(TiemNangType.THAN_PHAP.getDesc1());
        if (!TiemNangType.THAN_PHAP.getDesc2().isEmpty()) thanPhapLore.add(TiemNangType.THAN_PHAP.getDesc2());
        thanPhapLore.add("");
        thanPhapLore.add("§e§lHiệu ứng mỗi điểm:");
        thanPhapLore.add("§7  +0.5% Né tránh");
        thanPhapLore.add("§7  +0.3% Chí mạng");
        thanPhapLore.add("");
        thanPhapLore.add("§e§lBonus hiện tại:");
        thanPhapLore.add("§a  ✦ Né tránh: §f+" + U.lamTronString(dodge) + "%");
        thanPhapLore.add("§6  ★ Chí mạng: §f+" + U.lamTronString(crit) + "%");
        thanPhapLore.add("");
        thanPhapLore.add(clickAction);
        inv.setItem(12, U.buildItem(Material.FEATHER, TiemNangType.THAN_PHAP.getDisplayName(),
            thanPhapLore, data.getThanPhap() > 0, 1));

        // Noi Cong - slot 14
        double mana = TiemNangManager.getMaxMana(data);
        double magicDmg = TiemNangManager.getMagicDamage(data);
        List<String> noiCongLore = new ArrayList<>();
        noiCongLore.add("");
        noiCongLore.add("§7Cấp hiện tại: §9" + data.getNoiCong());
        noiCongLore.add("");
        noiCongLore.add("§e§lMô tả:");
        noiCongLore.add(TiemNangType.NOI_CONG.getDesc1());
        if (!TiemNangType.NOI_CONG.getDesc2().isEmpty()) noiCongLore.add(TiemNangType.NOI_CONG.getDesc2());
        noiCongLore.add("");
        noiCongLore.add("§e§lHiệu ứng mỗi điểm:");
        noiCongLore.add("§7  +5 Mana");
        noiCongLore.add("§7  +1.5 Sát thương nội công");
        noiCongLore.add("");
        noiCongLore.add("§e§lBonus hiện tại:");
        noiCongLore.add("§b  ✦ Mana: §f+" + U.lamTronString(mana));
        noiCongLore.add("§9  ✧ ST nội công: §f+" + U.lamTronString(magicDmg));
        noiCongLore.add("");
        noiCongLore.add(clickAction);
        inv.setItem(14, U.buildItem(Material.LAPIS_LAZULI, TiemNangType.NOI_CONG.getDisplayName(),
            noiCongLore, data.getNoiCong() > 0, 1));

        // The Luc - slot 16
        double hp = TiemNangManager.getMaxHP(data);
        double def = TiemNangManager.getDefense(data);
        List<String> theLucLore = new ArrayList<>();
        theLucLore.add("");
        theLucLore.add("§7Cấp hiện tại: §e" + data.getTheLuc());
        theLucLore.add("");
        theLucLore.add("§e§lMô tả:");
        theLucLore.add(TiemNangType.THE_LUC.getDesc1());
        if (!TiemNangType.THE_LUC.getDesc2().isEmpty()) theLucLore.add(TiemNangType.THE_LUC.getDesc2());
        theLucLore.add("");
        theLucLore.add("§e§lHiệu ứng mỗi điểm:");
        theLucLore.add("§7  +10 HP");
        theLucLore.add("§7  +0.5 Giáp");
        theLucLore.add("");
        theLucLore.add("§e§lBonus hiện tại:");
        theLucLore.add("§c  ♥ HP: §f+" + U.lamTronString(hp));
        theLucLore.add("§e  ♦ Giáp: §f+" + U.lamTronString(def));
        theLucLore.add("");
        theLucLore.add(clickAction);
        inv.setItem(16, U.buildItem(Material.GOLDEN_APPLE, TiemNangType.THE_LUC.getDisplayName(),
            theLucLore, data.getTheLuc() > 0, 1));

        // Close
        inv.setItem(31, U.getQuitItem());

        p.openInventory(inv);
    }
}
