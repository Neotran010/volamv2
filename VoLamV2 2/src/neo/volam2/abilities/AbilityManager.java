package neo.volam2.abilities;

import org.bukkit.entity.Player;

import neo.volam2.data.PlayerDataManager;

public class AbilityManager {
	
	public static int getLevel(Player p, String skillName) {
		return PlayerDataManager.getSkillLevel(p, skillName);
	}
	
}
