package com.vocab.vocab.Visualize;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vocab.vocab.ModelData.Word;
import com.vocab.vocab.R;

import java.util.ArrayList;

/**
 * Created by Hisham on 10/5/2015.
 */
public class WordsListAdapter extends ArrayAdapter {
    ArrayList<Word> words;
    public WordsListAdapter(ArrayList<Word> words, Context context) {
        super(context, 0, words);
        this.words=words;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dictionary_list_item, parent, false);
        }
        TextView word= (TextView) convertView.findViewById(R.id.dictionaryWord);
        word.setText(words.get(position).getWord());
        Log.d("word",words.get(position).getWord());
        return convertView;
    }}
