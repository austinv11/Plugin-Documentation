package io.github.austinv11.PluginDocumentation.API;

import io.github.austinv11.PluginDocumentation.Lib.StringUtils;
import io.github.austinv11.PluginDocumentation.Main.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;

public class BookData implements IBookData{
	private boolean HAS_LINKS = false;
	private HashMap<String, String> LINKS = new HashMap<String,String>();
	private String TITLE = null;
	private String VERSION = null;
	private int NUMBER_OF_CHAPTERS = 0;
	private String AUTHOR = "PluginDocumentation";
	private HashMap<Integer, List<String>> CONTENTS = new HashMap<Integer,List<String>>();
	private static HashMap<String,List<String>> pages = new HashMap<String,List<String>>();
	
	/**
	 * Used to create BlockData with default values, modifiable through methods.
	 */
	public BookData(){}
	
	/**
	 * Used to initialize BlockData with all values already inputed.
	 * @param hasLinks Whether the book has links.
	 * @param links Hashmap with the links, formatted with <Title,URL>.
	 * @param title The title.
	 * @param version The version.
	 * @param numberOfChapters Number of chapters.
	 * @param author Author name.
	 * @param contents Hashmap with the contents of the chapters, formatted with <Chapter Number, Chapter Contents>.
	 */
	public BookData(boolean hasLinks, HashMap<String, String> links, String title, String version, int numberOfChapters, String author, HashMap<Integer, List<String>> contents){
		HAS_LINKS = hasLinks;
		LINKS = links;
		TITLE = title;
		VERSION = version;
		NUMBER_OF_CHAPTERS = numberOfChapters;
		AUTHOR = author;
		CONTENTS = contents;
	}

	/**
	 * Sets hasLinks.
	 * @param hasLinks Whether the book has links.
	 */
	public void setHasLinks(boolean hasLinks){
		HAS_LINKS = hasLinks;
	}
	
	/**
	 * Sets the links.
	 * @param links Hashmap with the links, formatted with <Title,URL>.
	 */
	public void setLinks(HashMap<String, String> links){
		LINKS = links;
	}
	
	/**
	 * Sets the title.
	 * @param title The title.
	 */
	public void setTitle(String title){
		TITLE = title;
	}
	
	/**
	 * Sets the version.
	 * @param version The version.
	 */
	public void setVersion(String version){
		VERSION = version;
	}
	
	/**
	 * Sets the number of chapters.
	 * @param numberOfChapters Number of chapters.
	 */
	public void setNumberOfChapters(int numberOfChapters){
		NUMBER_OF_CHAPTERS = numberOfChapters;
	}
	
	/**
	 * Sets the author.
	 * @param author Author name.
	 */
	public void setAuthor(String author){
		AUTHOR = author;
	}
	
	/**
	 * Sets the contents.
	 * @param contents Hashmap with the contents of the chapters, formatted with <Chapter Number, Chapter Contents>.
	 */
	public void setContents(HashMap<Integer, List<String>> contents){
		CONTENTS = contents;
	}
	
	@Override
	public boolean hasLinks() {
		return HAS_LINKS;
	}

	@Override
	public HashMap<String, String> getLinks() {
		return LINKS;
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public String getVersion() {
		return VERSION;
	}

	@Override
	public int numberOfChapters() {
		return NUMBER_OF_CHAPTERS;
	}

	@Override
	public String getAuthor() {
		return AUTHOR;
	}

	@Override
	public HashMap<Integer, List<String>> getContents() {
		return CONTENTS;
	}

	private List<String> formatContents(boolean cache){
		List<String> page = new ArrayList<String>();
		List<String> temp = new ArrayList<String>();
		int key;
		if (CONTENTS != null){
			for (int i = 0; i < NUMBER_OF_CHAPTERS; i++){
				String chapter = null;
				String text = null;
				key = i;
				if (!CONTENTS.containsKey(0)){
					key++;
				}
				temp = CONTENTS.get(key);
				//Resources.LOGGER.info(temp.toString());
				if (temp.get(0).contains("$$TITLE$$")){
					chapter = ChatColor.translateAlternateColorCodes('&', temp.get(0).replace("$$TITLE$$", "").replaceFirst("=", "").replaceAll("\"", "").trim());
					temp.remove(0);
				}
				if (chapter != null){
					text = chapter+"-\n"+ChatColor.RESET;
				}
				for (int j = 0; j < temp.size(); j++){
					if (text != null){
						text = text+"\n"+ChatColor.translateAlternateColorCodes('&', temp.get(j));
					}else{
						text = ChatColor.translateAlternateColorCodes('&', temp.get(j));
					}
				}
				//Resources.LOGGER.info(text);
				if (text.length() > 256){//TODO improve page wrapping (so words don't get cut off)
					while (text.length() > 256){
						//Resources.LOGGER.info("1"+text.substring(0, 256));
						//Resources.LOGGER.info("2"+text.substring(256));
						//int endIndex = StringUtils.wrapText(text, 0, 256);
						//Resources.LOGGER.info("1"+endIndex);
						//Resources.LOGGER.info("2"+text.substring(0, endIndex).trim());
						page.add(text.substring(0, 256).trim());
						text = text.substring(256);
						Resources.LOGGER.info("3"+text);
						if (chapter != null){
							text = chapter+ChatColor.RESET+" (cont.)-\n\n"+text;
						}
						if (text.length() <= 256){
							page.add(text.trim());
						}
					}
				}else{
					page.add(text);
				}
			}
		}else{
			page = null;
		}
		if (cache){
			pages.put(this.TITLE, page);
			return null;
		}else{
			return page;
		}
	}
	
	@Override
	public List<String> getPages() {
		if (!pages.containsKey(this.TITLE) && Resources.CONFG.getBoolean("InternalCaching")){
			formatContents(true);
			return pages.get(this.TITLE);
		}else{
			return formatContents(false);
		}
	}
	
	/**
	 * Dumps the cached data.
	 */
	public static void dump(){
		pages = new HashMap<String,List<String>>();
	}
}
