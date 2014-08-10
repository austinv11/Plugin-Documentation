package io.github.austinv11.PluginDocumentation.Lib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class URLUtils {
	
	public static List<String> URLReader(String url) throws Exception{
		if (!url.contains("http://") && !url.contains("https://")){
			url = "http://"+url;
		}
		/*if (!url.endsWith("/")){
			url = url+"/";
		}*/
		URL input = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(input.openStream()));
		String temp;
		List<String> read = new ArrayList<String>();
		while ((temp = in.readLine()) != null){
			read.add(temp);
		}
		in.close();
		return read;
	}
	
	public static List<String> readGithub(String path) throws Exception{
		return URLReader("https://raw.github.com/austinv11/Plugin-Documentation/master/"+path);
	}
}
