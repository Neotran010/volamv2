package neo.volam2.main.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import neo.volam2.data.PlayerData;
import neo.volam2.data.PlayerDataManager;
import neo.volam2.gui.ChonMonPhaiGUI;
import neo.volam2.gui.ChonTocGUI;
import neo.volam2.gui.ThongTinNhanVatGUI;

public class PlayerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cChỉ người chơi mới có thể sử dụng lệnh này!");
            return true;
        }

        Player p = (Player) sender;
        PlayerData data = PlayerDataManager.get(p);
        if (data == null) return true;

        // If player hasn't chosen a race yet, open race selection GUI
        if (!data.hasTocHe()) {
            ChonTocGUI.open(p);
            return true;
        }

        // If player has race but no class, open class selection GUI
        if (!data.hasMonPhai()) {
            ChonMonPhaiGUI.open(p);
            return true;
        }

        ThongTinNhanVatGUI.open(p);
        return true;
    }
}
