package io.github.austinv11.PluginDocumentation.Lib;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class URLUtils {
	
	/**
	 * Gets a list representing the contents of the given url.
	 * @param url The url to read from.
	 * @return A list representing the contents of the given url.
	 * @throws Exception
	 */
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
	
	/**
	 * Shortcut method, automatically appends path to the PluginDocumentation (raw) repo.
	 * @see URLReader
	 * @param path The path from the root directory in the PluginDocumentation repo.
	 * @return A list representing the contents of the given url.
	 * @throws Exception
	 */
	public static List<String> readGithub(String path) throws Exception{
		List<String> in = URLReader("https://raw.github.com/austinv11/Plugin-Documentation/master/"+path);
		if (in.contains("Not Found")){
			throw new NotFoundException("404 Path "+path+" not found");
		}
		return in;
	}
	
	/**
	 * Sends the given string to pastebin.
	 * @param contents String to send to pastebin.
	 * @return Link to paste.
	 * @throws Exception
	 */
	public static String sendToPastebin(String contents) throws Exception{
		StringBuffer a = new StringBuffer("api_dev_key=fe38c64db3a9bd5a8fe5e81d890a69ad");
		a.append("&api_option=paste");
		a.append("&api_paste_code="+contents);
		a.append("&api_paste_name=BookData");
		String text = a.toString();
		URL url = new URL("http://pastebin.com/api/api_post.php");
		HttpURLConnection urlC = (HttpURLConnection) url.openConnection();
		urlC.setDoOutput(true);
		urlC.setDoInput(true);
		urlC.setInstanceFollowRedirects(false);
		urlC.setRequestMethod("POST");
		urlC.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		urlC.setRequestProperty("charset", "utf-8");
        urlC.setRequestProperty("Content-Length", "" + text.getBytes().length);
        urlC.setUseCaches(false);
        DataOutputStream wr = new DataOutputStream(urlC.getOutputStream());
        wr.writeBytes(text);
        wr.flush();
        wr.close();
        urlC.disconnect();
        Scanner s = new Scanner(urlC.getInputStream());
        ArrayList<String> output = new ArrayList<String>();
        while (s.hasNext()) {
        	String next = s.nextLine();
            output.add(next);
        }
        s.close();
		return output.get(0);
	}
}
