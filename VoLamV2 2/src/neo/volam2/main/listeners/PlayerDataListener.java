package neo.volam2.main.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import neo.volam2.data.PlayerData;
import neo.volam2.data.PlayerDataManager;
import neo.volam2.gui.ChonMonPhaiGUI;
import neo.volam2.gui.ChonTocGUI;
import neo.volam2.main.Main;
import neo.volam2.tiemnang.TiemNangManager;

public class PlayerDataListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PlayerDataManager.load(p);

        PlayerData data = PlayerDataManager.get(p);
        if (data == null) return;

        // Apply stats from potential points
        TiemNangManager.applyStats(p);

        // Open the appropriate selection GUI if needed (delayed by 1 tick for safety)
        openSelectionGUIIfNeeded(p);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        PlayerDataManager.remove(p.getUniqueId());
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;
        Player p = (Player) e.getPlayer();

        String title = e.getView().getTitle();
        // Only re-check if closing a selection GUI
        if (title.equals(ChonTocGUI.TITLE) || title.equals(ChonMonPhaiGUI.TITLE)) {
            openSelectionGUIIfNeeded(p);
        }
    }

    /**
     * Opens the appropriate selection GUI if the player still needs to choose.
     * - ChonTocGUI: if player has no tộc hệ (race)
     * - ChonMonPhaiGUI: if player has tộc hệ but no môn phái (class)
     * Delayed by 1 tick to avoid issues with opening inventory during events.
     */
    private void openSelectionGUIIfNeeded(Player p) {
        Bukkit.getScheduler().runTaskLater(Main.pl, () -> {
            if (!p.isOnline()) return;

            PlayerData data = PlayerDataManager.get(p);
            if (data == null) return;

            if (!data.hasTocHe() || data.isTriggerChooseClass()) {
                ChonTocGUI.open(p);
            } else if (!data.hasMonPhai()) {
                ChonMonPhaiGUI.open(p);
            }
        }, 1L);
    }
}
