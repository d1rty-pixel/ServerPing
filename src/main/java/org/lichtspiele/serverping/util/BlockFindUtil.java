package org.lichtspiele.serverping.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockFindUtil {
	
	@SuppressWarnings("deprecation")
	public static ArrayList<Block> getClosestBlocks(Location loc, List<Integer> blocks, int radius) {
		
		ArrayList<Block> _blocks = new ArrayList<Block>();
			
		final World world = loc.getWorld();
		for (int y = 1; y > -radius; y--) {
			for (int x = 1; x > -radius; x--) {
				for (int z = 1; z > -radius; z--) {
		
					int _x = (int) (loc.getX() + x);
					int _y = (int) (loc.getY() + y);
					int _z = (int) (loc.getZ() + z);
					
					int foo = world.getBlockTypeIdAt(_x, _y, _z);
					if (blocks.contains(foo)) {
						_blocks.add(world.getBlockAt(_x, _y, _z));
					}
				}
			}
		}
		 
		return _blocks;
	}
	
}
