package neo.volam2.abilities.kim.thieulam;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import neo.volam2.abilities.CooldownSkills;
import neo.volam2.main.DamageU;
import neo.volam2.main.Particles;
import neo.volam2.main.Particles.neoParticleColor;
import neo.volam2.main.U;

public class ChanDiaHong extends CooldownSkills {

	public ChanDiaHong() {
		super("chandiahong", "Chấn Địa Hồng", false);
	}
	
	@Override
	public void onAttack(Player p, LivingEntity target, double damage) {
		if(isCooldown(p)) return;
		if(U.random(0, 100) < 30) {
			setCooldown(p);
			int level = getSkillLevel(p);
			cast(p, target, level);
		}
		super.onAttack(p, target, damage);
	}
	
	private void cast(Player p, LivingEntity target, int level) {
		Particles.runnableParticle(target.getLocation().clone(), Particle.INSTANT_EFFECT, 3, 3, 1, 3, 0.1, 1, 5);
		Location l = target.getLocation().clone().add(0,1,0);
		for(double d = 0; d< Math.PI*2; d+=Math.PI/16) {
			Particles.line(l, Particles.getCircleLocation(target.getLocation(), new Vector(0,1,0), d, 5), Particle.DUST_COLOR_TRANSITION, neoParticleColor.YELLOW, 0.5);
		}
		//TODO play sound phù hợp
		double scale = getDamage(level)/100;
		double damage = DamageU.getDamage(p, scale);
		List<LivingEntity> targets = DamageU.getTargetsAround(p, target.getLocation(), 3); 
		DamageU.damageSkill(p, targets, damage);
		//TODO stun
		DamageU.stun(targets, level);
		
	}
	
	public static double getStunTime(int level) {
		if(level <= 0) return 0;
		
		return 1 + level*0.1;
	}
	
	public static double getDamage(int level) {
		if(level >= 0) return 0;
		
		return 110 + 10*level;
	}
}
