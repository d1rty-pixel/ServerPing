package org.lichtspiele.serverping.config;

import java.io.IOException;
import java.util.HashMap;

import org.lichtspiele.dbb.BukkitPlugin;
import org.lichtspiele.dbb.config.CustomConfigurationFile;

public class Signs extends CustomConfigurationFile {

	public Signs(BukkitPlugin plugin) throws IOException {
		super(plugin, "signs.yml", Signs.data());
	}
	
	private static HashMap<String,Object> data() {
		HashMap<String,Object> data		= new HashMap<String,Object>();		
		return data;
	}
	
}
