package io.github.austinv11.PluginDocumentation.Listeners;

import io.github.austinv11.PluginDocumentation.Main.Resources;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class BookOpenListener implements Listener{
	
	public BookOpenListener(){
		Bukkit.getPluginManager().registerEvents(this, Resources.INSTANCE);
	}
}
