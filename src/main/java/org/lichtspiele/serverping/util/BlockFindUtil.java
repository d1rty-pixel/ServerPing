package org.lichtspiele.serverping.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

public class BlockFindUtil {
	
	@SuppressWarnings("deprecation")
	public static ArrayList<Block> getClosestBlocks(Location loc, List<Integer> blocks, int radius) {
		final World world	= loc.getWorld();
		Vector vec 			= new BlockVector(loc.getX(), loc.getY(), loc.getZ());
		int x_center		= loc.getBlockX();
		int y_center		= loc.getBlockY();
		int z_center		= loc.getBlockZ();
		
		ArrayList<Block> _blocks = new ArrayList<Block>();
		
		for (int y = -radius; y <= radius; y++) {
			// check min/max height
			if (y+y_center < 0 || y+y_center > world.getMaxHeight()) continue;
			
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
						
					Vector position = vec.clone().add(new Vector(x, y, z));
					
					if (vec.distance(position) <= radius + 0.5 && vec.distance(position) >= radius - 0.5) {
						Block b = world.getBlockAt(x + x_center, y + y_center, z + z_center);
						if (blocks.contains(b.getTypeId())) {
							_blocks.add(b);
						}
					}
				}
			}
		}
		 
		return _blocks;
	}
	
}
