package io.github.austinv11.PluginDocumentation.Main;

import java.util.List;

import io.github.austinv11.PluginDocumentation.Lib.URLUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginDocumentation extends JavaPlugin{
	FileConfiguration config = getConfig();
	
	@Override
	public void onEnable(){
		new Resources(config, this, getLogger());
		configInit();
	}
	
	@Override
	public void onDisable(){}
	
	private void configInit(){
		config.addDefault("InternalCaching", true);
		config.addDefault("ExternalCaching", true);
		config.addDefault("ShowLinks", true);
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("contributors")){
			try{
				List<String> contributors = URLUtils.readGithub("CONTRIBUTORS.md/");
				String list = null;
				for (int i = 0; i < contributors.size(); i++){
					if (list == null){
						list = contributors.get(i);
					}else{
						list = list+", "+contributors.get(i);
					}
				}
				sender.sendMessage(list);
			}catch (Exception e){
				sender.sendMessage(ChatColor.RED+"[ERROR] Unhandled exception: "+e.getMessage());
				sender.sendMessage("Please report this to the plugin author ASAP!");
			}
			return true;
		}
		return false;
	}
}
