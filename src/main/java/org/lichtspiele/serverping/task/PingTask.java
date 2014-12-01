package org.lichtspiele.serverping.task;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.material.Attachable;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginManager;
import org.lichtspiele.dbb.exception.CustomConfigurationFileNotFoundException;
import org.lichtspiele.serverping.ServerPingSign;
import org.lichtspiele.serverping.event.ServerOfflineEvent;
import org.lichtspiele.serverping.event.ServerOnlineEvent;
import org.lichtspiele.serverping.manager.SignManager;
import org.lichtspiele.serverping.util.PingUtil;

public class PingTask {

	PluginManager pm		= Bukkit.getServer().getPluginManager();
	
    public static Block getAttachedBlock(Block b) {
        MaterialData m = b.getState().getData();
        BlockFace face = BlockFace.DOWN;
        if (m instanceof Attachable) {
            face = ((Attachable) m).getAttachedFace();
        }
        return b.getRelative(face);
    }
	
	@SuppressWarnings("deprecation")
	public PingTask() throws CustomConfigurationFileNotFoundException {
		
		SignManager.load();
		
		Set<String> worlds = SignManager.getWorlds();
		if (worlds == null) return;
		
		for (String world : worlds) {
			
			Set<String> signs = SignManager.getSigns(world);
			if (signs == null) continue;
			
			for (String sign_name : signs) {
				
				HashMap<String, Object> sign_config = SignManager.getSignConfiguration(world, sign_name);
							                                         
				try {
					boolean ping_state = PingUtil.ping(
						(String) sign_config.get("address"),
						Integer.parseInt((String) sign_config.get("port"))
					);
					
					// determine the signs location
					Location location	= new Location(
						Bukkit.getWorld(world),
						Double.parseDouble((String) sign_config.get("X")), 
						Double.parseDouble((String) sign_config.get("Y")),
						Double.parseDouble((String) sign_config.get("Z"))
					);

					
					Block signBlock = Bukkit.getServer().getWorld(world).getBlockAt(location);
					Block target;
					BlockFace face;
					
					switch(signBlock.getData()) {
					case 2:
						face = BlockFace.SOUTH;
						break;
					case 3:
						face = BlockFace.NORTH;
						break;
					case 4:
						face = BlockFace.EAST;
						break;
					default:
					case 5:
						face = BlockFace.WEST;
					}

					target = signBlock.getRelative(face);
					
					Sign sign = (Sign) signBlock.getState();
					ServerPingSign sps = new ServerPingSign(sign.getLines(), signBlock.getLocation(), target);

					if (ping_state) {
						this.pm.callEvent(new ServerOnlineEvent(sps));
					} else {
						this.pm.callEvent(new ServerOfflineEvent(sps));
					}
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
}
