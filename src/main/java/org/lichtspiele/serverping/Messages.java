package org.lichtspiele.serverping;

import org.bukkit.command.CommandSender;
import org.lichtspiele.dbb.BukkitPlugin;
import org.lichtspiele.dbb.MessageBase;
import org.lichtspiele.dbb.exception.TranslationFileNotFoundException;
import org.lichtspiele.dbb.exception.TranslationNotFoundException;

public class Messages extends MessageBase {

	public Messages(BukkitPlugin plugin) throws TranslationFileNotFoundException {
		super(plugin);
	}
	
	public void signPlaced(CommandSender sender, String sign) throws TranslationNotFoundException {
		String message = this.translation.getTranslation("sign_created", new String[] { "sign", sign });
		this.send(sender, message);
	}
	
	public void signDestroyed(CommandSender sender, String sign) throws TranslationNotFoundException {
		String message = this.translation.getTranslation("sign_destroyed", new String[] { "sign", sign });
		this.send(sender, message);
	}
	
	public String serverIniitializing() throws TranslationNotFoundException {
		return this.translation.getTranslation("init");
	}

	public String serverOnline() throws TranslationNotFoundException {
		return this.translation.getTranslation("server_online");
	}
	
	public String serverOffline() throws TranslationNotFoundException {
		return this.translation.getTranslation("server_offline");
	}
	
	/*
	 * locale
	 */
	public void locale(CommandSender sender) throws TranslationNotFoundException {
		this.send(sender, this.translation.getTranslation("set_locale",
			new String[] { "locale", this.translation.getLocale() }
		));	
	}

}


