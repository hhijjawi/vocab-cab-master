package com.vocab.vocab.ModelData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hisham on 11/29/2015.
 */
public class WordListSingleton implements Serializable{
    private static WordListSingleton ourInstance = new WordListSingleton();
    private ArrayList<Word> wordList;
    public static WordListSingleton getInstance() {
        return ourInstance;
    }

    public ArrayList<Word> getWordList() {
        return wordList;
    }

    private WordListSingleton() {

        this.wordList = new ArrayList<Word>();
    }


    public void setWordList(ArrayList<Word> wordList) {
        this.wordList = wordList;
    }
}