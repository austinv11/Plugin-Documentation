package io.github.austinv11.PluginDocumentation.Lib;

import org.bukkit.inventory.meta.BookMeta;

public class BookMetaHelper {
	
	public static String sendToPastebin(BookMeta bm) throws Exception{
		String contents = null;
		contents = "AUTHOR: "+bm.getAuthor();
		contents = "\nTITLE: "+bm.getTitle();
		for (String s : bm.getPages()){
			contents = contents+"\n"+s;
		}
		return URLUtils.sendToPastebin(contents);
	}
}
