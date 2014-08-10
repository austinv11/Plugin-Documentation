package io.github.austinv11.PluginDocumentation.Main;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;

public class Resources {
	public static FileConfiguration CONFG;
	public static PluginDocumentation INSTANCE;
	public static Logger LOGGER;
	
	public Resources(FileConfiguration config, PluginDocumentation instance, Logger logger){
		CONFG = config;
		INSTANCE = instance;
		LOGGER = logger;
	}
}
