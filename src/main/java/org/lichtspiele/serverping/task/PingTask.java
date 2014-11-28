package org.lichtspiele.serverping.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.plugin.PluginManager;
import org.lichtspiele.dbb.exception.CustomConfigurationFileNotFoundException;
import org.lichtspiele.serverping.event.ServerOfflineEvent;
import org.lichtspiele.serverping.event.ServerOnlineEvent;
import org.lichtspiele.serverping.manager.SignManager;
import org.lichtspiele.serverping.util.BlockFindUtil;
import org.lichtspiele.serverping.util.PingUtil;

public class PingTask {

	PluginManager pm		= Bukkit.getServer().getPluginManager();
	
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

					// clone sign to get the block where it has been attached to
					Sign sign = (Sign) location.getBlock().getState();					
					org.bukkit.material.Sign ms = (org.bukkit.material.Sign) sign.getData();
					Block attached = location.getBlock().getRelative(ms.getAttachedFace());

					// define the list of search blocks (REDSTONE_WIRE) and search for them in the near area (radius 1)
					@SuppressWarnings({ "serial" })
					ArrayList<Integer> search_blocks	= new ArrayList<Integer>() {{ add(Material.REDSTONE_WIRE.getId() ); }};
					ArrayList<Block> redstone_wires 	= BlockFindUtil.getClosestBlocks(attached.getLocation(), search_blocks, 1);

					if (ping_state) {
						System.out.println("online");
						this.pm.callEvent(new ServerOnlineEvent(sign, location, redstone_wires));
					} else {
						System.out.println("offline");
						this.pm.callEvent(new ServerOfflineEvent(sign, location, redstone_wires));
					}
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
}
