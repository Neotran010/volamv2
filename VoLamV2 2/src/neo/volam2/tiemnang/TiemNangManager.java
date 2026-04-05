package neo.volam2.tiemnang;

import org.bukkit.entity.Player;

import io.lumine.mythic.lib.api.player.MMOPlayerData;
import io.lumine.mythic.lib.api.stat.SharedStat;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import io.lumine.mythic.lib.player.modifier.ModifierType;
import neo.volam2.data.PlayerData;
import neo.volam2.data.PlayerDataManager;

public class TiemNangManager {

    private static final String MODIFIER_KEY = "volam_tiemnang_";

    // Stat values per point
    private static final double SUC_MANH_DAMAGE = 2.0;       // +2 physical damage per point
    private static final double THAN_PHAP_DODGE = 0.5;       // +0.5% dodge per point
    private static final double THAN_PHAP_CRIT_CHANCE = 0.3; // +0.3% crit chance per point
    private static final double NOI_CONG_MANA = 5.0;         // +5 mana per point
    private static final double NOI_CONG_MAGIC_DMG = 1.5;    // +1.5 magic damage per point
    private static final double THE_LUC_HP = 10.0;           // +10 HP per point
    private static final double THE_LUC_DEFENSE = 0.5;       // +0.5 defense per point

    public static void applyStats(Player p) {
        PlayerData data = PlayerDataManager.get(p);
        if (data == null) return;

        MMOPlayerData mmoData = MMOPlayerData.get(p.getUniqueId());
        if (mmoData == null) return;

        // Remove old modifiers
//        removeStats(p);

        // Suc Manh: +physical damage
        if (data.getSucManh() > 0) {
            double val = data.getSucManh() * SUC_MANH_DAMAGE;
            mmoData.getStatMap().getInstance(SharedStat.ATTACK_DAMAGE)
                .registerModifier(new StatModifier(MODIFIER_KEY + "sucmanh_dmg", SharedStat.ATTACK_DAMAGE, val, ModifierType.FLAT));
        }

        // Than Phap: +dodge, +crit chance
        if (data.getThanPhap() > 0) {
            double dodge = data.getThanPhap() * THAN_PHAP_DODGE;
            double crit = data.getThanPhap() * THAN_PHAP_CRIT_CHANCE;
            mmoData.getStatMap().getInstance(SharedStat.DODGE_RATING)
                .registerModifier(new StatModifier(MODIFIER_KEY + "thanphap_dodge", SharedStat.DODGE_RATING, dodge, ModifierType.ADDITIVE_MULTIPLIER));
            mmoData.getStatMap().getInstance(SharedStat.CRITICAL_STRIKE_CHANCE)
                .registerModifier(new StatModifier(MODIFIER_KEY + "thanphap_crit", SharedStat.CRITICAL_STRIKE_CHANCE, crit, ModifierType.ADDITIVE_MULTIPLIER));
        }

        // Noi Cong: +mana, +magic damage
        if (data.getNoiCong() > 0) {
            double mana = data.getNoiCong() * NOI_CONG_MANA;
            double magicDmg = data.getNoiCong() * NOI_CONG_MAGIC_DMG;
            mmoData.getStatMap().getInstance(SharedStat.MAX_MANA)
                .registerModifier(new StatModifier(MODIFIER_KEY + "noicong_mana", SharedStat.MAX_MANA, mana, ModifierType.FLAT));
            mmoData.getStatMap().getInstance(SharedStat.MAGICAL_DAMAGE)
                .registerModifier(new StatModifier(MODIFIER_KEY + "noicong_magicdmg", SharedStat.MAGICAL_DAMAGE, magicDmg, ModifierType.FLAT));
        }

        // The Luc: +HP, +defense
        if (data.getTheLuc() > 0) {
            double hp = data.getTheLuc() * THE_LUC_HP;
            double def = data.getTheLuc() * THE_LUC_DEFENSE;
            mmoData.getStatMap().getInstance(SharedStat.MAX_HEALTH)
                .registerModifier(new StatModifier(MODIFIER_KEY + "theluc_hp", SharedStat.MAX_HEALTH, hp, ModifierType.FLAT));
            mmoData.getStatMap().getInstance(SharedStat.ARMOR)
                .registerModifier(new StatModifier(MODIFIER_KEY + "theluc_def", SharedStat.ARMOR, def, ModifierType.FLAT));
        }
    }

//    public static void removeStats(Player p) {
//        MMOPlayerData mmoData = MMOPlayerData.get(p.getUniqueId());
//        if (mmoData == null) return;
//
//        String[] keys = {
//            "sucmanh_dmg", "thanphap_dodge", "thanphap_crit",
//            "noicong_mana", "noicong_magicdmg", "theluc_hp", "theluc_def"
//        };
//        String[] stats = {
//            SharedStat.ATTACK_DAMAGE, SharedStat.DODGE_RATING, SharedStat.CRITICAL_STRIKE_CHANCE,
//            SharedStat.MAX_MANA, SharedStat.MAGICAL_DAMAGE, SharedStat.MAX_HEALTH, SharedStat.ARMOR
//        };
//
//        for (int i = 0; i < keys.length; i++) {
//            try {
//                mmoData.getStatMap().getInstance(stats[i])
//                    .removeModifier(MODIFIER_KEY + keys[i]);
//            } catch (Exception ignored) {}
//        }
//    }

    public static boolean addPoint(Player p, TiemNangType type) {
        PlayerData data = PlayerDataManager.get(p);
        if (data == null) return false;
        if (data.getAvailableDiemTiemNang() <= 0) {
            p.sendMessage("§c§l[TIỀM NĂNG] §cBạn không còn điểm tiềm năng!");
            return false;
        }

        switch (type) {
            case SUC_MANH:
                data.setSucManh(data.getSucManh() + 1);
                break;
            case THAN_PHAP:
                data.setThanPhap(data.getThanPhap() + 1);
                break;
            case NOI_CONG:
                data.setNoiCong(data.getNoiCong() + 1);
                break;
            case THE_LUC:
                data.setTheLuc(data.getTheLuc() + 1);
                break;
        }

        applyStats(p);
        p.sendMessage("§a§l[TIỀM NĂNG] §aNâng §f" + type.getDisplayName() + " §athành công!");
        return true;
    }
}
