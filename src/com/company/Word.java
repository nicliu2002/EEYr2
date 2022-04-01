package com.company;

import java.util.Locale;

public class Word implements WordInterface {
    private String word;
    private String wordToNumber;
    private final char[] letterArray = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private final char[] value = {'2', '2', '2', '3', '3', '3', '4', '4', '4', '5', '5', '5', '6', '6', '6', '7', '7', '7','7', '8', '8', '8', '9','9','9','9'};

    public Word(String word) {
        word = word.toLowerCase();
        word = word.replaceAll("\\s+","");
        this.word = word;
    }

    public void setWord(String word) {
        word = word.toLowerCase();
        word = word.replaceAll("\\s+","");
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public char getDigit(char letter) {
        for(int i = 0; i < letterArray.length; i++) {
            if(letter == letterArray[i])
                return value[i];
        }
        return '0'; // should not happen
    }

    public void setWordToNumber(String word) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < word.length(); i++) {
            result.append(getDigit(word.charAt(i)));
        }
            wordToNumber = result.toString();
    }

    public String getWordToNumber() {
        return wordToNumber;
    }
    public String toString(){
        return word; //returns word to a string for easy use in other functions
    }
}