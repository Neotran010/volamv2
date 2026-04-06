package neo.volam2.abilities.kim.thieulam;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import neo.volam2.abilities.CooldownSkills;
import neo.volam2.main.DamageU;
import neo.volam2.main.Main;
import neo.volam2.main.Particles;
import neo.volam2.main.Particles.neoParticleColor;

public class HoanhTaoThietLinh extends CooldownSkills {

	public HoanhTaoThietLinh() {
		super("hoanhtaothietlinh", "Hoành Tảo Thiết Lĩnh", true);
	}
	
	public void cast(Player p, Location l, int level) {
		preCastParticle(l);
		
		new BukkitRunnable() {
			double damage = DamageU.getDamage(p, getDamage(level)/100);
			@Override
			public void run() {
				double r = level >= 5 ? 6 : 3;
				particleHit(l, r);
				List<LivingEntity> targets = DamageU.getTargetsAround(p, l, r);
				DamageU.damageSkill(p, targets, damage);
				if(level >= 7) {
					new BukkitRunnable() {
						int t = 0;
						Location loc = l.clone();
						Vector v = loc.getDirection().clone();
						@Override
						public void run() {
							t++;
							if(t > 3) {
								this.cancel();
								return;
							}
							
							Location loc1 = loc.clone().add(v.clone().multiply(t*3));
							particleHit(loc1, 3);
							DamageU.damageSkill(p, DamageU.getTargetsAround(p, loc1, 3), damage);
						}
					}.runTaskTimer(Main.pl, 0, 15);
				}
			}
		}.runTaskLater(Main.pl, 30);
	}
	
	@Override
	public void cast(Player p) {
		int level = getSkillLevel(p);
		if (level <= 0) return;
		Location target = DamageU.getTargetAheadEndLocation(p, 10, 2);
		cast(p, target, level);
		sendDoSkillsMsg(p);
	}
	
	private void particleHit(Location location, double range) {
		Particles.circleBigger(location, Particle.INSTANT_EFFECT, 0.5, range, 0.5, Math.PI/16, 1, Sound.ENTITY_IRON_GOLEM_ATTACK);
	}
	
	private void preCastParticle(Location location) {
		new BukkitRunnable() {
			int c = 0;
			Location start = location.clone();
			Vector v = start.getDirection();
			double d = 0;
			double y = 0;
			@Override
			public void run() {
				c++;
				if(c >= 30) {
					this.cancel();
					return;
				}
				if(c <= 20) {
					d+=Math.PI/6;
					y+=0.03;
					Particles.line(Particles.getCircleLocation(start.clone().add(0,y,0), new Vector(start.getDirection().getZ(), 0, -start.getDirection().getX()), d, 1.5),
							Particles.getCircleLocation(start, new Vector(start.getDirection().getZ(), 0, -start.getDirection().getX()), d + Math.PI/2, 1.5), Particle.DUST_COLOR_TRANSITION, neoParticleColor.YELLOW, 0.3);
				}else {
					Location loc = start.clone().add(0,y,0);
					Location end = loc.clone().add(v.clone().multiply(3)).add(0,(double)((double)(30-c)/10)*3,0);
					Particles.line(loc, end, Particle.DUST_COLOR_TRANSITION, neoParticleColor.YELLOW, 0.3);
				}
			}
		}.runTaskTimer(Main.pl, 1, 1);
	}
	
	public static double getDamage(int level) {
		if(level <= 0) return 0;
		
		return 100 + level*10;
	}

}
