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
	List<String> contributors = null;
	
	@Override
	public void onEnable(){
		new Resources(config, this, getLogger());
		configInit();
	}
	
	@Override
	public void onDisable(){}
	
	private void configInit(){
		config.addDefault("InternalCaching", true);
		//config.addDefault("ExternalCaching", true); TODO
		config.addDefault("ShowLinks", true);
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("contributors")){
			try{
				if (config.getBoolean("InternalCaching")){
					if (contributors == null){
						contributors = URLUtils.readGithub("CONTRIBUTORS.md");
					}
				}
				List<String> temp;
				if (contributors == null){
					temp = URLUtils.readGithub("CONTRIBUTORS.md");
				}else{
					temp = contributors;
				}
				String list = null;
				for (int i = 0; i < temp.size(); i++){
					if (list == null){
						list = temp.get(i);
					}else{
						list = list+", "+temp.get(i);
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
