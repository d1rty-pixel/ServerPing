package org.lichtspiele.serverping.event;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ServerEvent extends Event implements Cancellable {
    
	protected static final HandlerList handlers = new HandlerList();

	private Sign sign;
	
	private Location location;
	
	private ArrayList<Block> wires;
	
	private boolean cancel;
	
	ArrayList<Block> redstone_wires	= new ArrayList<Block>();	
	
	public ServerEvent(Sign sign, Location location, ArrayList<Block> redstone_wires) {
		super();
    	this.setSign(sign);
    	this.setLocation(location);
    	this.redstone_wires	= redstone_wires;
	}

	public HandlerList getHandlers() {
		return ServerEvent.handlers;
	}
	
	public static HandlerList getHandlerList() {
		return ServerEvent.handlers;
	}
	
	public Sign getSign() {
		return sign;
	}


	public void setSign(Sign sign) {
		this.sign = sign;
	}


	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	public ArrayList<Block> getWires() {
		return wires;
	}


	public void setWires(ArrayList<Block> wires) {
		this.wires = wires;
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
