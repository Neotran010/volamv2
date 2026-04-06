package neo.volam2.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import neo.volam2.abilities.ComboListener;
import neo.volam2.abilities.NeoSkills;
import neo.volam2.abilities.NeoSkillsListener;
import neo.volam2.data.DatabaseManager;
import neo.volam2.data.PlayerDataManager;
import neo.volam2.gui.GUIListener;
import neo.volam2.main.commands.KyNangCommand;
import neo.volam2.main.commands.PlayerCommand;
import neo.volam2.main.commands.TiemNangCommand;
import neo.volam2.main.commands.VolamCommand;
import neo.volam2.main.listeners.PlayerDataListener;

public class Main extends JavaPlugin 
{
	
	public static final String ABILITIES_PREFIX = "[ KỸ NĂNG ] ";
	public static Main pl;
	
	private DatabaseManager databaseManager;
	
	@Override
	public void onEnable() {
		pl = this;
		
		// Initialize database
		databaseManager = new DatabaseManager(this);
		databaseManager.init();
		
		// Initialize player data manager (auto-save scheduler)
		PlayerDataManager.init(databaseManager);
		
		// Initialize skill system
		NeoSkills.setup();
		
		// Register listeners
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new GUIListener(), this);
		pm.registerEvents(new NeoSkillsListener(), this);
		pm.registerEvents(new PlayerDataListener(), this);
		pm.registerEvents(new ComboListener(), this);
		
		// Register commands
		getCommand("kynang").setExecutor(new KyNangCommand());
		getCommand("tiemnang").setExecutor(new TiemNangCommand());
		getCommand("nhanvat").setExecutor(new PlayerCommand());
		getCommand("volam").setExecutor(new VolamCommand());
		
		getLogger().info("VoLamV2 đã được kích hoạt thành công!");
	}
	
	@Override
	public void onDisable() {
		// Save all player data
		PlayerDataManager.saveAll();
		
		// Close database connection
		if (databaseManager != null) {
			databaseManager.close();
		}
		
		getLogger().info("VoLamV2 đã tắt, dữ liệu đã được lưu.");
	}

}
