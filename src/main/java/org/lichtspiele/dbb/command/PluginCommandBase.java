package org.lichtspiele.dbb.command;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.lichtspiele.dbb.Permission;
import org.lichtspiele.dbb.exception.CommandSenderIsNotPlayerException;
import org.lichtspiele.dbb.exception.InsufficientPermissionException;

public class PluginCommandBase {

	protected CommandSender sender		= null;

	protected Player player				= null;
	
	protected String permission			= null;
	
	protected Permission p				= new Permission();
	
	protected JavaPlugin plugin			= null;
	
	protected YamlConfiguration config	= null;
	
	protected boolean mustBePlayer		= false;
	
	public PluginCommandBase(JavaPlugin plugin, CommandSender sender, String permission) 
			throws InsufficientPermissionException, CommandSenderIsNotPlayerException {
		this(plugin, sender, permission, false);
	}
	
	public PluginCommandBase(JavaPlugin plugin, CommandSender sender, String permission, boolean mustBePlayer)
			throws InsufficientPermissionException, CommandSenderIsNotPlayerException {

		this.permission		= permission;
		this.mustBePlayer 	= mustBePlayer;
		
		if (this.permission != null && !this.hasPermission(sender, this.permission)) {
			throw new InsufficientPermissionException(this.permission);			
		}
		
		if (this.mustBePlayer && (!(sender instanceof Player))) {
			throw new CommandSenderIsNotPlayerException("You need to be in-game for this command");
		}
		
		this.plugin 	= plugin;
		this.sender 	= sender;		
		if (sender instanceof Player) {
			this.player = (Player) sender;
		}
		
		this.reload();
	}

	protected void mustBePlayer(boolean mbb) {
		this.mustBePlayer = mbb;
	}
	
	protected void reload() {
		this.config 	= (YamlConfiguration) this.plugin.getConfig();
	}
	
	protected boolean hasPermission(CommandSender sender, String permission) {
		return Permission.hasPermission(sender, permission);
	}
	
	protected boolean isPlayer() {
		return (this.sender instanceof Player);
	}
		
}
