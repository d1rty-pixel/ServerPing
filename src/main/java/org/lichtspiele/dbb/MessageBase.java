package org.lichtspiele.dbb;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.lichtspiele.dbb.exception.TranslationFileNotFoundException;
import org.lichtspiele.dbb.exception.TranslationNotFoundException;

public class MessageBase {
	
	protected Translation translation	 	= null;
	
	String message_prefix					= null;

	private JavaPlugin plugin				= null;
	
	public MessageBase(JavaPlugin plugin) throws TranslationFileNotFoundException {
		this.plugin 		= plugin;
		this.message_prefix	= plugin.getName();
		this.loadTranslation();
	}

	private void loadTranslation() throws TranslationFileNotFoundException {
		this.translation = new Translation(this.plugin);
	}
	
	public void reloadTranslation() throws TranslationFileNotFoundException {
		this.loadTranslation();
	}
	
	
	public String getMessagePrefix() {
		return ChatColor.WHITE + "[" + ChatColor.AQUA + this.message_prefix.toString() + ChatColor.WHITE + "] " + ChatColor.RESET;
	}
	
	
	public void send(CommandSender sender, String message, boolean prefix) {
		if (prefix) message = this.getMessagePrefix() + message;
		if (!(sender instanceof Player)) message = ChatColor.stripColor(message);
		sender.sendMessage(message);
	}
	
	public void send(CommandSender sender, String message) {
		this.send(sender, message, true);
	}
	
	/*
	 * predefined messages
	 */
	public void reload(CommandSender sender) throws TranslationNotFoundException {
		this.send(sender, this.translation.getTranslation("reload"));
	}	
	
	public void version(CommandSender sender, String version) {
		this.send(sender, version);
	}
	
	public void insufficientPermission(CommandSender sender, String permission) throws TranslationNotFoundException {
		String message =				
			this.translation.getTranslation("error") + " " + 
			this.translation.getTranslation("missing_permission", new String[] {"permission", permission} );
		this.send(sender, message);		
	}
	
	public void missingTranslation(CommandSender sender, String path) {
		String message = ChatColor.RED + "ERROR: Translation not found for path " + ChatColor.AQUA + path;
		this.send(sender, message);
	}
	
	public void missingTranslationFile(CommandSender sender, String locale) {
		String message = ChatColor.RED + "ERROR: Translation files not found for locale " + ChatColor.AQUA + locale;
		this.send(sender, message);						
	}
	
	public void mustBePlayer(CommandSender sender) throws TranslationNotFoundException {
		String message = 
			this.translation.getTranslation("error") + "This command requires you to be ingame";
		this.send(sender, message);		
	}
	
	public void unknownWorld(CommandSender sender, String world) throws TranslationNotFoundException {
		String message = 
			this.translation.getTranslation("error") + " " +
			this.translation.getTranslation("unknown_world", new String[] {"world", world} );
		this.send(sender, message);
	}	
	
	public void invalidCommand(CommandSender sender, String command) throws TranslationNotFoundException {
		String message = 
			this.translation.getTranslation("error") + " " +
			this.translation.getTranslation("invalid_command", new String[] { "command", command } );
		this.send(sender, message);
	}
	
	/*
	 * help
	 */
	public void helpTitle(CommandSender sender) throws TranslationNotFoundException {
		this.send(sender, this.translation.getTranslation("help.header"));
	}
	
	public void helpEntry(CommandSender sender, String command, String[] args, String desc_lang_key) throws TranslationNotFoundException {
		String args_txt = StringUtils.join(args, " ");
		if (args_txt.length() > 0) args_txt = " " + args_txt;
		
		this.send(sender, 
			ChatColor.AQUA + " /" + command  + ChatColor.GREEN + args_txt.toString() +
		    ChatColor.WHITE + " " + this.translation.getTranslation(desc_lang_key),
		false);	
	}
	
	/*
	 * config 
	 */
	public void configTitle(CommandSender sender) throws TranslationNotFoundException {
		this.send(sender, this.translation.getTranslation("help.config_header"));		
	}
	
}
