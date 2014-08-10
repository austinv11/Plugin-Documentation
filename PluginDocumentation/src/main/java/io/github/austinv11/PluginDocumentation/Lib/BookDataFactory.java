package io.github.austinv11.PluginDocumentation.Lib;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import io.github.austinv11.PluginDocumentation.API.BookData;

public class BookDataFactory {
	public static HashMap<String, List<String>> linkCache = null;
	
	public static void dump(){
		linkCache = null;
	}
	
	public static BookData newBookData(String plugin) throws Exception{
		String version;
		JSONObject json = JSONUtils.listToJSON(URLUtils.readGithub(plugin.toUpperCase()+"/index.json"));
		version = (String) json.get("Version");
		if ((boolean) json.get("Sectioned")){
			
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
				return new BookData(true,links,plugin.toUpperCase(),version,chapters,"PluginDoumentation",contents);
			}else{
				return new BookData(false,null,plugin.toUpperCase(),version,chapters,"PluginDoumentation",contents);
			}
		}
		return null;
	}
}
