package io.github.austinv11.PluginDocumentation.API;

import io.github.austinv11.PluginDocumentation.Main.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookFactory {
	private static HashMap<String, ItemStack> bookCache;
	
	/**
	 * Creates a new written book with the given data.
	 * @param data The source of information for the book.
	 * @return The new written book.
	 */
	public static ItemStack newBook(BookData data){
		if (!bookCache.containsKey(data.getTitle())){
			ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
			BookMeta bm = (BookMeta) book.getItemMeta();
			bm.setAuthor(data.getAuthor());
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY+"Version: "+data.getVersion());
			bm.setLore(lore);
			bm.setDisplayName(data.getTitle());
			bm.setTitle(data.getTitle());
			String[] array = (String[]) data.getPages().toArray();
			bm.addPage(array);
			book.setItemMeta(bm);
			if (Resources.CONFG.getBoolean("InternalCaching")){
				bookCache.put(data.getTitle(), book);
			}
			return book;
		}else{
			return bookCache.get(data.getTitle());
		}
	}
	
	/**
	 * Dumps the cached data.
	 */
	public static void dump(){
		bookCache = null;
	}
}
