/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import android.nfc.Tag;
import android.util.Log;

import static android.content.ContentValues.TAG;


public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 9;
    private Random random = new Random();
     ArrayList<String> wordList;
     HashSet<String> wordSet;
     HashMap<Integer, ArrayList<String>> sizeToWords;
     HashMap<String,ArrayList<String>> lettersToWord;
    static int wordLength = DEFAULT_WORD_LENGTH;



    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;

        wordList= new ArrayList<String>();
        wordSet = new HashSet<String>();
        sizeToWords = new HashMap<Integer, ArrayList<String>>();
        lettersToWord = new HashMap<String, ArrayList<String>>();

        while((line = in.readLine()) != null) {
            String word = line.trim();
			wordList.add(word);
			wordSet.add(word);
            String k= sortLetters(word);

            if(wordSet.contains(word))
            {
                if (lettersToWord.containsKey(k)) {
                    if (lettersToWord.get(k).indexOf(word) == -1)
                        lettersToWord.get(k).add(word);
                } else {
                    lettersToWord.put(k, new ArrayList<String>());
                    lettersToWord.get(k).add(word);
                }
            }



            int l = k.length();

            if(sizeToWords.containsKey(l)) {
                sizeToWords.get(l).add(word);
            } else {
                ArrayList<String> t = new ArrayList<String>();
                t.add(word);
                sizeToWords.put(l, t);
            }
        }

        if(wordLength < MAX_WORD_LENGTH)
            wordLength++;
    }

    public String sortLetters( String inputString){
        char tempArray[] = inputString.toCharArray();
        Arrays.sort(tempArray);

        return new String(tempArray);
    }
	


    public boolean isGoodWord(String word, String base) {


        return (wordSet.contains(word) && !word.contains(base));

    }
	

    public ArrayList<String> getAnagrams(String targetWord) {
       ArrayList<String> result = new ArrayList<String>();

		String s = sortLetters(targetWord);




		for(int i=0;i<wordList.size();i++){
		
		String k= wordList.get(i);
		
		String z= sortLetters(k);
		
		boolean r = s.equals(z);
        if(r)
          result.add(k);
		}

	
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();

        for(int i='a';i<='z';i++)
        {
           /* k=sortLetters(word.concat(" "+i));

            if(lettersToWord.containsKey(k))
            {
                result.addAll(lettersToWord.get(k));
            }*/

            String k = word;
            k = k + Character.toString((char)i);

            ArrayList<String> anag = getAnagrams(k);
            for(String str : anag)
                if(isGoodWord(str, word))
                    result.add(str);

        }
        return result;
    }

    public String pickGoodStarterWord() {

       /* while (true) {
            int num = random.nextInt(wordList.size());
            String word = (String) wordList.get(num);
            ArrayList res = (ArrayList) lettersToWord.get(sortLetters(word));
            // A good word's length should be greater than 7 && must have atleast 5 anagrams
            if (word.length() <= MAX_WORD_LENGTH && res.size() >= MIN_NUM_ANAGRAMS)
                return (String) wordList.get(num);*/

            String ret;
            while(true){
                int index = random.nextInt(sizeToWords.get(wordLength).size());
                ret = sizeToWords.get(wordLength).get(index);
                if(getAnagramsWithOneMoreLetter(ret).size() >= MIN_NUM_ANAGRAMS)
                    break;
            }

            return ret;


            //return "stop";
        }

}
