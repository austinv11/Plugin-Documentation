package io.github.austinv11.PluginDocumentation.Lib;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class JSONUtils {
	
	public static JSONObject listToJSON(List<String> list) throws Exception{
		String json = null;
		for (int i = 0; i < list.size(); i++){
			json = json+list.get(i);
		}
		/*JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(json);*/
		return (JSONObject) JSONValue.parse(json);
	}
}
