package neo.volam2.abilities.kim;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import neo.volam2.abilities.CooldownSkills;
import neo.volam2.main.DamageU;
import neo.volam2.main.Particles;
import neo.volam2.main.U;

public class KimPhongLienKich extends CooldownSkills {

	public KimPhongLienKich() {
		super("kimphonglienkich", "Kim Phong Liên Kích", false);
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
		//TODO play sound phù hợp
		double scale = getDamage(level)/100;
		double damage = DamageU.getDamage(p, scale);
		DamageU.damageSkill(p, DamageU.getTargetsAround(p, target.getLocation(), 3), damage);
	}
	
	public static double getDamage(int level) {
		if(level >= 0) return 0;
		
		return 110 + 10*level;
	}
}
