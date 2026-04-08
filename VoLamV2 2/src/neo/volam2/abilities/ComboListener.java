package neo.volam2.abilities;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import neo.volam2.data.MonPhai;
import neo.volam2.data.Nhanh;
import neo.volam2.data.PlayerData;
import neo.volam2.data.PlayerDataManager;
import neo.volam2.main.Main;
import neo.volam2.main.U;

/**
 * Listens for sneak + left/right click events to track combo input.
 * Shows current combo progress as action bar title message.
 * When sneak is released, matches combo against skills and casts if valid.
 */
public class ComboListener implements Listener {

    private static final Map<UUID, ComboData> combos = new HashMap<>();
    /** Max time between combo inputs in ticks (1.5 seconds) */
    private static final long COMBO_TIMEOUT_TICKS = 30L;
    /** Max combo length */
    private static final int MAX_COMBO_LENGTH = 5;

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!p.isSneaking()) return;

        Action action = e.getAction();
        char key;
        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            key = 'L';
        } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            key = 'R';
        } else {
            return;
        }

        UUID uuid = p.getUniqueId();
        ComboData data = combos.get(uuid);

        if (data == null) {
            data = new ComboData();
            combos.put(uuid, data);
        }

        // Cancel existing timeout task
        if (data.timeoutTask != null) {
            data.timeoutTask.cancel();
        }

        // Add key to combo
        data.combo.append(key);

        // Trim if exceeds max length
        if (data.combo.length() > MAX_COMBO_LENGTH) {
            data.combo = new StringBuilder(data.combo.substring(data.combo.length() - MAX_COMBO_LENGTH));
        }

        // Show combo progress as action bar
        String comboDisplay = formatComboDisplay(data.combo.toString());
        p.sendTitle("§e§l⚡Chiêu thức⚡", comboDisplay, 0, 40, 0); //

        // Schedule timeout to clear combo
        data.timeoutTask = new BukkitRunnable() {
            @Override
            public void run() {
                combos.remove(uuid);
            }
        };
        data.timeoutTask.runTaskLater(Main.pl, COMBO_TIMEOUT_TICKS);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        ComboData data = combos.remove(uuid);
        if (data != null && data.timeoutTask != null) {
            data.timeoutTask.cancel();
        }
    }

    @EventHandler
    public void onToggleSneak(PlayerToggleSneakEvent e) {
        // When player stops sneaking, try to match combo and cast skill
        if (!e.isSneaking()) {
            UUID uuid = e.getPlayer().getUniqueId();
            ComboData data = combos.remove(uuid);
            if (data != null) {
                if (data.timeoutTask != null) {
                    data.timeoutTask.cancel();
                }
                String combo = data.combo.toString();
                if (!combo.isEmpty()) {
                    tryMatchAndCastSkill(e.getPlayer(), combo);
                    e.getPlayer().sendTitle("", "", 0, 0, 0); // Clear action bar
                }
            }
        }
    }

    /**
     * Attempts to match a combo string against available skills and cast the matched skill.
     * Flow: match combo → check skill learned → check level → check cooldown → check cost → cast → set cooldown
     */
    private void tryMatchAndCastSkill(Player p, String combo) {
        PlayerData pd = PlayerDataManager.get(p);
        if (pd == null) return;

        MonPhai monPhai = pd.getMonPhai();
        if (monPhai == null) return;

        Nhanh nhanh = pd.getNhanh();

        // Get skill definitions for the player's class
        List<SkillInfo> skillInfos = getSkillInfosForMonPhai(monPhai);

        // Find the matching skill
        SkillInfo matchedSkill = null;
        for (SkillInfo si : skillInfos) {
            if (si.getComboKey() == null) continue;
            if (!si.getComboKey().equals(combo)) continue;
            // Branch check: null = shared (all branches), otherwise must match player's branch
            if (si.getBranch() != null && !si.getBranch().equals(nhanh)) continue;
            matchedSkill = si;
            break;
        }

        if (matchedSkill == null) return;

        // Check if player has learned the skill (skill level > 0)
        int skillLevel = pd.getSkillLevel(matchedSkill.getId());
        if (skillLevel <= 0) {
            p.sendMessage(Main.ABILITIES_PREFIX + "§cBạn chưa học kỹ năng §f" + matchedSkill.getDisplayName() + "§c!");
            return;
        }

        // Check player level requirement
        if (pd.getLevel() < matchedSkill.getRequiredLevel()) {
            p.sendMessage(Main.ABILITIES_PREFIX + "§cBạn chưa đủ cấp độ! Cần cấp §f" + matchedSkill.getRequiredLevel() + "!");
            return;
        }

        // Get the NeoSkill runtime instance
        NeoSkill neoSkill = NeoSkills.get(matchedSkill.getId());
        if (neoSkill == null) {
            p.sendMessage(Main.ABILITIES_PREFIX + "§cKỹ năng chưa được triển khai!");
            return;
        }

        // Check cooldown
        if (neoSkill.isCooldown(p)) {
            if (neoSkill instanceof CooldownSkills) {
                ((CooldownSkills) neoSkill).sendCDMessage(p);
            }
            return;
        }

        // Check cost (HP/MP)
        if (neoSkill instanceof CooldownSkills) {
            if (!((CooldownSkills) neoSkill).cost(p, 0, (int) matchedSkill.getManaCost())) {
                return;
            }
        }

        // Cast the skill
        neoSkill.cast(p);

        // Set cooldown using SkillInfo's cooldown value
        if (neoSkill instanceof CooldownSkills && matchedSkill.getCooldown() > 0) {
            ((CooldownSkills) neoSkill).setCooldown(p, matchedSkill.getCooldown());
        }
    }

    /**
     * Gets the skill definitions for a given MonPhai (class).
     */
    private static List<SkillInfo> getSkillInfosForMonPhai(MonPhai monPhai) {
        switch (monPhai) {
            case THIEU_LAM:
                return ThieuLamSkills.getAllSkillInfos();
            // Add other classes here as they are implemented
            default:
                return Collections.emptyList();
        }
    }

    /**
     * Gets the current combo string for a player (e.g., "LRL").
     * Returns null if no combo is active.
     */
    public static String getCurrentCombo(UUID uuid) {
        ComboData data = combos.get(uuid);
        if (data == null || data.combo.length() == 0) return null;
        return data.combo.toString();
    }

    /**
     * Clears the combo for a player (after skill execution).
     */
    public static void clearCombo(UUID uuid) {
        ComboData data = combos.remove(uuid);
        if (data != null && data.timeoutTask != null) {
            data.timeoutTask.cancel();
        }
    }

    /**
     * Formats combo string "LRL" into colored display with arrows.
     */
    private static String formatComboDisplay(String combo) {
        return U.formatComboKey(combo);
    }

    private static class ComboData {
        StringBuilder combo = new StringBuilder();
        BukkitRunnable timeoutTask;
    }
}
