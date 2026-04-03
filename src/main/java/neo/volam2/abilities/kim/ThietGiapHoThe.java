package neo.volam2.abilities.kim;

import org.bukkit.entity.Player;

import neo.volam2.abilities.CooldownSkills;

public class ThietGiapHoThe extends CooldownSkills {

	public ThietGiapHoThe() {
		super("thietgiaphothe", "Thiết Giáp Hộ Thể", false);
	}
	
	public double getAddDefense(Player p) {
		return getAddDefense(getSkillLevel(p));
	}
	
	public static double getAddDefense(int level) {
		if(level <= 0) return 0;
		return 5+5*0.1*level;
	}

}
