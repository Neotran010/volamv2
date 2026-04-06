package neo.volam2.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private String playerName;

    // Toc he & Mon phai
    private TocHe tocHe;
    private MonPhai monPhai;
    private Nhanh nhanh;

    // Level
    private int level = 1;
    private int exp = 0;

    // Tiem nang (potential stats)
    private int diemTiemNang = 0; // available points
    private int diemTiemNangAdd = 0; // bonus points from admin/items/rewards
    private int sucManh = 0;
    private int thanPhap = 0;
    private int noiCong = 0;
    private int theLuc = 0;

    // Ky nang (skill points)
    private int diemKyNangAdd = 0; // bonus points from admin/items/rewards

    // Skill levels: skillName -> level
    private Map<String, Integer> skillLevels = new HashMap<>();

    // Trigger flags
    private boolean triggerChooseClass = false;

    // Dirty flag for saving
    private boolean dirty = false;

    public PlayerData(UUID uuid, String playerName) {
        this.uuid = uuid;
        this.playerName = playerName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        dirty = true;
    }

    public TocHe getTocHe() {
        return tocHe;
    }

    public void setTocHe(TocHe tocHe) {
        this.tocHe = tocHe;
        dirty = true;
    }

    public MonPhai getMonPhai() {
        return monPhai;
    }

    public void setMonPhai(MonPhai monPhai) {
        this.monPhai = monPhai;
        dirty = true;
    }

    public Nhanh getNhanh() {
        return nhanh;
    }

    public void setNhanh(Nhanh nhanh) {
        this.nhanh = nhanh;
        dirty = true;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        dirty = true;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
        dirty = true;
    }

    public int getMaxExp() {
        return level * 100;
    }

    // Tiem nang total = level * 5 + add
    public int getTotalDiemTiemNang() {
        return level * 5 + diemTiemNangAdd;
    }

    public int getUsedDiemTiemNang() {
        return sucManh + thanPhap + noiCong + theLuc;
    }

    public int getAvailableDiemTiemNang() {
        return getTotalDiemTiemNang() - getUsedDiemTiemNang();
    }

    public int getDiemTiemNangAdd() {
        return diemTiemNangAdd;
    }

    public void setDiemTiemNangAdd(int diemTiemNangAdd) {
        this.diemTiemNangAdd = diemTiemNangAdd;
        dirty = true;
    }

    // Ky nang total = level/5 + add
    public int getTotalDiemKyNang() {
        return level / 5 + diemKyNangAdd;
    }

    public int getUsedDiemKyNang() {
        int total = 0;
        for (int v : skillLevels.values()) {
            total += v;
        }
        return total;
    }

    public int getAvailableDiemKyNang() {
        return getTotalDiemKyNang() - getUsedDiemKyNang();
    }

    public int getDiemKyNangAdd() {
        return diemKyNangAdd;
    }

    public void setDiemKyNangAdd(int diemKyNangAdd) {
        this.diemKyNangAdd = diemKyNangAdd;
        dirty = true;
    }

    public int getSucManh() {
        return sucManh;
    }

    public void setSucManh(int sucManh) {
        this.sucManh = sucManh;
        dirty = true;
    }

    public int getThanPhap() {
        return thanPhap;
    }

    public void setThanPhap(int thanPhap) {
        this.thanPhap = thanPhap;
        dirty = true;
    }

    public int getNoiCong() {
        return noiCong;
    }

    public void setNoiCong(int noiCong) {
        this.noiCong = noiCong;
        dirty = true;
    }

    public int getTheLuc() {
        return theLuc;
    }

    public void setTheLuc(int theLuc) {
        this.theLuc = theLuc;
        dirty = true;
    }

    public int getSkillLevel(String skillName) {
        return skillLevels.getOrDefault(skillName, 0);
    }

    public void setSkillLevel(String skillName, int level) {
        skillLevels.put(skillName, level);
        dirty = true;
    }

    public Map<String, Integer> getSkillLevels() {
        return skillLevels;
    }

    public void setSkillLevels(Map<String, Integer> skillLevels) {
        this.skillLevels = skillLevels;
        dirty = true;
    }

    public boolean isTriggerChooseClass() {
        return triggerChooseClass;
    }

    public void setTriggerChooseClass(boolean triggerChooseClass) {
        this.triggerChooseClass = triggerChooseClass;
        dirty = true;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    // Check if player has chosen a toc he
    public boolean hasTocHe() {
        return tocHe != null;
    }

    // Check if player has chosen a mon phai
    public boolean hasMonPhai() {
        return monPhai != null;
    }
}
