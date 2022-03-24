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
    private List<Word> wordList = new ArrayList<>();
    private List<String> wordDict = new ArrayList<>();
    private boolean numSorted;
    private int numWords;
    private int numIndex = 0;

    static final String[] codes
            = { " "," ", "abc", "def",
            "ghi", "jkl", "mno",
            "pqr", "stu", "vwx",
            "yz" };

    public void addWord(String w) {
        Word tempWord = new Word(w);
        if (!isKnown(tempWord)){
            wordList.add(tempWord);
            wordDict.add(w);
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
        List<String> matchedList = new ArrayList<>();
        if (num.length() == 0) {
            ArrayList<String> baseRes = new ArrayList<>();
            baseRes.add("");
            // Return an Arraylist containing
            // empty string
            return baseRes;
        }
        // First character of num
        char ch = num.charAt(0);
        // Rest of the characters of num
        String restOfString = num.substring(1);
        List<String> prevRes = listWords(restOfString);
        List<String> Res = new ArrayList<>();
        String code = codes[ch - '0'];
        for (String val : prevRes) {
            for (int i = 0; i < code.length(); i++) {
                Res.add(code.charAt(i) + val);
            }
        }
        return Res;
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
