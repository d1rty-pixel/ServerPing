package org.lichtspiele.dbb;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ResoucreLoader {

	JavaPlugin plugin							= null;
	
	Logger logger 								= Logger.getLogger("Minecraft");
	
	HashMap<String, FileConfiguration> resources	= new HashMap<String, FileConfiguration>();
	
	public void ResourceLoader(JavaPlugin plugin, String[] resources) {
		this.plugin = plugin;
		
		for (String r : resources) {
			this.loadResourceFile(r);
		}
	}
	
	private void loadResourceFile(String filename) {
		String res_filename = "resource/" + filename + ".yml";
		File file = new File(this.plugin.getDataFolder().getAbsolutePath(), res_filename);
		
		if (!file.exists()) { 
			this.logger.warning("Resource " + res_filename + " not found. Creating from scratch");
			this.plugin.saveResource(res_filename, false);			
		}

		this.resources.put(filename, YamlConfiguration.loadConfiguration(file));		
	}
	
	public FileConfiguration getResource(String filename) {
		return this.resources.get(filename);
	}
	
}
