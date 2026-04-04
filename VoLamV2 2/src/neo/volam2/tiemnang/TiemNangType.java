package neo.volam2.tiemnang;

public enum TiemNangType {
    SUC_MANH("Sức Mạnh", "§c⚔ Sức Mạnh",
        "§7Tăng sát thương vật lý", "§7phù hợp các phái cận chiến."),
    THAN_PHAP("Thân Pháp", "§a✦ Thân Pháp",
        "§7Tăng né tránh, chính xác và tỷ lệ chí mạng", "§7phù hợp phái cần tốc độ."),
    NOI_CONG("Nội Công", "§9✧ Nội Công",
        "§7Tăng sát thương nội công và năng lượng (mana)", ""),
    THE_LUC("Thể Lực", "§e♥ Thể Lực",
        "§7Tăng máu (HP) và sức bền", "§7thiết yếu cho mọi nhân vật để tồn tại.");

    private final String name;
    private final String displayName;
    private final String desc1;
    private final String desc2;

    TiemNangType(String name, String displayName, String desc1, String desc2) {
        this.name = name;
        this.displayName = displayName;
        this.desc1 = desc1;
        this.desc2 = desc2;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDesc1() {
        return desc1;
    }

    public String getDesc2() {
        return desc2;
    }
}
