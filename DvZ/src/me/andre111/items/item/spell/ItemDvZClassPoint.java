package me.andre111.items.item.spell;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import me.andre111.dvz.manager.HighscoreManager;
import me.andre111.dvz.manager.PlayerScore;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.item.SpellVariable;

public class ItemDvZClassPoint extends ItemSpell {
	/*private String playername = "";
	int points = 0;
	
	@Override
	public void setCastVar(int id, String var) {
		if(id==0) playername = var;
	}
	
	@Override
	public void setCastVar(int id, double var) {
		if(id==1) points = (int) Math.round(var);
	}
	
	@Override
	public void setCastVar(int id, SpellVariable var) {
		if(id==0) playername = var.getAsString();
		else if(id==1) points = var.getAsInt();
	}
	
	@Override
	public boolean cast(Player player, Location loc, Player target, Block block) {
		Player pTarget = Bukkit.getPlayerExact(playername);
		if(playername.equals("")) {
			pTarget = player;
		}
		
		if(pTarget!=null) {
			PlayerScore pscore = HighscoreManager.getPlayerScore(pTarget.getName());
			pscore.setClasspoints(pscore.getClasspoints()+points);
			
			return true;
		}
		
		return false;
	}*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = args.arg(1);
			LuaValue pointsN = args.arg(2);
			
			if(playerN.isstring() && pointsN.isnumber()) {
				Player player = Bukkit.getPlayerExact(playerN.toString());
				int points = pointsN.toint();
				
				if(player!=null) {
					PlayerScore pscore = HighscoreManager.getPlayerScore(player.getName());
					pscore.setClasspoints(pscore.getClasspoints()+points);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
}
