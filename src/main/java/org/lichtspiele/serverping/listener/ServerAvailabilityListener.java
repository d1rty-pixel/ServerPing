package org.lichtspiele.serverping.listener;

import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.lichtspiele.dbb.exception.TranslationNotFoundException;
import org.lichtspiele.serverping.Messages;
import org.lichtspiele.serverping.ServerPing;
import org.lichtspiele.serverping.event.ServerInitEvent;
import org.lichtspiele.serverping.event.ServerOfflineEvent;
import org.lichtspiele.serverping.event.ServerOnlineEvent;

public class ServerAvailabilityListener implements Listener {

	private Messages messages;
	
	public ServerAvailabilityListener() {
		this.messages = (Messages) ServerPing.getInstance().getMessages();
	}
	
	public void onSignInitEvent(ServerInitEvent event) throws TranslationNotFoundException {
		System.out.println("init event listener");
		event.getSign().setLine(2, "");
		event.getSign().setLine(3, this.messages.serverIniitializing());
		event.getSign().update(true);
	}
	
	@SuppressWarnings("deprecation")
	public void onServerOnlineEvent(ServerOnlineEvent event) throws IndexOutOfBoundsException, TranslationNotFoundException {
		for (Block redstone_wire_block : event.getWires()) {
			redstone_wire_block.setData((byte) 0, true);
		}
		
		System.out.println("online event listener");
		
		event.getSign().setLine(2, "");
		event.getSign().setLine(3, this.messages.serverOnline());
		event.getSign().update(true);
	}
	
	@SuppressWarnings("deprecation")
	public void onServerOfflineEvent(ServerOfflineEvent event) throws IndexOutOfBoundsException, TranslationNotFoundException {
		for (Block redstone_wire_block : event.getWires()) {
			redstone_wire_block.setData((byte) 15, true);
		}
		
		System.out.println("offline event listener");
		event.getSign().setLine(2, "");
		event.getSign().setLine(3, this.messages.serverOffline());
		event.getSign().update(true);
	}
	
}
