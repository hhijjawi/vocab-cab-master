package com.vocab.vocab.ModelData;

import java.io.Serializable;

/**
 * Created by Hisham on 11/29/2015.
 */
public class Word implements Serializable{
    private String mWord;
    private String mDefinition;
    private String mSampleSentence;
    private int mFamiliarityScore;
    public String getWord() {
        return mWord;
    }

    public void setWord(String word) {
        this.mWord = word;
    }

    public String getmDefinition() {
        return mDefinition;
    }

    public void setmDefinition(String mDefinition) {
        this.mDefinition = mDefinition;
    }

    public String getmSampleSentence() {
        return mSampleSentence;
    }

    public void setmSampleSentence(String mSampleSentence) {
        this.mSampleSentence = mSampleSentence;
    }

    public double getmFamiliarityScore() {
        return mFamiliarityScore;
    }

    public void setmFamiliarityScore(int mFamiliarityScore) {
        this.mFamiliarityScore = mFamiliarityScore;
    }


    public Word(String mWord, String mDefinition) {
        this.mWord = mWord;
        this.mDefinition = mDefinition;
        this.mFamiliarityScore = 0;
        this.mSampleSentence = "";
    }
}