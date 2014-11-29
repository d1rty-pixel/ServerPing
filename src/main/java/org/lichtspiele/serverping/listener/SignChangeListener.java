package org.lichtspiele.serverping.listener;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.lichtspiele.dbb.exception.TranslationNotFoundException;
import org.lichtspiele.serverping.Messages;
import org.lichtspiele.serverping.ServerPing;
import org.lichtspiele.serverping.event.ServerInitEvent;
import org.lichtspiele.serverping.exception.SignAlreadyExistsException;
import org.lichtspiele.serverping.manager.SignManager;

public class SignChangeListener implements Listener {
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onSignChange(SignChangeEvent event) 
	{
		Player player = event.getPlayer();
		String Line1 = event.getLine(0);
		String Line2 = event.getLine(1);
		String Line3 = event.getLine(2);
		if(Line1.equals("[ServerPing]") || Line1.equals(ChatColor.AQUA + "[ServerPing]"))
		{
			Messages messages = (Messages) ServerPing.getInstance().getMessages();
			
			if(player.hasPermission("serverping.create")
			{
				try {
					messages.insufficientPermission(event.getPlayer(), "serversign.create");
				} catch (TranslationNotFoundException e) {
					messages.missingTranslationFile(event.getPlayer(), ServerPing.getInstance().getConfig().getString("locale"));
					ServerPing sp = (ServerPing) Bukkit.getPluginManager().getPlugin("ServerPing");
					sp.disable(e, event.getPlayer());
				}
			
				return;
			}
		
			{
				if(!Line2.equals(""))
				{
					if(!Line3.equals(""))
					{
					}
		
		                        System.out.println(sign.getLine(0));
		                        System.out.println(sign.getLine(1));
		                        System.out.println(sign.getLine(2));
		                        System.out.println(sign.getLine(3));
		
		                        String name 	  = sign.getLine(1);
		
		                        try {
		                        	
		       	                        SignManager.register(event.getPlayer().getWorld().getName(), name, (Sign) sign);
		                        } catch (SignAlreadyExistsException e) {
		                        	
		                                e.printStackTrace();
		                        }
		
		                        // call sign/server init event
		                       // ServerInitEvent requires a Sign object
		                       Bukkit.getPluginManager().callEvent(new ServerInitEvent(sign, sign.getLocation(), new ArrayList<Block>()));
		
		                      // tell the player what happened -> move to ServerInitEvent
		                      try {
			                messages.signPlaced(event.getPlayer(), sign.getLine(1));
		                      } catch (TranslationNotFoundException e) {
		                      	
			                messages.missingTranslationFile(event.getPlayer(), ServerPing.getInstance().getConfig().getString("locale"));
			                ServerPing sp = (ServerPing) Bukkit.getPluginManager().getPlugin("ServerPing");
			                sp.disable(e, event.getPlayer());
		                      }
				}
			}
