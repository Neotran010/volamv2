package neo.volam2.abilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.entity.Player;

import io.lumine.mythic.lib.api.stat.SharedStat;

public class StatsBoostEffect {

	private static final List<StatsBoostData> list = new ArrayList<>();

	/*
	 * Class lưu trữ danh sách hiệu ứng statsEffect của player
	 */
	static final class StatsBoostData {
		private final UUID playerId;
		private final String stat;
		private final double value;
		private double timeLeft;

		public StatsBoostData(Player p, String stat, double value, double timeLeft) {
			this.playerId = Objects.requireNonNull(p, "player").getUniqueId();
			this.stat = Objects.requireNonNull(stat, "stat");
			this.value = value;
			this.timeLeft = timeLeft;
		}

		public boolean matches(Player p, String stat) {
			return p != null && stat != null && playerId.equals(p.getUniqueId()) && this.stat.equals(stat);
		}

		public UUID getPlayerId() {
			return playerId;
		}

		public String getStat() {
			return stat;
		}

		public double getValue() {
			return value;
		}

		public double getTimeLeft() {
			return timeLeft;
		}

		public void setTimeLeft(double timeLeft) {
			this.timeLeft = timeLeft;
		}
	}

	public static void addStatsBoostEffect(Player p, SharedStat stat, double value, double duration, boolean override) {
		if (p == null || stat == null) return;
		addStatsBoostEffect(p, stat.toString(), value, duration, override);
	}

	public static void addStatsBoostEffect(Player p, String stat, double value, double duration, boolean override) {
		if (p == null || stat == null || stat.isBlank()) return;
		if (duration <= 0) return;

		StatsBoostData existing = null;
		for (StatsBoostData d : list) {
			if (d.matches(p, stat)) {
				existing = d;
				break;
			}
		}

		if (existing == null) {
			list.add(new StatsBoostData(p, stat, value, duration));
			return;
		}

		if (!override) return;

		boolean shouldReplace = value > existing.value || duration > existing.timeLeft;
		if (shouldReplace) {
			list.remove(existing);
			list.add(new StatsBoostData(p, stat, value, duration));
		}
	}

	public static void addStatsBoostEffect(Player p, SharedStat stat, double value, double duration) {
		addStatsBoostEffect(p, stat, value, duration, true);
	}

	public static void addStatsBoostEffect(Player p, String stat, double value, double duration) {
		addStatsBoostEffect(p, stat, value, duration, true);
	}

	public static void removeStatsBoostEffect(Player p, SharedStat stat) {
		if (p == null || stat == null) return;
		removeStatsBoostEffect(p, stat.toString());
	}

	public static void removeStatsBoostEffect(Player p, String stat) {
		if (p == null || stat == null || stat.isBlank()) return;
		list.removeIf(d -> d.matches(p, stat));
	}

	public static void clear(Player p) {
		if (p == null) return;
		UUID id = p.getUniqueId();
		list.removeIf(d -> d.playerId.equals(id));
	}

	public static void tick(double deltaSeconds) {
		if (deltaSeconds <= 0) return;

		for (Iterator<StatsBoostData> it = list.iterator(); it.hasNext();) {
			StatsBoostData d = it.next();
			d.timeLeft -= deltaSeconds;
			if (d.timeLeft <= 0) it.remove();
		}
	}

	public static double getTotalBoost(Player p, SharedStat stat) {
		if (p == null || stat == null) return 0D;
		return getTotalBoost(p, stat.toString());
	}

	public static double getTotalBoost(Player p, String stat) {
		if (p == null || stat == null || stat.isBlank()) return 0D;

		double sum = 0D;
		for (StatsBoostData d : list) {
			if (d.matches(p, stat)) sum += d.value;
		}
		return sum;
	}
}