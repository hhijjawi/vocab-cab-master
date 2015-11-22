package com.vocab.vocab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

/**
 * Created by Hisham on 10/5/2015.
 */
public  class DefinitionActivity extends AppCompatActivity{
    ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition_layout);
        TextView definitionTextView= (TextView) findViewById(R.id.dText);
        TextView wordTextView=(TextView) findViewById(R.id.titleTextView);
        int position=getIntent().getIntExtra("Position",1);
        String definition=getWordsAndDefinitionsTask.getDefinitionsList().get(position);
        String word=getWordsAndDefinitionsTask.getWordsList().get(position);
        definitionTextView.setText(definition);
        wordTextView.setText(word);
         mImageView= (ImageView)findViewById(R.id.imageView);

        FetchImageAsyncTask getWordJson=new FetchImageAsyncTask(word,this, this);
        getWordJson.execute();
    }
    public void setImageView(String URL){
   Ion.with(this).load(URL).intoImageView(mImageView);

    }

}
