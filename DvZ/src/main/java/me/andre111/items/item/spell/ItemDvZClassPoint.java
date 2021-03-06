package me.andre111.items.item.spell;

import me.andre111.dvz.manager.HighscoreManager;
import me.andre111.dvz.manager.PlayerScore;
import me.andre111.dvz.utils.PlayerHandler;
import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;

import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemDvZClassPoint extends ItemSpell {
	/*private String playername = "";
	int points = 0;*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue playerN = args.arg(1);
			LuaValue pointsN = args.arg(2);
			
			if(playerN.isstring() && pointsN.isnumber()) {
				Player player = PlayerHandler.getPlayerFromUUID(playerN.toString());
				int points = pointsN.toint();
				
				if(player!=null) {
					PlayerScore pscore = HighscoreManager.getPlayerScore(player.getUniqueId());
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
