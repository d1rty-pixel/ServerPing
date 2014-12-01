package org.lichtspiele.serverping.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;
import org.lichtspiele.dbb.exception.CustomConfigurationFileNotFoundException;
import org.lichtspiele.dbb.registry.CustomConfigurationRegistry;
import org.lichtspiele.serverping.ServerPingSign;
import org.lichtspiele.serverping.exception.SignAlreadyExistsException;

public class SignManager {
	
	private static HashMap<String,HashMap<String,HashMap<String,Object>>> data = new HashMap<>();
	
	public static HashMap<String, HashMap<String, HashMap<String, Object>>> getData() {
		return SignManager.data;
	}
	
	private static void createWorld(String world) {
		if (!SignManager.data.containsKey(world)) {
			SignManager.data.put(world, new HashMap<String,HashMap<String,Object>>());
		}
	}
	
	public static Set<String> getWorlds() {
		return SignManager.data.keySet();
	}
	
	public static Set<String> getSigns(String world) {
		return SignManager.data.get(world).keySet();
	}
	
	public static HashMap<String, HashMap<String, Object>> getWorld(String world) {
		return SignManager.data.get(world);
	}
	
	private static void createSign(String world, String sign) {
		if (!SignManager.getWorld(world).containsKey(sign)) {
			SignManager.data.get(world).put(sign, new HashMap<String,Object>());
		}
	}
	
	public static HashMap<String, Object> getSignConfiguration(String world, String sign) {
		return SignManager.data.get(world).get(sign);
	}
	
	public static void load() throws CustomConfigurationFileNotFoundException {
		SignManager.data = new HashMap<>();
		YamlConfiguration dtl = CustomConfigurationRegistry.get("signs.yml");
	
		if (dtl.getKeys(false).isEmpty()) return;
		
		for (Object w : dtl.getKeys(false).toArray()) {
			String world = w.toString();
			SignManager.createWorld(world);
			
			for (Object s : dtl.getConfigurationSection(world).getKeys(false).toArray()) {
				String sign = s.toString();
				SignManager.createSign(world, sign);
				
				HashMap<String,Object> settings = new HashMap<String,Object>();

				for (Object key : dtl.getConfigurationSection(world + "." + sign).getKeys(false).toArray()) {					
					String section = world + "." + sign + "." + key.toString();
					settings.put(key.toString(), dtl.getString(section));
				}
				SignManager.data.get(world).get(sign).putAll(settings);
			}
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public static void save() throws IOException, CustomConfigurationFileNotFoundException {
		
		HashMap<String,Object> dts = new HashMap<String,Object>(); 
		 
		for (String world : SignManager.getWorlds()) {
			for (String sign : SignManager.getSigns(world)) {
				
				try {
					HashMap<String, Object> sign_config = SignManager.getSignConfiguration(world, sign);
					Iterator i = sign_config.entrySet().iterator();
					
					while (i.hasNext()) {
						Map.Entry pair = (Map.Entry) i.next();
						dts.put(world + "." + sign + "." + pair.getKey(), pair.getValue());
					}					
				} catch (NullPointerException e) {
					continue;
				}
			}
		}
		
		CustomConfigurationRegistry.getFileObject("signs.yml").save(dts);
		SignManager.load();
	}
	
	public static void register(ServerPingSign sps)
			throws SignAlreadyExistsException {
		
		String name		= sps.getName();	
		String world 	= sps.getLocation().getWorld().getName();
		
		if (SignManager.signExists(name, world)) throw new SignAlreadyExistsException();
	
		HashMap<String,Object> settings = new HashMap<String,Object>();
		settings.put("address",		sps.getAddress());
		settings.put("port",		sps.getPort());
		settings.put("X",			sps.getLocation().getX());
		settings.put("Y",			sps.getLocation().getY());
		settings.put("Z",			sps.getLocation().getZ());

		// just to be safe
		SignManager.createWorld(world);
		SignManager.createSign(world, name);
		
		SignManager.data.get(world).get(name).putAll(settings);
		
		try {
			SignManager.save();
		} catch (IOException | CustomConfigurationFileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void unregister(String world, String name) 
			throws CustomConfigurationFileNotFoundException {

		if (!SignManager.signExists(world, name)) return;
		
		SignManager.data.get(world).remove(name);				
		try {
			SignManager.save();
		} catch (IOException | CustomConfigurationFileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	
	public static boolean signExists(String world, String name) {
		try {
			SignManager.getWorld(world);
			SignManager.getSignConfiguration(world, name);
			return true;
		} catch (NullPointerException e) {
			return false;
		} 
	}

}
