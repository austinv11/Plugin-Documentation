package io.github.austinv11.PluginDocumentation.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import io.github.austinv11.PluginDocumentation.Lib.Index;
import io.github.austinv11.PluginDocumentation.Lib.JSONUtils;
import io.github.austinv11.PluginDocumentation.Lib.Links;
import io.github.austinv11.PluginDocumentation.Lib.URLUtils;
import io.github.austinv11.PluginDocumentation.Main.Resources;

public class BookDataFactory {
	public static HashMap<String, HashMap<String,String>> linkCache = new HashMap<String, HashMap<String,String>>();
	private static HashMap<String, BookData> dataCache = new HashMap<String, BookData>();
	
	/**
	 * Dumps the cached data.
	 */
	public static void dump(){
		linkCache = new HashMap<String, HashMap<String,String>>();
		dataCache = new HashMap<String,BookData>();
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
		//JSONObject json = JSONUtils.listToJSON(URLUtils.readGithub(plugin.toUpperCase()+"/index.json"));
		Index json = JSONUtils.listToJSON(URLUtils.readGithub(plugin.toUpperCase()+"/index.json"));
		//version = (String) json.get("Version");
		version = json.Version;
		if (/*(boolean) json.get("Sectioned")*/json.Sectioned){
			return null;
		}else{
			//int chapters = (int) json.get("Chapters");
			int chapters = json.Chapters;
			HashMap<Integer, List<String>> contents = new HashMap<Integer, List<String>>();
			boolean startAtOne = false;
			try{
				URLUtils.URLReader("https://raw.github.com/austinv11/Plugin-Documentation/master/"+plugin.toUpperCase()+"/Chapter0.txt");
			}catch(Exception e){
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
			if (/*(boolean) json.get("HasLinks")*/json.HasLinks){
				HashMap<String,String> links = new HashMap<String,String>();
				//JSONArray array = (JSONArray) json.get("Links");
				Links[] array = json.Links;
				for (int j = 0; j < /*array.size()*/array.length; j++){
					/*JSONObject linkJSON = (JSONObject) array.get(j);
					links.put((String)linkJSON.get("Title"), (String)linkJSON.get("URL"));*/
					Links linkJSON = array[j];
					links.put(linkJSON.Title, linkJSON.URL);
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
		//JSONObject json = JSONUtils.listToJSON(URLUtils.readGithub(plugin.toUpperCase()+"/index.json"));
		Index json = JSONUtils.listToJSON(URLUtils.readGithub(plugin.toUpperCase()+"/index.json"));
		//version = (String) json.get("Version");
		version = json.Version;
		//JSONArray array = (JSONArray) json.get("Sections");
		String[] array = json.Sections;
		for (int iterator = 0; iterator < /*array.size()*/array.length; iterator++){
			if (dataCache.containsKey(/*array.get(iterator)*/array[iterator])){
				data.add(dataCache.get(/*array.get(iterator)*/array[iterator]));
			}else{
				HashMap<Integer, List<String>> contents = new HashMap<Integer, List<String>>();
				//JSONObject sectionJSON = JSONUtils.listToJSON(URLUtils.readGithub(plugin.toUpperCase()+"/"+array.get(iterator)+"/index.json"));
				Index sectionJSON = JSONUtils.listToJSON(URLUtils.readGithub(plugin.toUpperCase()+"/"+array[iterator]+"/index.json"));
				//Modified, but taken from newBookData()
				//int chapters = (int) sectionJSON.get("Chapters");
				int chapters = sectionJSON.Chapters;
				if (chapters == 0){
					chapters = 1;
				}
				//Resources.LOGGER.info(chapters+"");
				boolean startAtOne = false;
				try{
					URLUtils.URLReader("https://raw.github.com/austinv11/Plugin-Documentation/master/"+plugin.toUpperCase()+"/"+array[iterator]/*.get(iterator)*/+"/Chapter0.txt");
				}catch(Exception e){
					startAtOne = true;
				}
				int key = 0;
				for (int i = 0; i < chapters; i++){
					key = i;
					if (startAtOne){
						key++;
					}
					//Resources.LOGGER.info(URLUtils.readGithub(plugin.toUpperCase()+"/"+array[iterator]/*.get(iterator)*/+"/Chapter"+key+".txt").toString());
					contents.put(key, URLUtils.readGithub(plugin.toUpperCase()+"/"+array[iterator]/*.get(iterator)*/+"/Chapter"+key+".txt"));
				}
				//Resources.LOGGER.info("TEST:"+contents.toString());
				if (/*(boolean) sectionJSON.get("HasLinks")*/sectionJSON.HasLinks){
					HashMap<String,String> links = new HashMap<String, String>();
					//JSONArray sectionArray = (JSONArray) sectionJSON.get("Links");
					Links[] sectionArray = sectionJSON.Links;
					for (int j = 0; j < sectionArray.length/*.size()*/; j++){
						//JSONObject linkJSON = (JSONObject) sectionArray.get(j);
						Links linkJSON = sectionArray[j];
						//links.put((String)linkJSON.get("Title"), (String)linkJSON.get("URL"));
						links.put(linkJSON.Title, linkJSON.URL);
					}
					if (Resources.CONFG.getBoolean("InternalCaching")){
						/*linkCache.put(plugin.toUpperCase()+":"+(String) array.get(iterator), links);
						dataCache.put((String)array.get(iterator), new BookData(true,links,plugin.toUpperCase()+":"+(String) array.get(iterator),version,chapters,"PluginDoumentation",contents));*/
						linkCache.put(plugin.toUpperCase()+":"+array[iterator], links);
						dataCache.put(array[iterator], new BookData(true,links,plugin.toUpperCase()+":"+array[iterator],version,chapters,"PluginDoumentation",contents));
					}
					data.add(new BookData(true,links,plugin.toUpperCase()+":"+/*(String) array.get(iterator)*/array[iterator],version,chapters,"PluginDoumentation",contents));
				}else{
					if (Resources.CONFG.getBoolean("InternalCaching")){
						//dataCache.put((String)array.get(iterator), new BookData(false,null,plugin.toUpperCase()+":"+(String) array.get(iterator),version,chapters,"PluginDoumentation",contents));
						dataCache.put(array[iterator], new BookData(false,null,plugin.toUpperCase()+":"+array[iterator],version,chapters,"PluginDoumentation",contents));
					}
					data.add(new BookData(false,null,plugin.toUpperCase()+":"+/*(String) array.get(iterator)*/array[iterator],version,chapters,"PluginDoumentation",contents));
				}
			}
		}
		return data;
	}
}