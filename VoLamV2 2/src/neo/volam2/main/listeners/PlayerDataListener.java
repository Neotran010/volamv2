package neo.volam2.main.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import neo.volam2.data.PlayerData;
import neo.volam2.data.PlayerDataManager;
import neo.volam2.gui.ChonTocGUI;
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

        // If the player needs to choose a class, open selection GUI
        if (!data.hasTocHe() || !data.hasMonPhai() || data.isTriggerChooseClass()) {
            ChonTocGUI.open(p);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        PlayerDataManager.remove(p.getUniqueId());
    }
}
