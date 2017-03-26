package models;

//https://www.mkyong.com/java/jackson-streaming-api-to-read-and-write-json/

import java.io.File;
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

public class HugeJSONRead2 {
	
	public static String [] partsSpeechTokens = {"NN", "NNS","NNP", "NNPS" };
	
	public static String [] asinList = {"0188399518","0705391752", "097293751X", "0980027586", "1586637304", "1592922929", "6148479613" };//asin
	
	public static String [] blackList ={"today", "january","sunday", "friday","day", "month","year","tomorrow","afternoon","night", "monday", "tuesday", "wednesday", "thursday", "saturday","sunday", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december", "week", "year", "time", "weeks", "days", "years", "hours", "months", "hour" };
	
	//public static String fileName = "F:\\Thesis\\Amazon Dataset\\reviews_CDs_and_Vinyl.json\\reviews_CDs_and_Vinyl.json";
	
	public static String fileName = "F:\\Thesis\\Amazon Dataset\\reviews_Baby.json\\reviews_Baby.json";
	
//	public static String fileName = "F:\\Thesis\\Amazon Dataset\\reviews_Clothing_Shoes_and_Jewelry.json\\reviews_Clothing_Shoes_and_Jewelry.json";

	public static void main(String[] args) {

		try {

			JsonFactory jfactory = new JsonFactory();
			// Initialize the tagger
			MaxentTagger tagger = new MaxentTagger(
					"F:\\stanfordAnnotator\\models\\english-left3words-distsim.tagger");

			/*** read from file ***/
			JsonParser jParser = jfactory.createJsonParser(
					new File(fileName));

			// loop until token equal to "}"
			    String asin = null; // asin
			while (jParser.nextToken() != null)
				while (jParser.nextToken() != JsonToken.END_OBJECT) {

					String fieldname = jParser.getCurrentName();

					  if ("asin".equals(fieldname)){

						// current token is "age",
						// move to next, which is "name"'s value
						jParser.nextToken();
						  asin = jParser.getText();
						  System.out.println("ASIN:"+asin); // display 29//asin
					  }
					  
					  else if ("reviewText".equals(fieldname) && asin!=null && Arrays.asList(asinList).contains(asin)) {//asin
						  jParser.nextToken();
	                        String reviewTextString = jParser.getText();
	                        System.out.println(reviewTextString); // display 29

						////////////////////

						

						// The tagged string
						String tagged = null;
						try {
							tagged = tagger.tagString(reviewTextString);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						// Output the result
						System.out.println(tagged);
						
						///////// Here we extract the parts of speech
						
						String [] parts = tagged.split(" ");
						HashMap<String,Integer> uniqueKeySet = new HashMap();
						for(String token:parts){
							String [] stringAndMarker = token.split("_"); // ALBUM_NN -> ALBUM, NN
							
							if(stringAndMarker!=null && stringAndMarker.length==2 &&  Arrays.asList(partsSpeechTokens).contains(stringAndMarker[1])){
								
								String rawText = stringAndMarker[0];
								rawText = rawText.replaceAll("\\p{Punct}|\\d"," "); // removed all punctuation anywhere.
								rawText = rawText.toLowerCase(); // all are in lower case now
								rawText = rawText.replaceAll("\\d+",""); // removes any number anywhere.
								rawText = rawText.trim();
								if(rawText.length()>2 && !Arrays.asList(blackList).contains(rawText)){								
									//writeToFile(fileName+"_out.csv",stringAndMarker[1]+","+rawText);
									if(uniqueKeySet.containsKey(stringAndMarker[1]+","+rawText)){
										uniqueKeySet.put(stringAndMarker[1]+","+rawText, uniqueKeySet.get(stringAndMarker[1]+","+rawText)+1);
									} else {
										uniqueKeySet.put(stringAndMarker[1]+","+rawText, 1);
									}
								}
								
							}
						}
						
						// Get a set of the entries
					      Set<Entry<String, Integer>> set = uniqueKeySet.entrySet();
					      
					      // Get an iterator
					      java.util.Iterator<Entry<String, Integer>> i = set.iterator();
					      
					      // Display elements
					      while(i.hasNext()) {
					         Map.Entry me = (Map.Entry)i.next();
//					         System.out.print(me.getKey() + ": ");
//					         System.out.println(me.getValue());
					         
					         writeToFile(fileName+"_out.csv",me.getKey()+","+me.getValue());
					      }
						
						writeToFile(fileName+"_out.csv",",");

					}
					
					

				}
			
			
			
			jParser.close();

		} catch (JsonGenerationException e) {

			e.printStackTrace();

		} catch (JsonMappingException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		
	}

	
	public static void writeToFile(String fileName, String content) {	
		try {
			File file = new File(fileName);
			FileWriter fileWriter = new FileWriter(file,true);
			fileWriter.write(content+System.getProperty("line.separator"));
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
