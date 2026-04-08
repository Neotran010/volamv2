package neo.volam2.abilities;

import java.awt.TextComponent;
import java.util.HashMap;
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

import neo.volam2.main.Main;
import neo.volam2.main.U;

/**
 * Listens for sneak + left/right click events to track combo input.
 * Shows current combo progress as action bar title message.
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
        p.sendTitle("§e§l⚡Chiêu thức⚡", comboDisplay, 0, 40, 0);

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
        // When player stops sneaking, clear combo
        if (!e.isSneaking()) {
            UUID uuid = e.getPlayer().getUniqueId();
            ComboData data = combos.remove(uuid);
            if (data != null && data.timeoutTask != null) {
            	e.getPlayer().sendTitle("", "", 0, 0, 0); // Clear action bar //
                data.timeoutTask.cancel();
            }
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
