package org.lichtspiele.serverping.event;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class ServerOnlineEvent extends ServerEvent {

	public ServerOnlineEvent(Sign sign, Location location, ArrayList<Block> redstone_wires) {
		super(sign, location, redstone_wires);
	}

 
}
