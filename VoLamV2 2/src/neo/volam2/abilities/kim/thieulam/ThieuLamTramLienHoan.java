package neo.volam2.abilities.kim.thieulam;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import neo.volam2.abilities.CooldownSkills;
import neo.volam2.abilities.beam.AbilitiesBeamManager;
import neo.volam2.abilities.beam.MovementAbilitiesBeam;
import neo.volam2.main.DamageU;
import neo.volam2.main.Main;
import neo.volam2.main.Particles;
import neo.volam2.main.Particles.neoParticleColor;
import neo.volam2.main.U;

public class ThieuLamTramLienHoan extends CooldownSkills {

	public ThieuLamTramLienHoan() {
		super("thieulamtramlienhoan", "Thiếu Lâm Trảm Liên Hoàn", true);
	}
	
	
	public void cast(Player p, int level) {
		new BukkitRunnable() {
			int timeLeft = getAmount(level) + 1;
			double damage = DamageU.getDamage(p, getDamage(level));
			int t = 0;
			@Override
			public void run() {
				t++;
				if(timeLeft <= 0) {
					this.cancel();
					return;
				}
				if(t >= 10) {
					t = 0;
					AbilitiesBeamManager.register(new TLTramBeam(p.getLocation().clone().add(0,1,0), p.getLocation().getDirection().clone().setY(0), p, damage, U.getRandom(1, 2)));
				}
			}
		}.runTaskTimer(Main.pl, 0, 1);
	}
	
	public static int getAmount(int level) {
		if(level <= 0) return 0;
		
		return level >=7 ? 7 : level >=4 ? 5 : 3;
	}
	
	public static double getDamage(int level) {
		if(level <= 0) return 0;
		
		return 100 + level*5;
	}
	
	private static class TLTramBeam extends MovementAbilitiesBeam {
		
		private int type;

		public TLTramBeam(Location location, Vector direction, Player caster, double damage, int type) {
			super(location, direction, caster, damage, 16, 1);
			this.type = type;
		}
		
		@Override
		protected void particle() {
			switch (type) {
			case 1:
				Particles.doChemNgang(Particle.FLAME, getLocation(), getDirection(), 2, 1.5);
				break;
			case 2:
				Particles.getChemNghieng1(Particle.FLAME, getLocation(), getDirection(), neoParticleColor.RED, Math.PI/16);
				break;

			default:
				break;
			}
		}
		
	}

}
