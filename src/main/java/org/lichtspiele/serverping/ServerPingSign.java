package org.lichtspiele.serverping;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.lichtspiele.serverping.util.BlockFindUtil;

public class ServerPingSign {

	private String[] lines;
	
	private Location location;
	
	private ArrayList<Block> levers	= new ArrayList<Block>();
	
	private Block target;
	
	public ServerPingSign(String[] lines, Location location) {
		this.lines = lines;
		this.location = location;
	}
	
	public ServerPingSign(String[] lines, Location location, Block target) {
		this(lines, location);
		this.setTarget(target);
		this.lookupLevers();
	}

	@SuppressWarnings({ "deprecation" })
	private void lookupLevers() {
		ArrayList<Integer> search_blocks	= new ArrayList<Integer>() {
			private static final long serialVersionUID = -3956279865704489578L;
		{ add(Material.LEVER.getId() ); }};
		this.levers							= BlockFindUtil.getClosestBlocks(this.target.getLocation(), search_blocks, 1);
	}
	
	public ArrayList<Block> getLevers() {
		return this.levers;
	}
	
	public String getName() {
		return this.lines[1];
	}
	
	public String getAddress() {
		return this.lines[2];
	}

	public Integer getPort() {
		return Integer.parseInt(this.lines[3]);
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public String getWorld() {
		return this.location.getWorld().getName();
	}
	
	public void setLine(Integer pos, String content) {
		String w = this.getWorld();
		
		Block b = Bukkit.getServer().getWorld(w).getBlockAt(this.getLocation());
		Sign s = null;
		
		try {
			s = (Sign) b.getState();
		} catch (ClassCastException e) {
			System.out.println("ja bloed");
		}
		
		s.setLine(pos, content);
		s.update(true);
	}

	public Block getTarget() {
		return target;
	}

	public void setTarget(Block target) {
		this.target = target;
	}
	
}
