package org.lichtspiele.serverping.listener;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.material.Lever;
import org.bukkit.material.MaterialData;
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
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onSignInitEvent(ServerInitEvent event) throws TranslationNotFoundException {
		event.setLine(2, this.messages.serverIniitializing1());
		event.setLine(3, this.messages.serverIniitializing2());
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onServerOnlineEvent(ServerOnlineEvent event) throws IndexOutOfBoundsException, TranslationNotFoundException {
		
		for (Block l_block : event.getLevers()) {			
			BlockState blockState = l_block.getState();
			MaterialData mdata = blockState.getData();
			((Lever) mdata).setPowered(true);
			blockState.setData(mdata);
			blockState.update(true);
		}	
		event.setLine(2, "");
		event.setLine(3, this.messages.serverOnline());
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onServerOfflineEvent(ServerOfflineEvent event) throws IndexOutOfBoundsException, TranslationNotFoundException {
		for (Block l_block : event.getLevers()) {
			
			BlockState blockState = l_block.getState();
			MaterialData mdata = blockState.getData();
			((Lever) mdata).setPowered(false);
			blockState.setData(mdata);
			blockState.update(true);
		}
		event.setLine(2, "");
		event.setLine(3, this.messages.serverOffline());
	}

}
