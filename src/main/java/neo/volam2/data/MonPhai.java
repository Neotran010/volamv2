package neo.volam2.data;

import org.bukkit.Material;

public enum MonPhai {
    // Kim
    THIEU_LAM("Thiếu Lâm", "§6Thiếu Lâm", TocHe.KIM, Material.GOLDEN_CHESTPLATE, false),
    THIEN_VUONG("Thiên Vương", "§6Thiên Vương", TocHe.KIM, Material.GOLDEN_HELMET, true),
    // Moc
    DUONG_MON("Đường Môn", "§2Đường Môn", TocHe.MOC, Material.CROSSBOW, true),
    NGU_DOC("Ngũ Độc", "§2Ngũ Độc", TocHe.MOC, Material.SPIDER_EYE, true),
    // Thuy
    NGA_MY("Nga My", "§9Nga My", TocHe.THUY, Material.DIAMOND_SWORD, true),
    // Hoa
    CAI_BANG("Cái Bang", "§cCái Bang", TocHe.HOA, Material.STICK, true),
    THIEN_NHAN("Thiên Nhẫn", "§cThiên Nhẫn", TocHe.HOA, Material.IRON_SWORD, true),
    // Tho
    VO_DANG("Võ Đang", "§eVõ Đang", TocHe.THO, Material.IRON_CHESTPLATE, true),
    CON_LON("Côn Lôn", "§eCôn Lôn", TocHe.THO, Material.SHIELD, true);

    private final String name;
    private final String displayName;
    private final TocHe tocHe;
    private final Material icon;
    private final boolean maintenance;

    MonPhai(String name, String displayName, TocHe tocHe, Material icon, boolean maintenance) {
        this.name = name;
        this.displayName = displayName;
        this.tocHe = tocHe;
        this.icon = icon;
        this.maintenance = maintenance;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TocHe getTocHe() {
        return tocHe;
    }

    public Material getIcon() {
        return icon;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public static MonPhai[] getByTocHe(TocHe tocHe) {
        java.util.List<MonPhai> list = new java.util.ArrayList<>();
        for (MonPhai mp : values()) {
            if (mp.getTocHe() == tocHe) list.add(mp);
        }
        return list.toArray(new MonPhai[0]);
    }

    public static MonPhai fromName(String name) {
        for (MonPhai mp : values()) {
            if (mp.name().equalsIgnoreCase(name)) return mp;
        }
        return null;
    }
}
