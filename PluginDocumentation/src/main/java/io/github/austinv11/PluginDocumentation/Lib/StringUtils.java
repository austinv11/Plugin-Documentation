package io.github.austinv11.PluginDocumentation.Lib;

public class StringUtils {//FIXME
	
	/**
	 * Improved String.substring() method, prevents cutting off words.
	 * @deprecated
	 * @param string String to cut.
	 * @param beginIndex The beginning index, inclusive.
	 * @return The suggested beginIndex.
	 */
	public static int wrapText(String string, int beginIndex){
		int i = 1;
		while ((beginIndex - i) >= 0){
			while (string.charAt(beginIndex - i) != ' '){
				i++;
			}
		}
		return beginIndex-i;
	}
	
	/**
	 * Improved String.substring() method, prevents cutting off words.
	 * @deprecated
	 * @param string String to cut.
	 * @param beginIndex The beginning index, inclusive.
	 * @param endIndex The ending index, exclusive.
	 * @return The suggested endIndex.
	 */
	public static int wrapText(String string, int beginIndex, int endIndex){
		int i = 1;
		while ((endIndex - i) >= 0){
			while (string.charAt(endIndex - i) != ' '){
				i++;
			}
		}
		return endIndex-i;
	}
}
