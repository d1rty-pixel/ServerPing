package org.lichtspiele.serverping.config.locale;

import java.io.IOException;
import java.util.HashMap;

import org.lichtspiele.dbb.BukkitPlugin;
import org.lichtspiele.dbb.config.CustomConfigurationFile;

public class en_US extends CustomConfigurationFile {

	public en_US(BukkitPlugin plugin) throws IOException {
		super(plugin, "locale" + java.lang.System.getProperty("file.separator") + "en_US.yml", en_US.data());
	}
	
	private static HashMap<String,Object> data() {
		HashMap<String,Object> data		= new HashMap<String,Object>();
		
		data.put("error", 								"&cERROR");
		data.put("missing_translation",					"&4Translation for local &b%locale%&4 not found");		
		data.put("missing_permission", 					"&4Not enough permissions (%spermission%)");
		
		data.put("sign_created",						"&fSign for server &b%sign% &f created");
		data.put("sign_destroyed",						"&fSign for server &b%sign% &f destroyed");
		
		data.put("init_1",								"&kl&kl&kl&kl&kl");
		data.put("init_2",								"Initializing");
		data.put("server_online",						"&aonline");
		data.put("server_offline",						"&coffline");

		return data;
	}
	
}
