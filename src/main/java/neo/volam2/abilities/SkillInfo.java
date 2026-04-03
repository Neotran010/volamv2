package neo.volam2.abilities;

import java.util.List;

import neo.volam2.data.Nhanh;

public class SkillInfo {

    private final String id;
    private final String displayName;
    private final String type; // Active, Passive, Buff
    private final int requiredLevel;
    private final double manaCost;
    private final double cooldown;
    private final List<String> description;
    private final Nhanh branch; // null = shared skill (all branches)
    private final String comboKey; // e.g. "LRL" for Left-Right-Left, null if passive

    public SkillInfo(String id, String displayName, String type, int requiredLevel,
                     double manaCost, double cooldown, List<String> description,
                     Nhanh branch, String comboKey) {
        this.id = id;
        this.displayName = displayName;
        this.type = type;
        this.requiredLevel = requiredLevel;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.description = description;
        this.branch = branch;
        this.comboKey = comboKey;
    }

    public String getId() { return id; }
    public String getDisplayName() { return displayName; }
    public String getType() { return type; }
    public int getRequiredLevel() { return requiredLevel; }
    public double getManaCost() { return manaCost; }
    public double getCooldown() { return cooldown; }
    public List<String> getDescription() { return description; }
    public Nhanh getBranch() { return branch; }
    public String getComboKey() { return comboKey; }
}
