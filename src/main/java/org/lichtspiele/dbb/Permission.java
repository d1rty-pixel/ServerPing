package org.lichtspiele.dbb;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Permission {

	public static boolean hasPermission(CommandSender sender, String permission) {
		if (sender.isOp()) return true;
		if (sender instanceof Player && sender.hasPermission(permission)) return true;
		return false;
	}
	
}