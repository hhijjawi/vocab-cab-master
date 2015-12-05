package com.vocab.vocab.Visualize;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;

import com.vocab.vocab.ModelData.Word;
import com.vocab.vocab.ModelData.WordListSingleton;
import com.vocab.vocab.R;

import java.util.ArrayList;


/**
 * Created by Hisham on 10/5/2015.
 */
public class WordListActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Word> words = WordListSingleton.getInstance().getWordList();
        WordsListAdapter dictionaryListAdapter = new WordsListAdapter(words, this);
        setListAdapter(dictionaryListAdapter);
        setContentView(R.layout.activity_word_list);
        float friction = (float) (ViewConfiguration.getScrollFriction() * .6);
        getListView().setFriction(friction);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(WordListActivity.this, DefinitionActivity.class);
        i.putExtra("Position", position);
        startActivity(i);
    }
}
