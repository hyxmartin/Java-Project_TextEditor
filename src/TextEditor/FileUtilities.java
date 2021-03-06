

package TextEditor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/** FileUtilities class provides some basic tools to read a file, count words, search and replace strings. 
 */
public class FileUtilities {

	/**readFile() method takes filename as a string parameter. 
	 * It opens the file which is assumed to be residing in the project folder.
	 * It reads the file's each line into a StringBuilder. While reading, 
	 * it preserves the line breaks. It then returns the StringBuilder.
	 */
	public StringBuilder readFile(String filename)  {
		StringBuilder tempStringBuilder = new StringBuilder();
		File file = new File(filename);
		// Using try catch to capture if file is not found.
		try {
			Scanner input = new Scanner(file);
			while (input.hasNextLine()) {
				tempStringBuilder.append(input.nextLine() + "\n");  // save to tempStringBuilder line by line.
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tempStringBuilder;				
	}
	
	/** wordCount method receives text content in a StringBuilder object and 
	 * returns its word count.  It considers any character not an alphabet, number, or a single quote as a word-delimiter.   
	 */
	int countWords(StringBuilder fileContent) {
		int wordCount = 0;
		String[] words = fileContent.toString().split("[^a-zA-Z0-9']+");  // Saved words separated by any character not an alphabet, number, or a single quote.
		wordCount = words.length;
		return wordCount;
	}
	
	/** countUniqueWords method receives text content in a StringBuilder object and 
	 * returns its unique word count. It considers any character not an alphabet, number, or a single quote as a word-delimiter.  
	 */
	int countUniqueWords(StringBuilder fileContent) {
		Set<Word> uniqueWordSet = new HashSet<>();
		int wordCount = 0;
		String[] words = fileContent.toString().split("[^a-zA-Z0-9']+");  // Saved words separated by any character not an alphabet, number, or a single quote.
		for (String w : words) {
			uniqueWordSet.add(new Word(w.toLowerCase()));  // Use hashset to ensure uniqueness
		}
		wordCount = uniqueWordSet.size();
		return wordCount;
	}
	
	/**searchAll() method receives text content in a StringBuilder and 
	 * returns all the char-positions at which searchString is found.
	 * These positions are counted from the beginning of the file starting with 0.
	 * For example, if the fileContent has: "What a wonderful world", 
	 * the searchString 'wonder' is at char-position 7. 
	 * The search is case-sensitive. 
	 * If the searchString is not found, it returns null. 
	 * Hints: 
	 * 1. You may find String methods indexOf() and substring() useful. 
	 * 2. You may have to traverse the fileContent twice. Once to count the number of times 
	 * the searchString is found, and second time to store its positions. 
	 */
	int[] searchAll(StringBuilder fileContent, String searchString) {
		int index = -1;
		int searchCount = -1;  // assign -1 because the do while loop will run before to check condition.
		int startSearchPosition = 0;
		// Any input of the method is null will return null
		if (fileContent == null || searchString == null || searchString.isEmpty()) {
			return null;	
		} else {
			do {
				searchCount++;
				index = fileContent.indexOf(searchString, startSearchPosition);
				startSearchPosition = index + searchString.length();  // Start Position updates for each iteration by adding the search string length.
			} while (index != -1);
			startSearchPosition = 0;  // Set by Start Search point to 0
			if (searchCount > 0) {
				int[] indexArray = new int[searchCount];
				// The for loop assign the index of each searched result to int[].
				for (int i = 0; i < indexArray.length; i++) {
					indexArray[i] = fileContent.indexOf(searchString, startSearchPosition);
					startSearchPosition = indexArray[i] + searchString.length();
				}
				return indexArray;
			}
		}
		return null;
	}


	/** replace method receives text content in a StringBuilder object, and 
	 * replaces all occurrences of oldString with new String.
	 * It returns the number of replacements done.
	 * If oldString is not found, it means that no replacement happens. In such a case, it returns 0.
	 */
	int replace(StringBuilder fileContent, String oldString, String newString) {
		int replacementCount = 0;
		int startSearchPosition = 0;
		int index = - 1;
		// using while loop to to search next starting position, 
		// if nothing found, the index is -1
		// Any input of the method is null will return 0
		if (fileContent == null || oldString == null || newString == null || oldString.isEmpty() || newString.isEmpty()) {
			return 0;
		} else {
			while (fileContent.indexOf(oldString, startSearchPosition) != -1) {
				// Find index of next search.
				index = fileContent.indexOf(oldString, startSearchPosition);
				fileContent.replace(index, index + oldString.length(), newString);
				// Start position changed by the searched index plus the replaced string length.
				// In this way, it avoids incorrect replacement in case the new string has the same keyword as the old string.
				startSearchPosition = index + newString.length();  
				replacementCount++;  // Check count of replacement by iteration
			}
			return replacementCount;
		}
	}
	
	
	/**writeFile() method takes filename and fileContent as a string parameter. 
	 * It returns �File saved� from within try-catch block of BufferedWriter when the file is successfully written.
	 * it should return �Could not save file� from the catch block. 
	 * Whatever is returned, the handler updates statusLabel with it.
	 */
	public String writeFile(String filename, String fileContent)  {
		String status = null;
		try {
	        FileWriter filerWriter = new FileWriter(filename);
	        BufferedWriter bw = new BufferedWriter(filerWriter);  // Use BufferedWriter to handle file writing.
	        bw.write(fileContent);
	        bw.close();
	        filerWriter.close();
	        status = "File Saved";
	    } catch (IOException e) {
	        e.printStackTrace();
	        status = "Could not save file";
	    } 
		return status;
	}
	
}
