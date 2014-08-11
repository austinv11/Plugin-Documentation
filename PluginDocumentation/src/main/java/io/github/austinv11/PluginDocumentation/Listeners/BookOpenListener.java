package io.github.austinv11.PluginDocumentation.Listeners;

import java.util.List;
import java.util.Map;

import io.github.austinv11.PluginDocumentation.API.BookDataFactory;
import io.github.austinv11.PluginDocumentation.Lib.Index;
import io.github.austinv11.PluginDocumentation.Lib.JSONUtils;
import io.github.austinv11.PluginDocumentation.Lib.Links;
import io.github.austinv11.PluginDocumentation.Lib.URLUtils;
import io.github.austinv11.PluginDocumentation.Main.Resources;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class BookOpenListener implements Listener{
	
	public BookOpenListener(){
		Bukkit.getPluginManager().registerEvents(this, Resources.INSTANCE);
	}
	
	//TODO Add event for fetching links
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		if (!event.isCancelled()){
			if (Resources.CONFG.getBoolean("ShowLinks")){
				if (event.getItem().getType() == Material.WRITTEN_BOOK){
					if (event.getItem().hasItemMeta()){
						if (event.getItem().getItemMeta().hasLore()){
							BookMeta meta = (BookMeta) event.getItem().getItemMeta();
							List<String> lore = meta.getLore();
							for (int i = 0; i <lore.size(); i++){
								if (lore.get(i).contains("Has Links")){
									event.getPlayer().sendMessage(ChatColor.GOLD+"Here are some helpful links:");
									if (BookDataFactory.linkCache.containsKey(meta.getTitle())){
										for (Map.Entry<String, String> entry : BookDataFactory.linkCache.get(meta.getTitle()).entrySet()){
											String key = entry.getKey();
											String value = entry.getValue();
											event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', key)+": "+ChatColor.translateAlternateColorCodes('&', value));
										}
									}else{
										try{
											String title = meta.getTitle();
											if (title.contains(":")){
												String[] split = title.split(":");
												title = split[1];
												String plugin = split[0];
												//JSONObject json = JSONUtils.listToJSON(URLUtils.readGithub(plugin+"/"+title+"/index.json"));
												Index json = JSONUtils.listToJSON(URLUtils.readGithub(plugin+"/"+title+"/index.json"));
												//JSONArray array = (JSONArray) json.get("Links");
												Links[] array = json.Links;
												for (int j = 0; j < /*array.size()*/array.length; j++){
													/*JSONObject object = (JSONObject) array.get(j);
													event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', (String) object.get("Title"))+": "+ChatColor.translateAlternateColorCodes('&', (String) object.get("URL")));*/
													event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', array[j].Title)+": "+ChatColor.translateAlternateColorCodes('&', array[j].URL));
												}
											}else{
												//JSONObject json = JSONUtils.listToJSON(URLUtils.readGithub(title+"/index.json"));
												Index json = JSONUtils.listToJSON(URLUtils.readGithub(title+"/index.json"));
												//JSONArray array = (JSONArray) json.get("Links");
												Links[] array = json.Links;
												for (int j = 0; j < /*array.size()*/array.length; j++){
													/*JSONObject object = (JSONObject) array.get(j);
													event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', (String) object.get("Title"))+": "+ChatColor.translateAlternateColorCodes('&', (String) object.get("URL")));*/
													event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', array[j].Title)+": "+ChatColor.translateAlternateColorCodes('&', array[j].URL));
												}
											}
										}catch (Exception e){
											event.getPlayer().sendMessage(ChatColor.RED+"[ERROR] Unhandled exception: "+e.getMessage());
											event.getPlayer().sendMessage("Please contact the plugin author ASAP!");
											if (Resources.CONFG.getBoolean("Debug")){
												e.printStackTrace();
											}
										}
									}
									break;
								}
							}
						}
					}
				}
			}
		}
	}
}
