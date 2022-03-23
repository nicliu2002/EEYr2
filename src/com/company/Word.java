package com.company;

public class Word implements WordInterface {
    private String word;
    private String wordToNumber;

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public char getDigit(char letter) {
        char[] letterArray = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char[] value = {'1', '1', '1', '2', '2', '2', '3', '3', '3', '4', '4', '4', '5', '5', '5', '6', '6', '6', '6', '7', '7', '7','8','8','8','9', '9', '9'};

        for(int i = 0; i < letterArray.length; i++) {
            if(letter == letterArray[i])
                return value[i];
        }
        return '0';   // <--- shouldn't happen
    }

    public void setWordToNumber(String word) {


    }

    public String getWordToNumber() {
        return null;
    }

    public void setWord(String word) {
        this.word = word;
    }



}