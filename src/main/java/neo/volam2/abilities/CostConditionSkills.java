package neo.volam2.abilities;

import org.bukkit.entity.Player;

import neo.volam2.main.Main;

public class CostConditionSkills extends ConditionSkills {
	
	private int mpCost;
	private int hpCost;

	public CostConditionSkills(String name, String displayName, boolean msg, int mpCost, int hpCost) {
		super(name, displayName, msg);
		this.mpCost = mpCost;
		this.hpCost = hpCost;
	}

	@Override
	public boolean condition(Player p) {
		// Kiểm tra HP cost
		if (hpCost > 0 && p.getHealth() <= hpCost) {
			p.sendMessage(Main.ABILITIES_PREFIX + "§cKhông đủ HP! Cần: " + hpCost);
			return false;
		}
		
		//TODO cost
		
		return true;
	}
	
	@Override
	public void cast(Player p) {
		// Trừ HP nếu cần
		//TODO cost
		
		super.cast(p);
	}
	
	public int getMpCost() { return mpCost; }
	public int getHpCost() { return hpCost; }

}
