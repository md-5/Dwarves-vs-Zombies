package me.andre111.dvz.monster.attack;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import me.andre111.dvz.DvZ;
import me.andre111.dvz.Game;
import me.andre111.dvz.monster.MonsterAttack;

public class MonsterBlink extends MonsterAttack {
	private int range = 75;
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==0) range = (int) Math.round(var);
	}
	
	@Override
	public void spellCast(Game game, Player player) {	
		BlockIterator iter; 
		try {
			iter = new BlockIterator(player, range>0&&range<150?range:150);
		} catch (IllegalStateException e) {
			iter = null;
		}
		Block prev = null;
		Block found = null;
		Block b;
		if (iter != null) {
			while (iter.hasNext()) {
				b = iter.next();
				if (DvZ.transparent.contains((byte)b.getTypeId())) {
					prev = b;
				} else {
					found = b;
					break;
				}
			}
		}

		if (found != null) {
			Location loc = null;
			if (range > 0 && !(found.getLocation().distanceSquared(player.getLocation()) < range*range)) {
			} else if (DvZ.isPathable(found.getRelative(0,1,0)) && DvZ.isPathable(found.getRelative(0,2,0))) {
				// try to stand on top
				loc = found.getLocation();
				loc.setY(loc.getY() + 1);
			} else if (prev != null && DvZ.isPathable(prev) && DvZ.isPathable(prev.getRelative(0,1,0))) {
				// no space on top, put adjacent instead
				loc = prev.getLocation();
			}
			if (loc != null) {
				loc.setX(loc.getX()+.5);
				loc.setZ(loc.getZ()+.5);
				loc.setPitch(player.getLocation().getPitch());
				loc.setYaw(player.getLocation().getYaw());
				player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
				player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 0);
				player.teleport(loc);
				player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 0);
				player.sendMessage(DvZ.getLanguage().getString("string_blink","You blink away!"));
			} else {
				player.sendMessage(DvZ.getLanguage().getString("string_cannot_blink","You cannot blink there!"));
				game.setCountdown(player.getName(), 1, 0);
			}
		} else {
			player.sendMessage(DvZ.getLanguage().getString("string_cannot_blink","You cannot blink there!"));
			game.setCountdown(player.getName(), 1, 0);
		}
	}
	
	@Override
	public void spellCastOnLocation(Game game, Player player, Location target) {
		Location loc = target;
	
		if (loc != null) {
			loc.setX(loc.getX()+.5);
			loc.setZ(loc.getZ()+.5);
			loc.setPitch(player.getLocation().getPitch());
			loc.setYaw(player.getLocation().getYaw());
			player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
			player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 0);
			player.teleport(loc);
			player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 0);
			player.sendMessage(DvZ.getLanguage().getString("string_blink","You blink away!"));
		}
	}
	
	@Override
	public int getType() {
		return 0;
	}
}
