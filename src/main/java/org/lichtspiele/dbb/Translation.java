package org.lichtspiele.dbb;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.lichtspiele.dbb.exception.CustomConfigurationFileNotFoundException;
import org.lichtspiele.dbb.exception.TranslationFileNotFoundException;
import org.lichtspiele.dbb.exception.TranslationNotFoundException;
import org.lichtspiele.dbb.registry.CustomConfigurationRegistry;

public class Translation {

	private String default_locale	= "en_US";
	
	private String locale_path		= "locale";
	
	private String locale			= null;
	
	private BukkitPlugin plugin 	= null;
		
	private YamlConfiguration data	= null;
	
	public Translation(JavaPlugin plugin) throws TranslationFileNotFoundException {
		this.plugin = (BukkitPlugin) plugin;
		this.loadTranslation();
	}
	
	private void loadTranslation() throws TranslationFileNotFoundException {
		String _locale = this.plugin.getConfig().getString("locale");

		YamlConfiguration yc = null;
		String[] language_files = { _locale, this.default_locale };
		
		for ( String file : language_files ) {
						
			String locale_file = this.locale_path + java.lang.System.getProperty("file.separator") + file + ".yml";
			try {
				yc = CustomConfigurationRegistry.get(locale_file);
			} catch (CustomConfigurationFileNotFoundException e) {
				continue;
			}			

			if (yc != null) {
				this.locale = file;
				break;
			}
		}

		if (yc == null)
			throw new TranslationFileNotFoundException("Could not find a suitable locale file");
		
		this.data = yc;
	}
	
	public String getLocale() {
		return this.locale;
	}
	
	public YamlConfiguration getTranslation() {
		return this.data;
	}
	
	public String getTranslation(String path) throws TranslationNotFoundException {
		String s;
		try {
			s = this.data.getString(path);
		} catch (NullPointerException e) {
			throw new TranslationNotFoundException(path);
		}
		if (s == null)
			throw new TranslationNotFoundException(path);
		 
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}
	
	public String getTranslation(String path, String[] args) throws TranslationNotFoundException {
		String s = this.getTranslation(path);
		
		 // check even		
    	if (args.length % 2 == 1)
            return s;
        
        for(int i = 0; i < args.length; i++){
            s = s.replaceAll("%" + args[i] + "%", args[i+1]);
            i++;
        }
		s = ChatColor.translateAlternateColorCodes('&', s);        
        return s;
	}	

}
