package com.company;

import java.util.Scanner;

public class Main {
    /** a series of print statements demonstrating each function */
    public static void main(String[] args) {
        PhoneWords pw = new PhoneWords(true);
        NewPhoneWords npw = new NewPhoneWords(true);
        Word testWord = new Word("cat");
        System.out.println("getDigit\t" + testWord.getDigit('B'));
        System.out.println("getNumWords\t" + pw.getNumWords());
        System.out.println("isKnown (false), second one will be true \t" + pw.isKnown(testWord));
        pw.addWord("cat");
        System.out.print(pw.isKnown(testWord));
        System.out.println("listWords \n"+ pw.listWords("823"));
        System.out.println("listWords for new code (using hashmap) \t" + npw.listWords("823"));

    }
}
