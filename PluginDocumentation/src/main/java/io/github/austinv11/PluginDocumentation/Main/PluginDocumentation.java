package io.github.austinv11.PluginDocumentation.Main;

import java.util.ArrayList;
import java.util.List;

import io.github.austinv11.PluginDocumentation.API.BookData;
import io.github.austinv11.PluginDocumentation.API.BookDataFactory;
import io.github.austinv11.PluginDocumentation.API.BookFactory;
import io.github.austinv11.PluginDocumentation.Lib.BookMetaHelper;
import io.github.austinv11.PluginDocumentation.Lib.URLUtils;
import io.github.austinv11.PluginDocumentation.Listeners.BookOpenListener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginDocumentation extends JavaPlugin{
	FileConfiguration config = getConfig();
	List<String> contributors = null;
	List<String> plugins = null;
	
	@Override
	public void onEnable(){
		new Resources(config, this, getLogger());
		new BookOpenListener();
		configInit();
	}
	
	@Override
	public void onDisable(){}
	
	private void configInit(){
		config.addDefault("InternalCaching", true);
		//config.addDefault("ExternalCaching", true); TODO
		config.addDefault("ShowLinks", true);
		config.addDefault("Debug", false);
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	private void dump(){
		contributors = null;
		plugins = null;
		BookFactory.dump();
		BookData.dump();
		BookDataFactory.dump();
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
						list = temp.get(i).replaceFirst("-","").trim();
					}else{
						list = list+", "+temp.get(i).replaceFirst("-","").trim();
					}
				}
				sender.sendMessage(list);
			}catch (Exception e){
				sender.sendMessage(ChatColor.RED+"[ERROR] Unhandled exception: "+e.getMessage());
				sender.sendMessage("Please report this to the plugin author ASAP!");
				if (config.getBoolean("Debug")){
					e.printStackTrace();
				}
			}
			return true;
		}else if (cmd.getName().equalsIgnoreCase("dump")){
			sender.sendMessage("Dumping all cached data...");
			dump();
			return true;
		}else if (cmd.getName().equalsIgnoreCase("plugin-list")){
			try{
				if (config.getBoolean("InternalCaching")){
					if (plugins == null){
						plugins = URLUtils.readGithub("PLUGINS.md");
					}
				}
				List<String> temp;
				if (plugins == null){
					temp = URLUtils.readGithub("PLUGINS.md");
				}else{
					temp = plugins;
				}
				String list = null;
				for (int i = 0; i < temp.size(); i++){
					if (list == null){
						list = temp.get(i).replaceFirst("-","").trim();
					}else{
						list = list+", "+temp.get(i).replaceFirst("-","").trim();
					}
				}
				sender.sendMessage(list);
			}catch (Exception e){
				sender.sendMessage(ChatColor.RED+"[ERROR] Unhandled exception: "+e.getMessage());
				sender.sendMessage("Please report this to the plugin author ASAP!");
				if (config.getBoolean("Debug")){
					e.printStackTrace();
				}
			}
			return true;
		}else if (cmd.getName().equalsIgnoreCase("plugin-help")){
			if (args.length > 0){
				if (sender instanceof Player){
					try{
						sender.sendMessage("Attempting to fetch documentation, please wait...");
						Player player = (Player) sender;
						BookData data = BookDataFactory.newBookData(args[0]);
						if (data == null){
							List<BookData> dataList = new ArrayList<BookData>();
							dataList = BookDataFactory.newBookDataList(args[0]);
							for (int i = 0; i < dataList.size(); i++){
								data = dataList.get(i);
								ItemStack book = BookFactory.newBook(data);
								player.getInventory().addItem(book);
							}
							sender.sendMessage(ChatColor.GREEN+"Here you go!");
						}else{
							ItemStack book = BookFactory.newBook(data);
							player.getInventory().addItem(book);
							sender.sendMessage(ChatColor.GREEN+"Here you go!");
						}
					}catch (Exception e){
						//e.printStackTrace();
						sender.sendMessage(ChatColor.RED+"[ERROR] Unhandled exception: "+e.getMessage());
						sender.sendMessage("If you are certain that the documentation exists, report this to the plugin author ASAP!");
						if (config.getBoolean("Debug")){
							e.printStackTrace();
						}
					}
				}else{
					sender.sendMessage(ChatColor.RED+"Sorry, only a player can perform this command");
				}
				return true;
			}
		}else if (cmd.getName().equalsIgnoreCase("book-converter")){
			if (sender instanceof Player){
				Player player = (Player) sender;
				if (player.getInventory().getItemInHand().getType() == Material.WRITTEN_BOOK){
					try{
						sender.sendMessage("Attempting to post onto pastebin...");
						BookMeta bm = (BookMeta) player.getInventory().getItemInHand().getItemMeta();
						sender.sendMessage("The contents of this book has been posted! Visit it here: "+ChatColor.GOLD+BookMetaHelper.sendToPastebin(bm));
					}catch (Exception e){
						sender.sendMessage(ChatColor.RED+"[ERROR] Unhandled exception: "+e.getMessage());
						sender.sendMessage("Report this to the plugin author ASAP!");
						if (config.getBoolean("Debug")){
							e.printStackTrace();
						}
					}
				}else{
					sender.sendMessage(ChatColor.RED+"Sorry, you must be holding a written book in your hand");
				}
			}else{
				sender.sendMessage(ChatColor.RED+"Sorry console, this is not for you");
			}
			return true;
		}
		return false;
	}
}
