package org.lichtspiele.dbb.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.lichtspiele.dbb.exception.CommandSenderIsNotPlayerException;
import org.lichtspiele.dbb.exception.InsufficientPermissionException;
import org.lichtspiele.dbb.exception.TranslationFileNotFoundException;
import org.lichtspiele.dbb.exception.TranslationNotFoundException;
import org.lichtspiele.serverping.Messages;

public class VersionCommand extends PluginCommandBase {

	public VersionCommand(JavaPlugin plugin, CommandSender sender)
			throws InsufficientPermissionException, CommandSenderIsNotPlayerException {
		
		super(plugin, sender, "yaspawn.yaspawn");
	}
	
	public boolean call(Messages messages, String[] args) throws TranslationNotFoundException, TranslationFileNotFoundException {
		messages.version(this.sender, args[0]);
		return true;
	}

}
