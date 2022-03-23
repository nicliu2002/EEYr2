package com.company;

import java.util.Locale;

public class Word implements WordInterface {
    private String word;
    private String wordToNumber;
    private final char[] letterArray = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private final char[] value = {'1', '1', '1', '2', '2', '2', '3', '3', '3', '4', '4', '4', '5', '5', '5', '6', '6', '6', '6', '7', '7', '7','8','8','8','9', '9', '9'};

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
        return "to string"; //figure out what im meant to put in here
    }
}