package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Given a collection of words, you can convert any word to its equivalent phone
 * number, or phone number into a list of possible valid words. It accepts
 * either reading words from words.txt and/or adding new words
 *
 * @author Saber Elsayed
 * @version Jan 2022
 * @see Word
 */
public class PhoneWords implements PhoneWordsInterface{
    private boolean numUniqueSorted;
    private List<Word> wordList = new ArrayList<Word>();
    private boolean numSorted;
    private int NumWords;
    static Character[][] numberToCharMap;
    private int numIndex = 0;

    private static void generateNumberToCharMap()
    {
        numberToCharMap = new Character[10][5];
        numberToCharMap[0] = new Character[]{'\0'};
        numberToCharMap[1] = new Character[]{'\0'};
        numberToCharMap[2] = new Character[]{'a','b','c'};
        numberToCharMap[3] = new Character[]{'d','e','f'};
        numberToCharMap[4] = new Character[]{'g','h','i'};
        numberToCharMap[5] = new Character[]{'j','k','l'};
        numberToCharMap[6] = new Character[]{'m','n','o'};
        numberToCharMap[7] = new Character[]{'p','q','r','s'};
        numberToCharMap[8] = new Character[]{'t','u','v'};
        numberToCharMap[9] = new Character[]{'w','x','y','z'};
    }

    public void addWord(String w) {
        Word tempWord = new Word(w);
        if (!isKnown(tempWord)){
            wordList.add(tempWord);
        }
    }

    public boolean isKnown(Word word) {
        return wordList.contains(word);
    }

    public int getNumWords() {
        return wordList.size();
    }

    public void setNumSorted(boolean numSortedStatus) {

    }

    public boolean isNumSorted() {
        return false;
    }

    public List<String> listWords(String num) {
        List<String> result = new ArrayList<String>();
        int[] numbers = new int[num.length()];
        generateNumberToCharMap()
        for(int i=0; i<num.length(); i++) {
            numbers[i] = Character.getNumericValue(num.charAt(i));
        } //up to here converts num into an integer array

        for (int i : numbers){

        }

        return result;
    }

    public String sortNum(String num) {
        return null;
    }

    /**
     * construct a new phoneWord object
     *
     * @param isWordsFileProvided yes, if we need to read words from words.txt,
     * otherwise no
     */

    public PhoneWords(boolean isWordsFileProvided) {
        numSorted = false;
        numUniqueSorted=false;
        if (isWordsFileProvided) {
            loadDict("words.txt", this);
        }
    }

    /**
     * read and process all the words in the words file and add them to the
     * PhoneWords object
     *
     * @param name of the file being indexed
     * @param pw PhoneWords object to add words to
     */
    private static void loadDict(String name, PhoneWords pw) {
        Scanner dictIn; // words file scanner
        // open named words file, then read & add words to pw
        try {
            dictIn = new Scanner(new File(name));
            while (dictIn.hasNextLine()) {
                String word = dictIn.nextLine();
                // add next word from words file, catching any errors
                try {
                    pw.addWord(word);
                } catch (IllegalArgumentException iae) {
                    System.out.println("addWord failed: " + iae.getMessage());
                }
            }
        } // handle file I/O exceptions
        catch (FileNotFoundException fnfe) {
            System.err.println("Error opening words file: " + fnfe);
            System.exit(100);
        } catch (IOException e) {
            System.err.println("Error reading words file: " + e);
            System.exit(100);
        }
    }

}
