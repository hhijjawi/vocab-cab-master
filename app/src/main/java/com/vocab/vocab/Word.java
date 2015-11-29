package com.vocab.vocab;

/**
 * Created by Hisham on 11/29/2015.
 */
public class Word {
    private String word;
    private String definition;
    private String sampleSentence;
    private double familiarityScore;

    public Word(String word, String definition) {
        this.word = word;
        this.definition = definition;
        this.familiarityScore = 0;
        this.sampleSentence = "";
    }
}
