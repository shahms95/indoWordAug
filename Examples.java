
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Examples {
	
	static void demonstration() {

		BufferedReader inputWordsFile = null;
		try {
			inputWordsFile = new BufferedReader(new InputStreamReader (new FileInputStream ("../hi-en/dev_test/dev.hi"), "UTF8"));
		} catch( FileNotFoundException e){
			System.err.println("Error opening input words file.");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			System.err.println("UTF-8 encoding is not supported.");
			System.exit(-1);
		}
		JHWNL.initialize();
		
		String inputLine;
		long[] synsetOffsets;
		int counter = 10;
		try {
			while(counter > 0 && ((inputLine = inputWordsFile.readLine()) != null)){
				System.out.println("\n" + inputLine);
				String[] words = inputLine.split("\\s+");
				//	 Look up the word for all POS tags
				IndexWordSet demoIWSet = Dictionary.getInstance().lookupAllIndexWords(words[0].trim());				
				//	 Note: Use lookupAllMorphedIndexWords() to look up morphed form of the input word for all POS tags				
				IndexWord[] demoIndexWord = new IndexWord[demoIWSet.size()];
				demoIndexWord  = demoIWSet.getIndexWordArray();
				for ( int i = 0;i < demoIndexWord.length;i++ ) {
					int size = demoIndexWord[i].getSenseCount();
					System.out.println("Sense Count is " + size);	

					Synset[] synsetArray = demoIndexWord[i].getSenses(); 
					for ( int k = 0;k < size;k++ ) {
						System.out.println("Synset [" + k +"] "+ synsetArray[k]);
					}
						
				}
					counter--;
			}
				
		} catch (IOException e) {
			System.err.println("Error in input/output.");			
			e.printStackTrace();
		} catch (JHWNLException e) {
			System.err.println("Internal Error raised from API.");
			e.printStackTrace();
		} 
	}
	
	public static void main(String args[]) throws Exception {
		demonstration();
	}
}
