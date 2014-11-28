package org.lichtspiele.dbb;

import java.io.IOException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.lichtspiele.dbb.registry.CustomConfigurationRegistry;
import org.mcstats.Metrics;

public class BukkitPlugin extends JavaPlugin {
	
	protected CustomConfigurationRegistry cc_registry;
	
	private boolean useMetrics									= true;
	
	private static BukkitPlugin instance;
	
	private MessageBase messages;
	
	public String version() {
		return "DBB 1.0";
	}
	
	public BukkitPlugin() {
		super();
		
		// create default directory and config file
		this.getDataFolder().mkdirs();
		this.saveDefaultConfig();
		
		this.useMetrics	= this.getConfig().getBoolean("use-metrics");
	}
	
	public FileConfiguration getConfig() {
		return super.getConfig();
	}
	
	public String getMessagePrefix() {
		return 	ChatColor.WHITE + "[" + 
				ChatColor.AQUA + this.getName().toString() + " v" + this.version() + 
				ChatColor.WHITE + "]" + 
				ChatColor.RESET + " ";
	}

	protected void enableMetrics() {
		if (!this.useMetrics) {
			this.getLogger().info(ChatColor.stripColor(this.getMessagePrefix()) + "Disabling metrics");
			return;
		}
		
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
			this.getLogger().info(ChatColor.stripColor(this.getMessagePrefix()) + "Failed to submit metrics");
		}		
	}
	
	public void disable(Exception e) {
		this.getLogger().severe(ChatColor.stripColor(this.getMessagePrefix()) + "Unrecoverable error: " + e.getMessage());
		this.getLogger().severe(ChatColor.stripColor(this.getMessagePrefix()) + "Disabling plugin");
        this.getPluginLoader().disablePlugin(this);	
	}

	public void disable(Exception e, CommandSender sender) {
		sender.sendMessage(this.getMessagePrefix() + "Unrecoverable error. Disabling plugin");
		this.disable(e);
	}

	public static BukkitPlugin getInstance() {
		return instance;
	}

	public static void setInstance(BukkitPlugin instance) {
		BukkitPlugin.instance = instance;
	}

	public MessageBase getMessages() {
		return messages;
	}

	public void setMessages(MessageBase messages) {
		this.messages = messages;
	}
	
}
