package org.lichtspiele.serverping.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_7_R2.block.CraftSign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.lichtspiele.dbb.exception.CustomConfigurationFileNotFoundException;
import org.lichtspiele.dbb.exception.TranslationNotFoundException;
import org.lichtspiele.serverping.Messages;
import org.lichtspiele.serverping.ServerPing;
import org.lichtspiele.serverping.manager.SignManager;

public class BlockBreakListener implements Listener {
	
	private Messages messages;
	
	public BlockBreakListener() {
		super();
		this.messages = (Messages) ServerPing.getInstance().getMessages();
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onBlockBreak (BlockBreakEvent event) {
		
		if (event.getBlock().getType().equals(Material.WALL_SIGN)) {
			Sign sign = new CraftSign(event.getBlock());
			if (sign.getLine(0).trim().equals("[ServerPing]")) {
				this.handle(event, sign);
			}
		}
		
		BlockFace[] bf = new BlockFace[] {
		    BlockFace.EAST,
		    BlockFace.SOUTH,
		    BlockFace.WEST,
		    BlockFace.NORTH
		};
		Block b;
		for (int i = 3; i >= 0; i--) {
		    b = event.getBlock().getRelative(bf[(i)]);
		
		    if (b.getType().equals(Material.WALL_SIGN)) {
		    	
				Sign sign = new CraftSign(b);
				if (sign.getLine(0).trim().equals("[ServerPing]")) {
					this.handle(event, (Sign) sign);
					break;
				}
			}
		}
	}
	
	private void handle(BlockBreakEvent event, Sign sign) {
		if (!event.getPlayer().hasPermission("serverping.destroy")) {
			try {
				this.messages.insufficientPermission(event.getPlayer(), "serversign.destroy");
			} catch (TranslationNotFoundException e) {
				this.messages.missingTranslationFile(event.getPlayer(), ServerPing.getInstance().getConfig().getString("locale"));
				ServerPing sp = (ServerPing) Bukkit.getPluginManager().getPlugin("ServerPing");
				sp.disable(e, event.getPlayer());
			}
		}

		try {
			SignManager.unregister(event.getPlayer().getWorld().getName(), sign.getLine(1));
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (CustomConfigurationFileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// tell the player what happened
		try {
			this.messages.signDestroyed(event.getPlayer(), sign.getLine(1));
		} catch (TranslationNotFoundException e) {
			this.messages.missingTranslationFile(event.getPlayer(), ServerPing.getInstance().getConfig().getString("locale"));
			ServerPing sp = (ServerPing) Bukkit.getPluginManager().getPlugin("ServerPing");
			sp.disable(e, event.getPlayer());
			
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}			
	}
	

	
}