package com.vocab.vocab.Visualize;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.vocab.vocab.AsyncTasks.FetchImageAsyncTask;
import com.vocab.vocab.ModelData.WordListSingleton;
import com.vocab.vocab.R;
import com.vocab.vocab.Utilities.Utils;


/**
 * Created by Hisham on 10/5/2015.
 */
public class DefinitionActivity extends AppCompatActivity {
    Button nextButton;
    ImageView mImageView;
    NumberPicker familiarityPicker;
    int position;
    int timesRun=0;
    final int NEITHERPRESSED=0;
    final int BACKBUTTONPRESSED=1;
    final int FORWARDBUTTONPRESSED=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition_layout);
        if(timesRun==0){
            timesRun++;
           setPosition(getIntent().getIntExtra("Position", 1));
            Log.d("Position set to ", ""+position+" at line 38");
            loadViewAt(position,NEITHERPRESSED);
        }


    }

    public void loadViewAt(int position,int whereCalled) {
    if(Utils.randInt(1,4)<(int)WordListSingleton.getInstance().getWordList().get(position).getFamiliarityScore()){
        switch(whereCalled) {
            case NEITHERPRESSED:
                break;
            case BACKBUTTONPRESSED:
                if(getPosition()==0){
                    Log.d("Loaded Position set to", "" + (WordListSingleton.getInstance().getWordList().size() - 1));
                    setPosition(WordListSingleton.getInstance().getWordList().size() - 1);
                    loadViewAt(getPosition(), BACKBUTTONPRESSED);
                    return;
                }
                setPosition((getPosition()-1));
                loadViewAt(getPosition(), BACKBUTTONPRESSED);

                Log.d("Loaded view at position","Loaded view at position "+ position+ " at line 53");

return;

            case FORWARDBUTTONPRESSED:
                if(getPosition()==(WordListSingleton.getInstance().getWordList().size() - 1)){
                    Log.d("Loaded Position set to", "" + 0);
                    setPosition(0);
                    loadViewAt(getPosition(), FORWARDBUTTONPRESSED);
                    return;
                }
setPosition(getPosition()+1);                    loadViewAt(getPosition(), FORWARDBUTTONPRESSED);
                Log.d("Loaded view at position", "Loaded view at position " + position + " at line 58");
return;

        }
    }
        final RadioGroup ratingsGroup= (RadioGroup)findViewById(R.id.rGroup);
        Log.d("famScore",""+WordListSingleton.getInstance().getWordList().get(position).getFamiliarityScore());

        switch((int)WordListSingleton.getInstance().getWordList().get(position).getFamiliarityScore()){
            case 0:ratingsGroup.clearCheck();

        }

        TextView definitionTextView = (TextView) findViewById(R.id.dText);
        TextView wordTextView = (TextView) findViewById(R.id.titleTextView);
        String definition = WordListSingleton.getInstance().getWordList().get(position).getDefinition();
        String word = WordListSingleton.getInstance().getWordList().get(position).getWord();
        definitionTextView.setText(definition);
        wordTextView.setText(word);
        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPosition() == WordListSingleton.getInstance().getWordList().size() - 1) {
                    setPosition(0);
                    ratingsGroup.clearCheck();
                    loadViewAt(getPosition(), FORWARDBUTTONPRESSED);

                    Log.d("Loaded view at position", "Loaded view at position " + getPosition() + " at line 86");
                    Log.d("Position set to ", "" + getPosition() + " at line 69");

                } else {
                    ratingsGroup.clearCheck();

                    setPosition(getPosition() + 1);
                    Log.d("Position set to ", "" + getPosition() + " at line 74");

                    loadViewAt(getPosition(), FORWARDBUTTONPRESSED);

                    Log.d("Loaded view at position", "Loaded view at position " + getPosition() + " at line 93");
                }
            }
        });
        mImageView = (ImageView) findViewById(R.id.imageView);


        Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPosition() != 0) {
                    ratingsGroup.clearCheck();

                    setPosition(getPosition()-1);
                  loadViewAt(getPosition(),BACKBUTTONPRESSED);
                    Log.d("Loaded view at position", "Loaded view at position " + getPosition() + " at line 108");


                } else {
                    ratingsGroup.clearCheck();
                    setPosition(WordListSingleton.getInstance().getWordList().size()-1);
                    Log.d("Position set to ", ""+getPosition()+" at line 113");

                    loadViewAt(getPosition(), BACKBUTTONPRESSED);
                }
            }

        });
        if (Utils.isNetworkAvailable(this)) {
            FetchImageAsyncTask getImageIntoImageView = new FetchImageAsyncTask(word, this, this);
            getImageIntoImageView.execute();
        }


        if(ratingsGroup.getCheckedRadioButtonId()!=WordListSingleton.getInstance().getWordList().get(position).getFamiliarityScore()){
        ratingsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("OnCheckChangedCalled", "OnCheckChangedCalled");
                switch (checkedId) {
                    case 0:
                        break;
                    case R.id.radioButton1:
                        WordListSingleton.getInstance().getWordList().get(getPosition()).setFamiliarityScore(1);
                        Log.d("Loaded famscore for", "Loaded famscore for " + WordListSingleton.getInstance().getWordList().get(getPosition()).getWord() + "to 1");


                        break;
                    case R.id.radioButton2:
                        WordListSingleton.getInstance().getWordList().get(getPosition()).setFamiliarityScore(2);
                        Log.d("Loaded famscore for", "Loaded famscore for " + WordListSingleton.getInstance().getWordList().get(getPosition()).getWord() + "to 2");



                        break;
                    case R.id.radioButton3:
                        WordListSingleton.getInstance().getWordList().get(getPosition()).setFamiliarityScore(3);
                        Log.d("Loaded famscore for", "Loaded famscore for " + WordListSingleton.getInstance().getWordList().get(getPosition()).getWord() + "to 3");
                        ratingsGroup.clearCheck();


                        break;
                    case R.id.radioButton4:
                        WordListSingleton.getInstance().getWordList().get(getPosition()).setFamiliarityScore(4);
                        Log.d("Set famscore for", "Loaded famscore for " + WordListSingleton.getInstance().getWordList().get(getPosition()).getWord() + "to 4");


                        break;
                    case R.id.radioButton5:
                        WordListSingleton.getInstance().getWordList().get(getPosition()).setFamiliarityScore(5);
                        Log.d("Loaded famscore for", "Loaded famscore for " + getPosition() + " to 5");


                        break;

                }
            }
        });

    }}

    public void setImageView(String URL) {
        Ion.with(this).load(URL).intoImageView(mImageView);
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

}
