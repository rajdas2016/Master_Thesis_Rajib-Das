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

public class HugeJSONRead3 { // generates csv with productkey
	
	public static String [] partsSpeechTokens = {"NN", "NNS","NNP"};
	
	//public static String [] asinList = {"B00LA89DW2","B00LBAM588" };//asin
	
	public static String [] blackList ={"today", "january","sunday", "friday","day", "month","year","tomorrow","afternoon","night", "monday", "tuesday", "wednesday", "thursday", "saturday","sunday", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december", "week", "year", "time", "weeks", "days", "years", "hours", "months", "hour","product" };
	
	//public static String fileName = "F:\\Thesis\\Amazon Dataset\\reviews_Video_Games.json\\reviews_Video_Games.json";
	
	
	//public static String fileName = "F:\\Thesis\\Amazon Dataset\\reviews_Clothing_Shoes_and_Jewelry.json\\reviews_Clothing_Shoes_and_Jewelry.json";
	
     // public static String fileName = "F:\\Thesis\\Amazon Dataset\\reviews_Automotive.json\\reviews_Automotive.json"; 
	
	public static String fileName = "F:\\Thesis\\Amazon Dataset\\reviews_Baby.json\\reviews_Baby.json";
	
	//public static String fileName = "F:\\Thesis\\Amazon Dataset\\reviews_Automotive.json\\reviews_Automotive.json
	
	
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
			    String reviewId = null;
			while (jParser.nextToken() != null)
				while (jParser.nextToken() != JsonToken.END_OBJECT) {

					String fieldname = jParser.getCurrentName();

					 
					
					if ("reviewerID".equals(fieldname)){

						// current token is "age",
						// move to next, which is "name"'s value
						jParser.nextToken();
						  reviewId = jParser.getText();
						  System.out.println("reviewId:"+reviewId); // display 29//asin
					  }

					else if ("asin".equals(fieldname)){

						// current token is "age",
						// move to next, which is "name"'s value
						jParser.nextToken();
						  asin = jParser.getText();
						  System.out.println("ASIN:"+asin); // display 29//asin
					  }
					  
					  else if ("reviewText".equals(fieldname) && asin!=null && reviewId!=null ) {	    //&& Arrays.asList(asinList).contains(asin)
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
								rawText = rawText.replaceAll("\\p{Punct}|\\d",""); // removed all punctuation anywhere.
								rawText = rawText.toLowerCase(); // all are in lower case now
								rawText = rawText.replaceAll("\\d+",""); // removes any number anywhere.
								rawText = rawText.trim(); //white space removing
								if(rawText.length()>2 && !Arrays.asList(blackList).contains(rawText)){								
									//writeToFile(fileName+"_out.csv",stringAndMarker[1]+","+rawText);
									if(uniqueKeySet.containsKey(reviewId+","+asin+","+stringAndMarker[1]+","+rawText)){
										uniqueKeySet.put(reviewId+","+asin+","+stringAndMarker[1]+","+rawText, uniqueKeySet.get(reviewId+","+asin+","+stringAndMarker[1]+","+rawText)+1);
									} else {
										uniqueKeySet.put(reviewId+","+asin+","+stringAndMarker[1]+","+rawText, 1);
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
						
						//writeToFile(fileName+"_out.csv",",");

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
