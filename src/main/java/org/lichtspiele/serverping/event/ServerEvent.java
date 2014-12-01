package org.lichtspiele.serverping.event;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.lichtspiele.serverping.ServerPingSign;

public class ServerEvent extends Event implements Cancellable {
    
	protected static final HandlerList handlers = new HandlerList();

	private ServerPingSign sign;

	private boolean cancel;
	
	ArrayList<Block> redstone_wires	= new ArrayList<Block>();	
	
	public ServerEvent(ServerPingSign sps) {
		super();
		this.setSign(sps);
	}

	public HandlerList getHandlers() {
		return ServerEvent.handlers;
	}
	
	public static HandlerList getHandlerList() {
		return ServerEvent.handlers;
	}
	
	public ServerPingSign getSign() {
		return this.sign;
	}

	public void setSign(ServerPingSign sign) {
		this.sign = sign;
	}

	public void setLine(Integer index, String content) {
		this.sign.setLine(index, content);
	}
		
	public ArrayList<Block> getLevers() {
		return this.sign.getLevers();
	}

	@Override
	public boolean isCancelled() {
		return this.cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;		
	}

}
