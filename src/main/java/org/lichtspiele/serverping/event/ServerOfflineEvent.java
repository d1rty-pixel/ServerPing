package org.lichtspiele.serverping.event;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class ServerOfflineEvent extends ServerEvent {

	public ServerOfflineEvent(Sign sign, Location location, ArrayList<Block> redstone_wires) {
		super(sign, location, redstone_wires);
	}
	
}
