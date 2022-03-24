package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PhoneWords pw = new PhoneWords(true);
        System.out.println("Enter a number for listWords");
        System.out.println(pw.listWords("222"));
        /*System.out.println("Word.java test");
        Word testWord = new Word(sc.nextLine()); //make new word object and test all functions
        System.out.println(".getWord returns: " + testWord.getWord());
        testWord.setWord("test");
        System.out.println(".getWord returns: " + testWord.getWord());
        testWord.setWordToNumber("test");
        System.out.println("WordtoNumber returns: " + testWord.getWordToNumber());
        System.out.println("To String returns: " + testWord);

        System.out.println("PhoneWords.java test");
        PhoneWords pw = new PhoneWords(true);
        System.out.println("Word to add to phonewords? ");
        pw.addWord(sc.nextLine());
        System.out.println("Enter a number for listWords");
        System.out.println(pw.listWords(sc.nextLine())); */
    }
}
