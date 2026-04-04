package neo.volam2.data;

public enum TocHe {
    KIM("§6Kim", "§e⚔ Kim"),
    MOC("§2Mộc", "§a🌿 Mộc"),
    THUY("§9Thủy", "§b💧 Thủy"),
    HOA("§cHỏa", "§c🔥 Hỏa"),
    THO("§eThổ", "§6🏔 Thổ");

    private final String displayName;
    private final String iconName;

    TocHe(String displayName, String iconName) {
        this.displayName = displayName;
        this.iconName = iconName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIconName() {
        return iconName;
    }
}
