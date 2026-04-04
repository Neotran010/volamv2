package neo.volam2.data;

import org.bukkit.Material;

public enum Nhanh {
    // Thieu Lam branches
    THIEU_LAM_QUYEN("Quyền", "§6Quyền §7(Găng tay)", MonPhai.THIEU_LAM, Material.LEATHER, true,
        "§7Trâu, phản đòn, choáng liên tục (đơn/nhỏ)"),
    THIEU_LAM_BONG("Bổng/Côn", "§6Bổng/Côn", MonPhai.THIEU_LAM, Material.STICK, false,
        "§7Đánh lan, khống chế diện rộng, kháng khống chế"),
    THIEU_LAM_DAO("Đao", "§6Đao", MonPhai.THIEU_LAM, Material.IRON_SWORD, false,
        "§7Tầm xa, sát thương chí mạng"),

    // Thien Vuong branches (placeholder)
    THIEN_VUONG_DEFAULT("Mặc định", "§6Mặc định", MonPhai.THIEN_VUONG, Material.IRON_AXE, true, "§7Đang bảo trì"),

    // Duong Mon branches (placeholder)
    DUONG_MON_DEFAULT("Mặc định", "§2Mặc định", MonPhai.DUONG_MON, Material.CROSSBOW, true, "§7Đang bảo trì"),

    // Ngu Doc branches (placeholder)
    NGU_DOC_DEFAULT("Mặc định", "§2Mặc định", MonPhai.NGU_DOC, Material.SPIDER_EYE, true, "§7Đang bảo trì"),

    // Nga My branches (placeholder)
    NGA_MY_DEFAULT("Mặc định", "§9Mặc định", MonPhai.NGA_MY, Material.DIAMOND_SWORD, true, "§7Đang bảo trì"),

    // Cai Bang branches (placeholder)
    CAI_BANG_DEFAULT("Mặc định", "§cMặc định", MonPhai.CAI_BANG, Material.STICK, true, "§7Đang bảo trì"),

    // Thien Nhan branches (placeholder)
    THIEN_NHAN_DEFAULT("Mặc định", "§cMặc định", MonPhai.THIEN_NHAN, Material.IRON_SWORD, true, "§7Đang bảo trì"),

    // Vo Dang branches (placeholder)
    VO_DANG_DEFAULT("Mặc định", "§eMặc định", MonPhai.VO_DANG, Material.IRON_CHESTPLATE, true, "§7Đang bảo trì"),

    // Con Lon branches (placeholder)
    CON_LON_DEFAULT("Mặc định", "§eMặc định", MonPhai.CON_LON, Material.SHIELD, true, "§7Đang bảo trì");

    private final String name;
    private final String displayName;
    private final MonPhai monPhai;
    private final Material weaponIcon;
    private final boolean isDefault;
    private final String description;

    Nhanh(String name, String displayName, MonPhai monPhai, Material weaponIcon, boolean isDefault, String description) {
        this.name = name;
        this.displayName = displayName;
        this.monPhai = monPhai;
        this.weaponIcon = weaponIcon;
        this.isDefault = isDefault;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public MonPhai getMonPhai() {
        return monPhai;
    }

    public Material getWeaponIcon() {
        return weaponIcon;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public String getDescription() {
        return description;
    }

    public static Nhanh getDefault(MonPhai monPhai) {
        for (Nhanh n : values()) {
            if (n.getMonPhai() == monPhai && n.isDefault()) return n;
        }
        return null;
    }

    public static Nhanh[] getByMonPhai(MonPhai monPhai) {
        java.util.List<Nhanh> list = new java.util.ArrayList<>();
        for (Nhanh n : values()) {
            if (n.getMonPhai() == monPhai) list.add(n);
        }
        return list.toArray(new Nhanh[0]);
    }

    public static Nhanh fromName(String name) {
        for (Nhanh n : values()) {
            if (n.name().equalsIgnoreCase(name)) return n;
        }
        return null;
    }
}
