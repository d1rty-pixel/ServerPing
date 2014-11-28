package org.lichtspiele.dbb.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.lichtspiele.dbb.BukkitPlugin;

public class CustomConfigurationFile  {

	private YamlConfiguration yaml				= null;
	
	private File file							= null;
	
	private String filename						= null;
	
	public static CustomConfigurationFile instance	= null;
	
	public CustomConfigurationFile(BukkitPlugin plugin, String filename, HashMap<String,Object> data) throws IOException {
		
		this.setFilename(filename);
		this.file = new File(
			plugin.getDataFolder().getAbsolutePath() +
			java.lang.System.getProperty("file.separator") + 
			filename
		);
		
		if (!this.file.exists()) {
			plugin.getLogger().info("Creating file " + this.file.getAbsolutePath());
			this.save(data);
		}
		
		instance 	= this;
	}
		
	@SuppressWarnings("rawtypes")
	public void save(HashMap<String,Object> data) throws IOException {
		this.yaml = new YamlConfiguration();
		
		Iterator it = data.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			this.yaml.set((String) pair.getKey(), pair.getValue());
			it.remove();
		}

		this.yaml.save(this.file.getAbsolutePath());		
	}
	
	public YamlConfiguration load() {
		return YamlConfiguration.loadConfiguration(this.file);
	}
	
	public YamlConfiguration reload() {
		return this.load();
	}

	public static CustomConfigurationFile getInstance() {
		return instance;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}
