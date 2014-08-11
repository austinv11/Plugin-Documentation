package io.github.austinv11.PluginDocumentation.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import io.github.austinv11.PluginDocumentation.Lib.JSONUtils;
import io.github.austinv11.PluginDocumentation.Lib.URLUtils;
import io.github.austinv11.PluginDocumentation.Main.Resources;

public class BookDataFactory {
	public static HashMap<String, HashMap<String,String>> linkCache;
	private static HashMap<String, BookData> dataCache;
	
	/**
	 * Dumps the cached data.
	 */
	public static void dump(){
		linkCache = null;
		dataCache = null;
	}
	
	/**
	 * Attempts to fetch BookData.
	 * @param plugin Name of the plugin to get data for.
	 * @return The BookData, null if the documentation is section-use newBookDataList().
	 * @throws Exception If any error is encountered.
	 */
	//TODO Add event to allow plugins to hook into this.
	public static BookData newBookData(String plugin) throws Exception{
		if (dataCache != null){
			if (dataCache.containsKey(plugin.toUpperCase())){
				return dataCache.get(plugin.toUpperCase());
			}
		}
		String version;
		JSONObject json = JSONUtils.listToJSON(URLUtils.readGithub(plugin.toUpperCase()+"/index.json"));
		version = (String) json.get("Version");
		if ((boolean) json.get("Sectioned")){
			return null;
		}else{
			int chapters = (int) json.get("Chapters");
			HashMap<Integer, List<String>> contents = null;
			boolean startAtOne = false;
			if (!URLUtils.URLReader("https://raw.github.com/austinv11/Plugin-Documentation/master/"+plugin.toUpperCase()+"/Chatpter0.txt").contains("Not Found")){
				startAtOne = true;
			}
			int key = 0;
			for (int i = 0; i < chapters; i++){
				key = i;
				if (startAtOne){
					key++;
				}
				contents.put(key, URLUtils.readGithub(plugin.toUpperCase()+"/Chapter"+key+".txt"));
			}
			if ((boolean) json.get("HasLinks")){
				HashMap<String,String> links = null;
				JSONArray array = (JSONArray) json.get("Links");
				array.iterator();
				for (int j = 0; j < array.size(); j++){
					JSONObject linkJSON = (JSONObject) array.get(j);
					links.put((String)linkJSON.get("Title"), (String)linkJSON.get("URL"));
				}
				if (Resources.CONFG.getBoolean("InternalCaching")){
					linkCache.put(plugin.toUpperCase(), links);
					dataCache.put(plugin.toUpperCase(),new BookData(true,links,plugin.toUpperCase(),version,chapters,"PluginDoumentation",contents));
				}
				return new BookData(true,links,plugin.toUpperCase(),version,chapters,"PluginDoumentation",contents);
			}else{
				if (Resources.CONFG.getBoolean("InternalCaching")){
					dataCache.put(plugin.toUpperCase(),new BookData(false,null,plugin.toUpperCase(),version,chapters,"PluginDoumentation",contents));
				}
				return new BookData(false,null,plugin.toUpperCase(),version,chapters,"PluginDoumentation",contents);
			}
		}
	}
	
	/**
	 * Attempts to fetch BookData.
	 * @param plugin Name of the plugin to get data for.
	 * @return The BookData.
	 * @throws Exception If any error is encountered.
	 */
	public static List<BookData> newBookDataList(String plugin) throws Exception{
		List<BookData> data = new ArrayList<BookData>();
		String version;
		JSONObject json = JSONUtils.listToJSON(URLUtils.readGithub(plugin.toUpperCase()+"/index.json"));
		version = (String) json.get("Version");
		JSONArray array = (JSONArray) json.get("Sections");
		for (int iterator = 0; iterator < array.size(); iterator++){
			if (dataCache.containsKey(array.get(iterator))){
				data.add(dataCache.get(array.get(iterator)));
			}else{
				JSONObject sectionJSON = JSONUtils.listToJSON(URLUtils.readGithub(plugin.toUpperCase()+"/"+array.get(iterator)+"/index.json"));
				//Modified, but taken from newBookData()
				int chapters = (int) sectionJSON.get("Chapters");
				HashMap<Integer, List<String>> contents = null;
				boolean startAtOne = false;
				if (!URLUtils.URLReader("https://raw.github.com/austinv11/Plugin-Documentation/master/"+plugin.toUpperCase()+"/"+array.get(iterator)+"/Chatpter0.txt").contains("Not Found")){
					startAtOne = true;
				}
				int key = 0;
				for (int i = 0; i < chapters; i++){
					key = i;
					if (startAtOne){
						key++;
					}
					contents.put(key, URLUtils.readGithub(plugin.toUpperCase()+"/"+array.get(iterator)+"/Chapter"+key+".txt"));
				}
				if ((boolean) sectionJSON.get("HasLinks")){
					HashMap<String,String> links = null;
					JSONArray sectionArray = (JSONArray) sectionJSON.get("Links");
					sectionArray.iterator();
					for (int j = 0; j < sectionArray.size(); j++){
						JSONObject linkJSON = (JSONObject) sectionArray.get(j);
						links.put((String)linkJSON.get("Title"), (String)linkJSON.get("URL"));
					}
					if (Resources.CONFG.getBoolean("InternalCaching")){
						linkCache.put(plugin.toUpperCase()+":"+(String) array.get(iterator), links);
						dataCache.put((String)array.get(iterator), new BookData(true,links,plugin.toUpperCase()+":"+(String) array.get(iterator),version,chapters,"PluginDoumentation",contents));
					}
					data.add(new BookData(true,links,plugin.toUpperCase()+":"+(String) array.get(iterator),version,chapters,"PluginDoumentation",contents));
				}else{
					if (Resources.CONFG.getBoolean("InternalCaching")){
						dataCache.put((String)array.get(iterator), new BookData(false,null,plugin.toUpperCase()+":"+(String) array.get(iterator),version,chapters,"PluginDoumentation",contents));
					}
					data.add(new BookData(false,null,plugin.toUpperCase()+":"+(String) array.get(iterator),version,chapters,"PluginDoumentation",contents));
				}
			}
		}
		return data;
	}
}