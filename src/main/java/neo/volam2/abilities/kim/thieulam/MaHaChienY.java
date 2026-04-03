package neo.volam2.abilities.kim.thieulam;

import neo.volam2.abilities.CooldownSkills;

public class MaHaChienY extends CooldownSkills {

	public MaHaChienY() {
		super("mahachieny", "Ma Ha Chiến Ý", false);
	}

	public static double getAddCritChanceValue(int level) {
		if(level <= 0) return 0;
		return 5 + 5*0.1*level;
	}

	public static double getAddCritDamageValue(int level) {
		if(level <= 0) return 0;
		return 15 + 15*0.1*level;
	}
	

}
