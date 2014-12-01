package org.lichtspiele.serverping.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.lichtspiele.dbb.exception.TranslationNotFoundException;
import org.lichtspiele.serverping.Messages;
import org.lichtspiele.serverping.ServerPing;
import org.lichtspiele.serverping.ServerPingSign;
import org.lichtspiele.serverping.event.ServerInitEvent;
import org.lichtspiele.serverping.exception.SignAlreadyExistsException;
import org.lichtspiele.serverping.manager.SignManager;

public class SignChangeListener implements Listener {
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void Signit(SignChangeEvent event) {

		if (!event.getLine(0).trim().equals("[ServerPing]")) return;

		Messages messages = (Messages) ServerPing.getInstance().getMessages();
		if (!event.getPlayer().hasPermission("serverping.create")) {
			try {
				messages.insufficientPermission(event.getPlayer(), "serversign.create");
			} catch (TranslationNotFoundException e) {
				messages.missingTranslationFile(event.getPlayer(), ServerPing.getInstance().getConfig().getString("locale"));
				ServerPing sp = (ServerPing) Bukkit.getPluginManager().getPlugin("ServerPing");
				sp.disable(e, event.getPlayer());
			}
			return;
		}
				
		final ServerPingSign sps = new ServerPingSign(event.getLines(), event.getBlock().getLocation());
		
		try {
			SignManager.register(sps);
		} catch (SignAlreadyExistsException e) {
			e.printStackTrace();
		}
		
		// tell the player what happened -> move to ServerInitEvent
		try {
			messages.signPlaced(event.getPlayer(), event.getLine(1));
		} catch (TranslationNotFoundException e) {
			messages.missingTranslationFile(event.getPlayer(), ServerPing.getInstance().getConfig().getString("locale"));
			ServerPing sp = (ServerPing) Bukkit.getPluginManager().getPlugin("ServerPing");
			sp.disable(e, event.getPlayer());
		}
				
        new BukkitRunnable() {        	
            @Override
            public void run() {
        		// call sign/server init event
        		Bukkit.getPluginManager().callEvent(new ServerInitEvent(sps));
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("ServerPing"), 20L);
		
	}	
}
