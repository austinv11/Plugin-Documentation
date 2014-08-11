package io.github.austinv11.PluginDocumentation.Lib;

import org.bukkit.inventory.meta.BookMeta;

public class BookMetaHelper {
	
	/**
	 * Serializes the given BookMeta and pastes it to pastebin.
	 * @param bm The BookMeta from the book to be sent to pastebin.
	 * @return The link for the new paste.
	 * @throws Exception
	 */
	public static String sendToPastebin(BookMeta bm) throws Exception{
		String contents = null;
		contents = "AUTHOR: "+bm.getAuthor();
		contents = contents+"\nTITLE: "+bm.getTitle();
		for (String s : bm.getPages()){
			contents = contents+"\n"+s;
		}
		return URLUtils.sendToPastebin(contents);
	}
}
