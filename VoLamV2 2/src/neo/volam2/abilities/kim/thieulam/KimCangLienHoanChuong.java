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
	
	private static class KimCangAttackData {
		private Location location;
		private Player caster;
		private double damage;
		
		private int timeLeft;
		private int tick = 0;
		public KimCangAttackData(Location location, Player caster, double damage, int timeLeft) {
			super();
			this.location = location;
			this.caster = caster;
			this.damage = damage;
			this.timeLeft = timeLeft;
		}
		
		public void onTick() {
			tick++;
			if(tick >= 5) {
				tick = 0;
				timeLeft--;
				if(timeLeft <= 0) {
					return;
				}
				List<LivingEntity> targets = DamageU.getTargetsAround(caster, location, 3);
				particle();
				DamageU.damageSkill(caster, targets, damage);
				DamageU.stun(targets, 20*1);
			}
		}
		
		private void particle() {
			Location l = location.clone().add(U.random(-3, 3),10,U.random(-3, 3));
			Vector v = location.clone().subtract(l).toVector().normalize();
			ParticleNativeCore.loadAPI(Main.pl).LIST_1_13.BLOCK.of(Material.GOLD_BLOCK).packetMotion(false, l, v);
			Particles.DoParticle(location, Particle.EXPLOSION, 3, 1, 0.2, 1, 1);
			U.playSound(location, Sound.ENTITY_GENERIC_EXPLODE);
		}
		
	}
	
	public void cast(Player p, LivingEntity target, int level) {
		double damage = DamageU.getDamage(p, getDamage(level));
		list.add(new KimCangAttackData(target.getLocation(), p, damage, level >= 7 ? 5 : level >= 5 ? 4 : 3));
	}
	
	@Override
	public void cast(Player p) {
		int level = getSkillLevel(p);
		if (level <= 0) return;
		LivingEntity target = DamageU.getTargetAhead(p, 10, 2);
		if (target == null) {
			p.sendMessage(Main.ABILITIES_PREFIX + "§cKhông thấy mục tiêu!");
			return;
		}
		cast(p, target, level);
		sendDoSkillsMsg(p);
	}
	
	public static double getDamage(int level) {
		if(level <= 0) return 0;
		
		return 100 + level*2;
	}

}
