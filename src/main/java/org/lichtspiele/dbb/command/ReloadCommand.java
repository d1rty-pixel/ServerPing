package org.lichtspiele.dbb.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.lichtspiele.dbb.MessageBase;
import org.lichtspiele.dbb.exception.CommandSenderIsNotPlayerException;
import org.lichtspiele.dbb.exception.InsufficientPermissionException;
import org.lichtspiele.dbb.exception.TranslationFileNotFoundException;
import org.lichtspiele.dbb.exception.TranslationNotFoundException;

public class ReloadCommand extends PluginCommandBase {

	public ReloadCommand(JavaPlugin plugin, CommandSender sender)
			throws InsufficientPermissionException, CommandSenderIsNotPlayerException {
		
		super(plugin, sender, "yaspawn.admin.reload");
	}
	
	public boolean call(MessageBase messages, String[] args) throws TranslationNotFoundException, TranslationFileNotFoundException {		
		this.plugin.reloadConfig();
		
		this.reload();
		messages.reloadTranslation();
		messages.reload(this.sender);
		
		return true;
	}

}
