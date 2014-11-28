package org.lichtspiele.serverping;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.lichtspiele.dbb.BukkitPlugin;
import org.lichtspiele.dbb.exception.CustomConfigurationFileNotFoundException;
import org.lichtspiele.dbb.registry.CustomConfigurationRegistry;
import org.lichtspiele.serverping.config.Signs;
import org.lichtspiele.serverping.config.locale.en_US;
import org.lichtspiele.serverping.listener.BlockBreakListener;
import org.lichtspiele.serverping.listener.ServerAvailabilityListener;
import org.lichtspiele.serverping.listener.SignChangeListener;
import org.lichtspiele.serverping.task.PingTask;

public class ServerPing extends BukkitPlugin {
	
	public Messages messages 			= null;
		
	public void onEnable() {
		ServerPing.setInstance(this);
			
		this.enableMetrics();		
		this.enable();
	}
	
	public void enable() {
		try {
			this.loadCustomConfiguration();
			this.setMessages(new Messages(this));
			this.registerEvents();
		} catch (IOException e) {
			System.out.println(e);
			this.disable(e);
		}
		
		try {
			this.loadWorlds();
		} catch (CustomConfigurationFileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("signs.yml not found");
			//this.disable(e);
		}
				
		this.registerTask();		
	}

	private void loadCustomConfiguration() throws IOException {
		CustomConfigurationRegistry.register(new en_US(this));
		CustomConfigurationRegistry.register(new Signs(this));
	}
	
	private void loadWorlds() throws CustomConfigurationFileNotFoundException {
		YamlConfiguration config = CustomConfigurationRegistry.get("signs.yml");
		
		List<World> worlds = Bukkit.getServer().getWorlds();
		for (World world : worlds) {
			ConfigurationSection config_world = config.getConfigurationSection(world.getName());
			if (config_world == null) {
				config.set(world.getName(), new HashMap<String,String>());
			}
		}
	}

	private void registerEvents() {
		this.getServer().getPluginManager().registerEvents(new SignChangeListener(), this);
		this.getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
		this.getServer().getPluginManager().registerEvents(new ServerAvailabilityListener(), this);
	}
	
	private void registerTask() {	
		// interval in seconds * 20 ticks
		// a value of 10 results in 200 ticks, 1 second is 20 ticks => 10 seconds  
		long interval = (long) this.getConfig().getInt("interval") * 20;

		// start scheduled timed task
		this.getServer().getScheduler().runTaskTimer(this, new Runnable() {
			public void run() {		
				try {
					new PingTask();
				} catch (CustomConfigurationFileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}, 60L, interval);
	}		

	

}
