
//package in.ac.iitb.cfilt.jhwnl.examples;

import in.ac.iitb.cfilt.jhwnl.JHWNL;
import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.data.IndexWord;
import in.ac.iitb.cfilt.jhwnl.data.IndexWordSet;
import in.ac.iitb.cfilt.jhwnl.data.Pointer;
import in.ac.iitb.cfilt.jhwnl.data.PointerType;
import in.ac.iitb.cfilt.jhwnl.data.Synset;
import in.ac.iitb.cfilt.jhwnl.data.POS;
import in.ac.iitb.cfilt.jhwnl.dictionary.Dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.util.Random;
import java.util.ArrayList;
import java.lang.*;

public class Examples {
	
	static void demonstration() {

		BufferedReader inputWordsFile = null;
		FileWriter myWriter = null;
		ArrayList<String> validPOS = new ArrayList();
		// validPOS.add("NOUN");
		validPOS.add("VERB");
		validPOS.add("ADVERB");
		validPOS.add("ADJECTIVE");

		Random rand = new Random();
		try {
			inputWordsFile = new BufferedReader(new InputStreamReader (new FileInputStream ("../hi-en/parallel/IITB.en-hi.100k.hi"), "UTF8"));
			myWriter = new FileWriter("../hi-en/parallel/IITB-SR-aug.en-hi.100k.hi");
		} catch( FileNotFoundException e){
			System.err.println("Error opening input words file.");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			System.err.println("UTF-8 encoding is not supported.");
			System.exit(-1);
		} catch (IOException e){
			System.err.println("IOException in creating output file");
		}
		JHWNL.initialize();
		
		String inputLine;
		long[] synsetOffsets;
		// int counter = 5;
		int lineCount = 0;
		int numWordsLimit = 4;
		try {
			while( ((inputLine = inputWordsFile.readLine()) != null)){
				// System.out.println("\n" + inputLine);
				String[] words = inputLine.split("\\s+");
				//	 Look up the word for all POS tags
				int numWords = words.length;
				if(numWords< numWordsLimit){
					continue;
				}

				myWriter.write(String.valueOf(lineCount)+"::"+inputLine+'\n');
				int numReplacedWords = (int)Math.max(1.0, 0.15*numWords);
				// System.out.println("Number of replaced words: " + String.valueOf(numReplacedWords));
				int[] replaceIndexes = new int[numReplacedWords];
				for(int i=0; i<numReplacedWords; i++){
					replaceIndexes[i] = rand.nextInt(numWords);
				}

				for(int replaceIndex: replaceIndexes){
					String originalWord = words[replaceIndex];
					// System.out.println("Word being replaced: " + originalWord + " from index " + String.valueOf(replaceIndex));
					IndexWordSet demoIWSet = Dictionary.getInstance().lookupAllIndexWords(words[replaceIndex].trim());
					//	 Note: Use lookupAllMorphedIndexWords() to look up morphed form of the input word for all POS tags				
					IndexWord[] demoIndexWord = new IndexWord[demoIWSet.size()];
					demoIndexWord  = demoIWSet.getIndexWordArray();
					for (int i = 0; i < demoIndexWord.length; i++) {
						int size = demoIndexWord[i].getSenseCount();
						// System.out.println("Sense Count is " + size);	

						Synset[] synsetArray = demoIndexWord[i].getSenses(); 
						for ( int k = 0;k < size;k++ ) {
							// System.out.println("Synset [" + k +"] "+ synsetArray[k]);
							if(validPOS.contains(synsetArray[k].getPOS().toString())){
								for(int synsetIndex = 1; synsetIndex <= Math.min(3,synsetArray[k].getWordsSize()); synsetIndex++){
									words[replaceIndex] = synsetArray[k].getWord(synsetIndex).toString();
									StringBuilder builder = new StringBuilder();
									builder.append(lineCount);
									builder.append("::");
									for(String s: words){
										builder.append(s+ ' ');
									}
									String changedLine = builder.toString().trim();
									// System.out.println("Changed line " + changedLine);
									myWriter.write(changedLine+'\n');
								}
							}
							// else{
							// 	System.out.println("Invalid POS");
							// }

							// System.out.println("SynsetPOS [" + k +"] "+ synsetArray[k].getPOS());
						}							
					}
					words[replaceIndex] = originalWord;
				}
				// counter--;
				lineCount++;	
			}
				
		} catch (IOException e) {
			System.err.println("Error in input/output.");			
			e.printStackTrace();
		} catch (JHWNLException e) {
			System.err.println("Internal Error raised from API.");
			e.printStackTrace();
		} 
		finally{
			try{
				myWriter.close();
			} catch (IOException e){
			System.err.println("IOException in creating output file");
			}
		}
	}
	
	public static void main(String args[]) throws Exception {
		demonstration();
	}
}
