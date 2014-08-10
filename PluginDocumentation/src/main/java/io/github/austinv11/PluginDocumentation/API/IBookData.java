package io.github.austinv11.PluginDocumentation.API;

import java.util.HashMap;
import java.util.List;

public interface IBookData {
	
	/**
	 * If it has links, when the book is opened, it will send the player links.
	 * @return Whether the book has links.
	 */
	public boolean hasLinks();
	
	/**
	 * If it has links, the links will be accessed through this method.
	 * @return Hashmap with the links, formatted with <Title,URL>.
	 */
	public HashMap<String,String> getLinks();
	
	/**
	 * Gets the name of the book (either the plugin name or section name).
	 * @return The title.
	 */
	public String getTitle();
	
	/**
	 * Gets the version of the book, in order to determine whether to reload the cached version.
	 * @return The version.
	 */
	public String getVersion();
	
	/**
	 * Gets the number of chapters in the book.
	 * @return Number of chapters.
	 */
	public int numberOfChapters();
	
	/**
	 * Gets the 'author' of the book, this is usually the name of the plugin that generated the BookData.
	 * @return Author name.
	 */
	public String getAuthor();
	
	/**
	 * Gets the contents of the book.
	 * @return Hasmap with the contents of the chapters, formatted with <Chapter Number, Chapter Contents>.
	 */
	public HashMap<Integer, List<String>> getContents();
}