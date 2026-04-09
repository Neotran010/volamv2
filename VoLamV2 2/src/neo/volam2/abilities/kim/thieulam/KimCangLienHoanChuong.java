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
			Location above = location.clone().add(0, 6 + hitCount * 0.5, 0);
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
			Location center = location.clone();
			
			// === Falling block columns (fist slamming down) ===
			// Central pillar (main fist)
			spawnFallingColumn(center.clone(), 8 + hitCount, 0);
			// Surrounding pillars (staggered, like fingers)
			for(int i = 0; i < 5; i++) {
				double angle = (Math.PI * 2 / 5) * i + hitCount * 0.3;
				double dist = 1.2 + U.random(0, 0.8);
				Location pillarLoc = center.clone().add(Math.cos(angle) * dist, 0, Math.sin(angle) * dist);
				spawnFallingColumn(pillarLoc, 6 + hitCount, (i + 1) * 1);
			}
			
			// === Impact shockwave ring expanding outward ===
			new BukkitRunnable() {
				double radius = 0.5;
				int ticks = 0;
				@Override
				public void run() {
					ticks++;
					if(radius > 5 || ticks > 8) {
						this.cancel();
						return;
					}
					for(double d = 0; d < Math.PI * 2; d += Math.PI / (8 + radius * 4)) {
						Location l = center.clone().add(Math.sin(d) * radius, 0.2, Math.cos(d) * radius);
						Particles.DoParticle(l, Particle.SWEEP_ATTACK, 1);
					}
					radius += 0.7;
				}
			}.runTaskTimer(Main.pl, 2, 1);
			
			// === Ground crack particles ===
			for(int i = 0; i < 8; i++) {
				double angle = (Math.PI * 2 / 8) * i;
				for(double d = 0.5; d < 3; d += 0.4) {
					Location l = center.clone().add(Math.cos(angle) * d, 0.05, Math.sin(angle) * d);
					Material crackBlock = (d < 1.5) ? Material.DIAMOND_BLOCK : Material.STONE;
					ParticleNativeCore.loadAPI(Main.pl).LIST_1_13.BLOCK
						.of(crackBlock)
						.packetMotion(false, l, new Vector(U.random(-0.1, 0.1), U.random(0.3, 0.8), U.random(-0.1, 0.1)))
						.sendInRadiusTo(l.getWorld().getPlayers(), 32);
				}
			}
			
			// === Rising dust cloud at impact ===
			Particles.DoParticle(center.clone().add(0, 0.5, 0), Particle.CAMPFIRE_COSY_SMOKE, 8, 1.5, 0.1, 1.5, 0.02);
			Particles.DoParticle(center, Particle.EXPLOSION, 5, 1.5, 0.3, 1.5, 0.5);
			Particles.DoParticle(center.clone().add(0, 1, 0), Particle.CLOUD, 12, 2, 0.5, 2, 0.05);
			
			// === Crit sparkle burst ===
			Particles.DoParticle(center.clone().add(0, 0.5, 0), Particle.CRIT, 20, 2, 1, 2, 0.3);
			
			// === Sound effects (layered for impact) ===
			U.playSound(center, Sound.ENTITY_GENERIC_EXPLODE);
			U.playSound(center, Sound.ENTITY_IRON_GOLEM_HURT);
			U.playSound(center, Sound.BLOCK_ANVIL_PLACE);
		}
		
		/** Spawn a column of falling block particles from height down to ground, with tick delay */
		private void spawnFallingColumn(Location base, int height, int delayTicks) {
			new BukkitRunnable() {
				int currentY = height;
				@Override
				public void run() {
					if(currentY <= 0) {
						this.cancel();
						// Mini impact at column base
						Particles.DoParticle(base.clone().add(0, 0.3, 0), Particle.CRIT, 3, 0.3, 0.2, 0.3, 0.1);
						return;
					}
					Location blockLoc = base.clone().add(0, currentY, 0);
					Material mat = SLAM_BLOCKS[(int)(U.random(0, SLAM_BLOCKS.length - 0.01))];
					
					// Falling block particle
					Vector downward = new Vector(U.random(-0.05, 0.05), -0.8, U.random(-0.05, 0.05));
					ParticleNativeCore.loadAPI(Main.pl).LIST_1_13.BLOCK
						.of(mat)
						.packetMotion(false, blockLoc, downward)
						.sendInRadiusTo(blockLoc.getWorld().getPlayers(), 32);
					
					// Trail particles behind the falling block
					Particles.DoParticle(blockLoc.clone().add(0, 0.5, 0), Particle.CLOUD, 2, 0.15, 0.15, 0.15, 0.01);
					Particles.DoParticle(blockLoc, Particle.END_ROD, 1, 0.1, 0.3, 0.1, 0.01);
					
					currentY -= 2;
				}
			}.runTaskTimer(Main.pl, delayTicks, 1);
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
