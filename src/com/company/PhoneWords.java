package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;


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
    private boolean firstTime = true; //solves backtracking issues with checknum
    private String goodNum;
    private int functionLength;
    private List<String> unsortedWordList = new ArrayList<>();
    static final String[] codes //numpad for listAllWords to seek
            = {"","", "abc", "def",
            "ghi", "jkl", "mno",
            "pqrs", "tuv", "wxyz"};

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

    public void setNumSorted(boolean numSortedStatus){
        numSorted = numSortedStatus;
    }

    public boolean isNumSorted() {
        return numSorted;
    }
    public String checkNum(String rawNum) throws IllegalArgumentException {
        boolean isNumber;
        Pattern ptrn = Pattern.compile("^(\\+?\\(61\\)|\\(\\+?61\\)|\\+?61|\\(0[1-9]\\)|0[1-9])?( ?-?[0-9]){7,9}$"); //regex pattern for all Australian numbers
        Matcher match = ptrn.matcher(rawNum);
        if (match.find() && match.group().equals(rawNum)) { //if statement, if number fits within regex constraints will run
            rawNum = rawNum.replaceAll("\\s", "");
            rawNum = rawNum.replace("(", "");
            rawNum = rawNum.replace(")", "");
            rawNum = rawNum.replace("+", "00");
        } else {
            throw new IllegalArgumentException("not a valid number");
        }
        return rawNum; //returns the "adjusted number" (no spaces, actual phone number, no brackets)
    }
    public List<String> listAllWords(String number) {
        if (firstTime) {
            goodNum = checkNum(number);
            functionLength = number.length();
            firstTime = false;
        } else {
            goodNum = number;
        }
        assert goodNum != null; //solves null pointer exception
        if (goodNum.length() == 0) {
            ArrayList<String> baseRes = new ArrayList<>();
            baseRes.add("");
            return baseRes; //if there's nothing there returns an empty list (error handling)
        }
        // First character of num
        char ch = goodNum.charAt(0);
        // Rest of the characters of num
        String restOfString = goodNum.substring(1);
        List<String> prevRes = listAllWords(restOfString); //backtracking to handle n length of number
        List<String> Res = new ArrayList<>();
        String code = codes[ch - '0'];
        for (String val : prevRes) {
            for (int i = 0; i < code.length(); i++) {
                Res.add(code.charAt(i) + val);
            }
        }
        unsortedWordList.addAll(Res);
        functionLength--; //uses this to add to the list
        if (functionLength == 0){
            return unsortedWordList;
        }
        return Res;
    }
    public List<String> listWords(String num) { //actual listWords function that takes listAllWords output to avoid an issue with backtracking
        List<String> unSorted = listAllWords(num);
        List<String> result = new ArrayList<>();
        for (String i : unSorted){
            if (wordDict.contains(i)){ //only outputs words from words.txt or added words
                result.add(i);
                numWords++;
            }
        }
        firstTime = true;
        return result;
    }

    public String sortNum(String num) {
        String result = num;
        return result;
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
        } catch (IOException e) { //this is in the code but the exception caused by this is already caught?
            System.err.println("Error reading words file: " + e);
            System.exit(100);
        }
    }

}
