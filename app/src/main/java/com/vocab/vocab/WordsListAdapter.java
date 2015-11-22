package com.vocab.vocab;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hisham on 10/5/2015.
 */
public class WordsListAdapter extends ArrayAdapter {
    ArrayList<String> words;
    public WordsListAdapter(ArrayList<String> words, Context context) {
        super(context, 0, words);
        this.words=words;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dictionary_list_item, parent, false);
        }
        TextView word= (TextView) convertView.findViewById(R.id.dictionaryWord);
        word.setText(words.get(position));
        Log.d("word",words.get(position));
        return convertView;
    }}
