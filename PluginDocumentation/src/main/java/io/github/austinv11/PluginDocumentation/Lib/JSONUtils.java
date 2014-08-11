package io.github.austinv11.PluginDocumentation.Lib;

import io.github.austinv11.PluginDocumentation.Main.Resources;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONUtils {
	
	public static Index listToJSON(List<String> list) throws Exception{
		String json = null;
		for (int i = 0; i < list.size(); i++){
			if (list.get(i) != null){
				if (json != null){
					json = json+list.get(i);
				}else{
					json = list.get(i);
				}
			}
		}
		Gson gson = new GsonBuilder().serializeNulls().create();
		/*JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(json);
		return (JSONObject) JSONValue.parse(json);*/
		/*Resources.LOGGER.info("TEST:"+json);
		Resources.LOGGER.info("TEST2:"+gson.toJson(gson.fromJson(json, Index.class)));*/
		return gson.fromJson(json, Index.class);
	}
}
