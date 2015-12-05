package com.vocab.vocab.ModelData;

/**
 * Created by Hisham on 11/29/2015.
 */
public class Word {
    private String word;
    private String definition;
    private String sampleSentence;
    private int familiarityScore;
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getSampleSentence() {
        return sampleSentence;
    }

    public void setSampleSentence(String sampleSentence) {
        this.sampleSentence = sampleSentence;
    }

    public double getFamiliarityScore() {
        return familiarityScore;
    }

    public void setFamiliarityScore(int familiarityScore) {
        this.familiarityScore = familiarityScore;
    }


    public Word(String word, String definition) {
        this.word = word;
        this.definition = definition;
        this.familiarityScore = 0;
        this.sampleSentence = "";
    }
}