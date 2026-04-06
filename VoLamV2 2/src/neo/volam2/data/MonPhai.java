package neo.volam2.data;

import org.bukkit.Material;

public enum MonPhai {
    // Kim
    THIEU_LAM("Thiếu Lâm", "§6Thiếu Lâm", TocHe.KIM, Material.GOLDEN_CHESTPLATE, false,
        "§7Thiên hạ võ công xuất Thiếu Lâm,",
        "§7Kim Cang bất hoại, La Hán hộ thân.",
        "§7Quyền cước cương mãnh, bổng pháp oai phong,",
        "§7đao thuật sắc bén, xưng bá võ lâm."),
    THIEN_VUONG("Thiên Vương", "§6Thiên Vương", TocHe.KIM, Material.GOLDEN_HELMET, true,
        "§7Thiên Vương cái thế, uy trấn tứ phương.",
        "§7Thần lực vô song, phá diệt vạn pháp."),
    // Moc
    DUONG_MON("Đường Môn", "§2Đường Môn", TocHe.MOC, Material.CROSSBOW, true,
        "§7Ám khí vô song, bách bộ xuyên dương.",
        "§7Đường Môn cơ quan, thần quỷ mạc trắc."),
    NGU_DOC("Ngũ Độc", "§2Ngũ Độc", TocHe.MOC, Material.SPIDER_EYE, true,
        "§7Ngũ Độc kỳ thuật, vạn độc bất xâm.",
        "§7Dĩ độc công độc, trị bệnh cứu nhân."),
    // Thuy
    NGA_MY("Nga My", "§9Nga My", TocHe.THUY, Material.DIAMOND_SWORD, true,
        "§7Nga My kiếm pháp, nhu trung đới cương.",
        "§7Thanh tâm quả dục, kiếm thuật siêu phàm."),
    // Hoa
    CAI_BANG("Cái Bang", "§cCái Bang", TocHe.HOA, Material.STICK, true,
        "§7Thiên hạ đệ nhất đại bang,",
        "§7Đả Cẩu bổng pháp, hàng long thập bát chưởng."),
    THIEN_NHAN("Thiên Nhẫn", "§cThiên Nhẫn", TocHe.HOA, Material.IRON_SWORD, true,
        "§7Nhẫn giả vô địch, sát nhân vô hình.",
        "§7Ám sát chi thuật, thiên hạ vô song."),
    // Tho
    VO_DANG("Võ Đang", "§eVõ Đang", TocHe.THO, Material.IRON_CHESTPLATE, true,
        "§7Thái Cực sinh lưỡng nghi,",
        "§7nhu khắc cương, tĩnh chế động,",
        "§7kiếm thuật phi phàm."),
    CON_LON("Côn Lôn", "§eCôn Lôn", TocHe.THO, Material.SHIELD, true,
        "§7Côn Lôn tiên phái, đạo pháp tự nhiên.",
        "§7Hộ thân kỳ thuật, cương nhu tịnh tế.");

    private final String name;
    private final String displayName;
    private final TocHe tocHe;
    private final Material icon;
    private final boolean maintenance;
    private final String[] description;

    MonPhai(String name, String displayName, TocHe tocHe, Material icon, boolean maintenance, String... description) {
        this.name = name;
        this.displayName = displayName;
        this.tocHe = tocHe;
        this.icon = icon;
        this.maintenance = maintenance;
        this.description = description;
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

    public String[] getDescription() {
        return description;
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
