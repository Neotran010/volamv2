package neo.volam2.abilities.kim.thieulam;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import neo.volam2.abilities.CooldownSkills;
import neo.volam2.main.DamageU;
import neo.volam2.main.Particles;
import neo.volam2.main.Particles.neoParticleColor;
import neo.volam2.main.U;

public class PhanChanKimCang extends CooldownSkills {

	public PhanChanKimCang() {
		super("phanchankimcang", "Phản Chấn Kim Cang", false);
	}
	
	/**
	 * 
	 * @param blockAmount số lượng damage mà block đã chặn
	 */
	public void onBlock(Player p, LivingEntity target, double blockAmount) {
		if(isCooldown(p)) return;
		int level = getSkillLevel(p);
		if(level <= 0) return;
		
		double rate = getRate(level);
		if(U.random(0, 100) < rate) {
			setCooldown(p);
			double rateDamage = getRateDamage(level);
			double damage = blockAmount*rateDamage;
			DamageU.damageSkill(p, target, damage);
			Particles.line(p.getLocation().clone().add(0,1,0), target.getLocation().clone().add(0,1,0), Particle.DUST_COLOR_TRANSITION, neoParticleColor.YELLOW, 0.5);
			//TODO sound phù hợp
		}
	}
	
	@Override
	public double getCooldown() {
		return 1;
	}
	
	public static double getRate(int level) {
		if(level <= 0) return 0;
		return 30+level*2;
	}
	
	public static double getRateDamage(int level) {
		if(level <= 0) return 0;
		return 50+level*3;
	}
	
	/**
	 * 
	 * @return tính theo % máu
	 */
	public static double getMaxDamageRate(int level) {
		if(level <= 0) return 0;
		return 15+level*1;
	}

}
