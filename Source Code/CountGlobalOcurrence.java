package models;

//https://www.mkyong.com/java/jackson-streaming-api-to-read-and-write-json/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.JsonMappingException;

////////////

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class CountGlobalOcurrence {

	// public static String fileName = "F:\\Thesis\\Amazon Dataset\\reviews_CDs_and_Vinyl.json\\reviews_CDs_and_Vinyl.json_out.csv";
	public static String fileName = "F:\\Thesis\\Amazon Dataset\\reviews_Clothing_Shoes_and_Jewelry.json\\reviews_Clothing_Shoes_and_Jewelry.json_out.csv";

	public static void main(String[] args) {

		HashMap<String, Integer> uniqueKeySet = new HashMap();

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {

				String[] termWithLocalOccurrence = line.split(",");
				if (termWithLocalOccurrence.length == 3) {
					
					String myKey = termWithLocalOccurrence[0] + "," + termWithLocalOccurrence[1];
					
					if (uniqueKeySet.containsKey(myKey)) {
						uniqueKeySet.put(myKey, uniqueKeySet.get(myKey) + Integer.parseInt(termWithLocalOccurrence[2]) );
					} else {
						uniqueKeySet.put(myKey, Integer.parseInt(termWithLocalOccurrence[2]));
					}
				}

			}
			
			///////////////////////
			
			// Get a set of the entries
			Set<Entry<String, Integer>> set = uniqueKeySet.entrySet();

			// Get an iterator
			java.util.Iterator<Entry<String, Integer>> i = set.iterator();

			// Display elements
			while (i.hasNext()) {
				Map.Entry me = (Map.Entry) i.next();
				// System.out.print(me.getKey() + ": ");
				// System.out.println(me.getValue());

				writeToFile(fileName + "_global_occurrence.csv", me.getKey() + "," + me.getValue());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

		

	public static void writeToFile(String fileName, String content) {
		try {
			File file = new File(fileName);
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write(content + System.getProperty("line.separator"));
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
