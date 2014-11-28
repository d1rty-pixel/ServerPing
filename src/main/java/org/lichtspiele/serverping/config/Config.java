package org.lichtspiele.serverping.config;

import java.io.IOException;
import java.util.HashMap;

import org.lichtspiele.dbb.BukkitPlugin;
import org.lichtspiele.dbb.config.CustomConfigurationFile;

public class Config extends CustomConfigurationFile {

	public Config(BukkitPlugin plugin) throws IOException {
		super(plugin, "signs.yml", Config.data());
	}
	
	private static HashMap<String,Object> data() {
		HashMap<String,Object> data		= new HashMap<String,Object>();
		
		data.put("interval", 		10);
		data.put("locale",			"en_US");	
		
		return data;
	}
	
}
