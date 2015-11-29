package com.vocab.vocab;

import java.util.ArrayList;

/**
 * Created by Hisham on 11/23/2015.
 */
public class DefinitionsSingelton {
    ArrayList<String> wordsList = new ArrayList<String>();
    ArrayList<String> definitionsList = new ArrayList<String>();
    ArrayList<ArrayList<String>> wordsAndDefs = new ArrayList<ArrayList<String>>();
    private static DefinitionsSingelton ourInstance = new DefinitionsSingelton();



    public static DefinitionsSingelton getInstance() {
        return ourInstance;
    }

    private DefinitionsSingelton() {

    }
    public ArrayList<String> getWordsList() {
        return wordsList;
    }

    public void setWordsList(ArrayList<String> wordsList) {
        this.wordsList = wordsList;
    }

    public ArrayList<String> getDefinitionsList() {
        return definitionsList;
    }

    public void setDefinitionsList(ArrayList<String> definitionsList) {
        this.definitionsList = definitionsList;
    }

    public ArrayList<ArrayList<String>> getWordsAndDefs() {
        return wordsAndDefs;
    }

    public void setWordsAndDefs(ArrayList<ArrayList<String>> wordsAndDefs) {
        this.wordsAndDefs = wordsAndDefs;
    }
}
