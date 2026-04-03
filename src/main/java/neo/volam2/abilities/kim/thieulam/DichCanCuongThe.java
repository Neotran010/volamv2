package neo.volam2.abilities.kim.thieulam;

import neo.volam2.abilities.CooldownSkills;

public class DichCanCuongThe extends CooldownSkills {

	public DichCanCuongThe() {
		super("dichcancuongthe", "Dịch Cân Cường Thể", false);
	}
	
	public static double getAddTheChat(int level) {
		if(level <= 0) return 0;
		return 50 + level*5;
	}

	public static double getAddSinhKhi(int level) {
		if(level <= 0) return 0;
		return 50 + level*5;
	}

}
