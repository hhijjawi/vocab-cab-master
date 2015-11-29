package com.vocab.vocab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

/**
 * Created by Hisham on 10/5/2015.
 */
public class DefinitionActivity extends AppCompatActivity {
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition_layout);
        wireUpViews();
    }

    public void wireUpViews() {
        TextView definitionTextView = (TextView) findViewById(R.id.dText);
        TextView wordTextView = (TextView) findViewById(R.id.titleTextView);
        int position = getIntent().getIntExtra("Position", 1);
        String definition = DefinitionsSingelton.getInstance().getDefinitionsList().get(position);

        String word = DefinitionsSingelton.getInstance().getWordsList().get(position);
        definitionTextView.setText(definition);
        wordTextView.setText(word);
        mImageView = (ImageView) findViewById(R.id.imageView);
        if (Utils.isNetworkAvailable(this)) {
            FetchImageAsyncTask getImageIntoImageView = new FetchImageAsyncTask(word, this, this);
            getImageIntoImageView.execute();
        }
    }

    public void setImageView(String URL) {
        Ion.with(this).load(URL).intoImageView(mImageView);
    }

}
