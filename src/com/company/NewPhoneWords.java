package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;
import java.util.HashMap;


/**
 * Given a collection of words, you can convert any word to its equivalent phone
 * number, or phone number into a list of possible valid words. It accepts
 * either reading words from words.txt and/or adding new words
 *
 * @author Nicholas Liu
 * @version April 2022
 * @see Word
 */

public class NewPhoneWords implements PhoneWordsInterface{
    /**a Hashmap links Word objects to its "phone number" String as the key*/
    private Map<String, List<Word>> wordList = new HashMap<String, List<Word>>();
    /**number of word objects in wordList hashmap*/
    int NumWords;
    /** provided as part of document*/
    private boolean numSorted;
    /** provided as part of document*/
    private boolean numUniqueSorted;

    /**
     * construct a new phoneWord object
     *
     * @param isWordsFileProvided yes, if we need to read words from words.txt,
     * otherwise no
     */

    public NewPhoneWords(boolean isWordsFileProvided) {
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
    private static void loadDict(String name, NewPhoneWords pw) {
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
        Word newWord = new Word(w);
        List<Word> tempList = new ArrayList<>();
        tempList.add(newWord);
        if (!isKnown(newWord)){
            if (wordList.containsKey(newWord.getWordToNumber())) {
                wordList.get(newWord.getWordToNumber()).add(newWord);
            } else {
                wordList.put(newWord.getWordToNumber(), tempList);
                NumWords++;
            }
        }
    }

    //javadoc comment moved over from interface
    public boolean isKnown(Word word) {
        return wordList.containsValue(word);
    }
    //javadoc comment moved over from interface
    public int getNumWords() {
        return wordList.size();
    }
    //javadoc comment moved over from interface
    public void setNumSorted(boolean numSortedStatus){
        numSorted = numSortedStatus;
    }
    //javadoc comment moved over from interface
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
        } else {
            throw new IllegalArgumentException("not a valid number");
        }
        return result; //returns the "adjusted number" (no spaces, actual phone number, no brackets)
    }
    //javadoc comment moved over from interface
    public List<String> listWords(String num) throws IllegalArgumentException {
        String goodNum = checkNum(num);
        if (!numSorted){
            throw new IllegalArgumentException("number is not sorted"); //handles any possible situation where number is not sorted before listwords
        }
        List<String> result = new ArrayList<>();
        for (String i : wordList.keySet()){
            if (Objects.equals(goodNum, i)){
                Spliterator<Word> words = wordList.get(i).spliterator();
                words.forEachRemaining((n) -> result.add(n.toString()));
            }
        }
        return result;
    }
    //javadoc comment moved over from interface
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
