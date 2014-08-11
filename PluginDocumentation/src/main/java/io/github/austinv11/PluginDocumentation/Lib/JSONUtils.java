package io.github.austinv11.PluginDocumentation.Lib;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONUtils {
	
	public static Index listToJSON(List<String> list) throws Exception{
		String json = null;
		for (int i = 0; i < list.size(); i++){
			json = json+list.get(i);
		}
		Gson gson = new GsonBuilder().serializeNulls().create();
		/*JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(json);
		return (JSONObject) JSONValue.parse(json);*/
		return gson.fromJson(json, Index.class);
	}
}
