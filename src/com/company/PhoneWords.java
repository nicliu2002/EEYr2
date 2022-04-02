package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;


/**
 * Given a collection of words, you can convert any word to its equivalent phone
 * number, or phone number into a list of possible valid words. It accepts
 * either reading words from words.txt and/or adding new words
 *
 * @author Nicholas Liu
 * @version April 2022
 * @see Word
 */
public class PhoneWords implements PhoneWordsInterface{
    /** provided as part of document*/
    private boolean numSorted;
    /** provided as part of document*/
    private boolean numUniqueSorted;
    /** a list of Word objects for adding to and referencing back to*/
    private List<Word> wordList = new ArrayList<>();
    /** a list of String objects for adding to and referencing back to*/
    private List<String> wordDict = new ArrayList<>();
    /** number of words added */
    private int numWords;
    /**provides an intialisation switch for listallwords */
    private boolean firstTime = true; //solves backtracking issues with checknum
    /**array of String objects to act as a numpad*/
    static final String[] codes //numpad for listAllWords to seek
            = {"","", "abc", "def",
            "ghi", "jkl", "mno",
            "pqrs", "tuv", "wxyz"};

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
        } catch (IOException e) { //this is in the code but the exception caused by this is already caught
            System.err.println("Error reading words file: " + e);
            System.exit(100);
        }
    }

    //javadoc comment moved over from interface
    public void addWord(String w) {
        Word tempWord = new Word(w);
        if (!isKnown(tempWord)){
            wordList.add(tempWord);
            wordDict.add(w);
            numWords++;
        }
    }

    //javadoc comment moved over from interface
    public boolean isKnown(Word word) {
        return wordList.contains(word);
    }

    //javadoc comment moved over from interface
    public int getNumWords() {
        return numWords; //returns number of words in collection
    }

    public void setNumSorted(boolean numSortedStatus){
        numSorted = numSortedStatus;
    }

    public boolean isNumSorted() {
        return numSorted;
    }
    /**
     * manipulates a string so that fulfills the requirements for a number or an exception if thats not the case,
     * also sorts this string into ascending order using sortNum
     *
     * @param rawNum a String object
     * @return a String object
     * @throws IllegalArgumentException an exception that gets thrown when the wrong argument is forced in
     */
    public String checkNum(String rawNum) throws IllegalArgumentException {
        String result = rawNum.replaceAll("\\s+", ""); //removing spaces first makes the rejex statement a bit smaller
        Pattern ptrn = Pattern.compile("^(\\(\\d+\\)|\\(\\+\\d*\\)|\\+|\\d)\\d*$"); //regex pattern more efficient then a bunch of if statements
        Matcher match = ptrn.matcher(result);
        if (match.find() && match.group().equals(result)) { //if statement, if number fits within regex constraints will run
            result = result.replace("(", "");
            result = result.replace(")", "");
            result = result.replace("+", "00");
            result = sortNum(result); //doing sortNum here is optimal as the above statements have ensured that the output is a
            numSorted = true;
        } else {
            throw new IllegalArgumentException("not a valid number");
        }
        return result; //returns the "adjusted number" (no spaces, actual phone number, no brackets)
    }
    /**
     * uses backtracking to iterate through all possible combinations of numbers and compile it into a list that
     * could possibly result from String number
     *
     * @param number a String object
     * @return a java List of String objects
     * @throws IllegalArgumentException an exception that gets thrown when the wrong argument is forced in
     */
    public List<String> listAllWords(String number) throws IllegalArgumentException{
        String goodNum;
        if (firstTime) { //first time startup features as the function calls itself to iterate through any size of number
            goodNum = checkNum(number);
            firstTime = false;
            if (!numSorted){ //throws error if number is not sorted
                throw new IllegalArgumentException("number is not sorted");
            }
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
        return Res;
    }

    public List<String> listWords(String num) { //actual listWords function that takes listAllWords output to avoid an issue with backtracking
        List<String> unSorted = listAllWords(num);
        List<String> result = new ArrayList<>();
        for (String i : unSorted){
            if (wordDict.contains(i)){ //only outputs words from words.txt or added words
                result.add(i);
            }
        }
        firstTime = true;
        return result;
    }

    public String sortNum(String num) {
        char[] sortArray = num.toCharArray();
        int [] numberArray = new int[num.length()];
        for(int i = 0; i<sortArray.length; i++) {
            numberArray[i] = Integer.parseInt(String.valueOf(sortArray[i]));//using streams here cuts down on loops and is a lot more concise
        }
        Arrays.sort(numberArray);
        numSorted = true;
        return (Arrays.toString(numberArray).replaceAll("\\[|\\]|,|\\s", "")); //regex to delete all the other "bits" of the array.toString
    }
}
