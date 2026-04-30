package neo.volam2.abilities.kim.thieulam;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.github.fierioziy.particlenativeapi.core.ParticleNativeCore;

import neo.volam2.abilities.CooldownSkills;
import neo.volam2.main.DamageU;
import neo.volam2.main.Main;
import neo.volam2.main.Particles;
import neo.volam2.main.U;

public class KimCangLienHoanChuong extends CooldownSkills {

	public KimCangLienHoanChuong() {
		super("kimcanglienhoanchuong", "Kim Cang Liên Hoàn Chưởng", false);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(KimCangAttackData dt : new ArrayList<>(list)) {
					if(dt.timeLeft <= 0) {
						list.remove(dt);
						continue;
					}
					dt.onTick();
				}
			}
		}.runTaskTimer(Main.pl, 0, 1);
	}
	
	private static List<KimCangAttackData> list = new ArrayList<KimCangLienHoanChuong.KimCangAttackData>();
	
	private static final Material[] SLAM_BLOCKS = {
		Material.DIAMOND_BLOCK, Material.IRON_BLOCK, Material.GOLD_BLOCK,
		Material.LAPIS_BLOCK, Material.EMERALD_BLOCK
	};
	
	private static class KimCangAttackData {
		private Location location;
		private Player caster;
		private double damage;
		
		private int timeLeft;
		private int tick = 0;
		private int hitCount = 0;
		public KimCangAttackData(Location location, Player caster, double damage, int timeLeft) {
			super();
			this.location = location;
			this.caster = caster;
			this.damage = damage;
			this.timeLeft = timeLeft;
		}
		
		public void onTick() {
			tick++;
			
			// Warning phase: ground circle + gathering particles (ticks 1-4 before each hit)
			if(tick == 1) {
				warningParticle();
			}
			if(tick >= 2 && tick <= 4) {
				gatherParticle();
			}
			
			if(tick >= 5) {
				tick = 0;
				timeLeft--;
				hitCount++;
				if(timeLeft <= 0) {
					return;
				}
				List<LivingEntity> targets = DamageU.getTargetsAround(caster, location, 3);
				slamParticle();
				DamageU.damageSkill(caster, targets, damage);
				DamageU.stun(targets, 20*1);
			}
		}
		
		/** Phase 1: Warning circle on ground before each slam */
		private void warningParticle() {
			// Glowing circle on ground
			double radius = 2.5;
			for(double d = 0; d < Math.PI * 2; d += Math.PI / 16) {
				Location l = location.clone().add(Math.sin(d) * radius, 0.1, Math.cos(d) * radius);
				Particles.DoParticle(l, Particle.END_ROD, 1);
			}
			// Inner cross pattern
			for(double i = -radius; i <= radius; i += 0.5) {
				Particles.DoParticle(location.clone().add(i, 0.1, 0), Particle.CRIT, 1);
				Particles.DoParticle(location.clone().add(0, 0.1, i), Particle.CRIT, 1);
			}
			U.playSound(location, Sound.BLOCK_ANVIL_LAND);
		}
		
		/** Phase 2: Particles gathering above the target (fist forming) */
		private void gatherParticle() {
			Location above = location.clone().add(0, 6 + Math.min(hitCount * 0.5, 3.0), 0);
			// Converging particles toward the gathering point
			for(int i = 0; i < 6; i++) {
				Location start = above.clone().add(U.random(-4, 4), U.random(-2, 3), U.random(-4, 4));
				Vector v = above.clone().subtract(start).toVector().normalize();
				double distance = start.distance(above);
				Particles.movingParticle(start, Particle.ENCHANTED_HIT, v, distance / 10.0);
			}
			// Swirling dust around the gathering point
			for(int i = 0; i < 3; i++) {
				double angle = U.random(0, Math.PI * 2);
				double r = 1.5;
				Location l = above.clone().add(Math.cos(angle) * r, U.random(-0.5, 0.5), Math.sin(angle) * r);
				Particles.DoParticle(l, Particle.CLOUD, 1, 0.1, 0.1, 0.1, 0.02);
			}
		}
		
		/** Phase 3: Main slam - columns of blocks falling from sky + impact */
		private void slamParticle() {
			Location center = location.clone().add(0, 0.1, 0);
			Location above = location.clone().add(U.random(-3, 3), 6, U.random(-3, 3));
			Vector v = center.clone().subtract(above).toVector().normalize();
			for(double d = 0; d < 6; d+=0.5) {
				Location spawn = above.clone().add(v.clone().multiply(d));
				ParticleNativeCore.loadAPI(Main.pl).LIST_1_13.BLOCK_MARKER.of(Material.DIAMOND_BLOCK).packet(true, spawn, 0.01, 0.02, 0.01, 1, 5).sendInRadiusTo(location.getWorld().getPlayers(), 32);;
			}
			
			U.playSound(center, Sound.ENTITY_GENERIC_EXPLODE);
			U.playSound(center, Sound.ENTITY_IRON_GOLEM_HURT);
			U.playSound(center, Sound.BLOCK_ANVIL_PLACE);
		}
		
		
	}
	
	@Override
	public void cast(Player p) {
		if(isCooldown(p)) {
			sendCDMessage(p);
			return;
		}
		LivingEntity target = DamageU.getTargetAhead(p, 16, 1);
		if(target == null) {
			p.sendMessage("§cKhông tìm thấy mục tiêu!");
			return;
		}

		setCooldown(p);
		cast(p, target, getSkillLevel(p));
	}
	
	public void cast(Player p, LivingEntity target, int level) {
		double damage = DamageU.getDamage(p, getDamage(level));
		list.add(new KimCangAttackData(target.getLocation(), p, damage, level >= 7 ? 5 : level >= 5 ? 4 : 3));
	}
	
	public static double getDamage(int level) {
		if(level <= 0) return 0;
		
		return 100 + level*2;
	}

}