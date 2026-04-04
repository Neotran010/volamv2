package neo.volam2.abilities.kim;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import io.lumine.mythic.lib.api.stat.SharedStat;
import neo.volam2.abilities.StatsBoostEffect;
import neo.volam2.abilities.boost.BoostNeoSkill;
import neo.volam2.main.Main;
import neo.volam2.main.Particles;
import neo.volam2.main.Particles.neoParticleColor;

public class KimCangHoThanQuyet extends BoostNeoSkill {

	
	public KimCangHoThanQuyet() {
		super("kimcanghothanquyet", "Kim Cang Hộ Thân Quyết", true, neoParticleColor.YELLOW, 20*6);
	}

	@Override
	public BoostData createBoostData(Player p) {
		int level = getSkillLevel(p);
		StatsBoostEffect.addStatsBoostEffect(p, SharedStat.ARMOR, getArmorValue(level), getBoostTickTime());
		StatsBoostEffect.addStatsBoostEffect(p, SharedStat.DAMAGE_REDUCTION, getDamageReductionValue(level), getBoostTickTime());
		return getPotionsBoostData(p);
	}

	@Override
	public PotionsBoostData getPotionsBoostData(Player p) {
		List<PotionEffect> pe = new ArrayList<PotionEffect>();
		pe.add(new PotionEffect(PotionEffectType.RESISTANCE, getBoostTickTime(), getSkillLevel(p)/2));
		pe.add(new PotionEffect(PotionEffectType.HEALTH_BOOST, getBoostTickTime(), getSkillLevel(p)/2));
		return new PotionsBoostData(pe, getBoostTickTime());
	}
	
	@Override
	protected void particleOnCast(Player p) {
		new BukkitRunnable() {
			double d = 0;
			@Override
			public void run() {
				d+=0.1;
				if(d >= 2.5) {
					this.cancel();
					return;
				}
				Particles.circle(p.getLocation().clone().add(0,d,0), Particle.DUST_COLOR_TRANSITION, 1, Math.PI/12, neoParticleColor.YELLOW);
			}
		}.runTaskTimer(Main.pl, 0, 1);
		super.particleOnCast(p);
	}
	
	int tick = 0;
	
	@Override
	protected void particle(Player p) {
		tick++;
		int level = getSkillLevel(p);
		Location l = p.getLocation().clone().add(0,1,0);
		if(level >= 8 && tick > 20) {
			tick = 0;
			Particles.circle(l, Particle.DUST_COLOR_TRANSITION, 1.5, Math.PI/12, neoParticleColor.YELLOW);
			Particles.circle(l.clone().add(0,0.5,0), Particle.DUST_COLOR_TRANSITION, 0.7, Math.PI/12, neoParticleColor.YELLOW);
			Particles.circle(l.clone().add(0,-0.5,0), Particle.DUST_COLOR_TRANSITION, 0.7, Math.PI/12, neoParticleColor.YELLOW);
			Particles.circle(l, Particle.DUST_COLOR_TRANSITION, 1.5, Math.PI/12, neoParticleColor.YELLOW);
		}else if(level >= 5 && tick > 20) {
			tick = 0;
			Particles.circle(l, Particle.DUST_COLOR_TRANSITION, 1, Math.PI/12, neoParticleColor.YELLOW);
		}
		Particles.spawnParticleColor(l, Particle.DUST_COLOR_TRANSITION, neoParticleColor.YELLOW, 2, 0.1, 0.1, 0.1);
	}

	private static double getDamageReductionValue(int level) {
		if(level <= 0) return 0;
		return 5 + 5*0.1*level;
	}

	private static double getArmorValue(int level) {
		if(level <= 0) return 0;
		return 5 + 5*0.1*level;
	}
	
}
