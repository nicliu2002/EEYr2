package com.company;

import java.util.IllformedLocaleException;

/**
 * Given a String this class creates an object to both store the string as well as a corresponding number
 * to that string in order for it to be found.
 * @author Nicholas Liu
 * @version April 2022
 * @see Word
 */
public class Word implements WordInterface {
    /** A string object for the stored word*/
    private String word;
    /** A string object for the stored number equivalent */
    private String wordToNumber;
    /** character array for easy conversion*/
    private final char[] letterArray = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    /** character array for easy conversion*/
    private final char[] value = {'2', '2', '2', '3', '3', '3', '4', '4', '4', '5', '5', '5', '6', '6', '6', '7', '7', '7','7', '8', '8', '8', '9','9','9','9'};

    /**
     * takes a string, makes it all lowercase amd removes spaces, then sets this words attribute for its equivalent number
     * @param word is a String object
     */
    public Word(String word) {
        word = word.toLowerCase();
        word = word.replaceAll("\\s+","");
        this.word = word;
        setWordToNumber(word);
    }
    //javadoc comment moved over from interface
    public void setWord(String word) {
        word = word.toLowerCase();
        word = word.replaceAll("\\s+","");
        this.word = word;
    }
    //javadoc comment moved over from interface
    public String getWord() {
        return word;
    }
    //javadoc comment moved over from interface
    public char getDigit(char letter) throws IllegalArgumentException {
        letter = Character.toLowerCase(letter);
        for (int i = 0; i < letterArray.length; i++) {
            if (letter == letterArray[i]) {
                return value[i];
            } else if (Character.isDigit(letter)) {
                return letter;
            }
        }
        throw new IllegalArgumentException("that is wrong"); //handles weird symbols
    }
    //javadoc comment moved over from interface
    public void setWordToNumber(String word) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < word.length(); i++) {
            result.append(getDigit(word.charAt(i)));
        }
            wordToNumber = result.toString();
    }
    //javadoc comment moved over from interface
    public String getWordToNumber() {
        return wordToNumber;
    }
    //javadoc comment moved over from interface
    public String toString(){
        return word; //returns word to a string for easy use in other functions
    }
}