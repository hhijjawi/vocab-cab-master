package com.vocab.vocab.Verify;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import com.vocab.vocab.ModelData.WordListSingleton;
import com.vocab.vocab.R;
import com.vocab.vocab.Utilities.Utils;

/**
 * Created by Hisham on 11/30/2015.
 */
public class VerifyActivity extends Activity {
    public Button definitionOne;
    public Button definitionTwo;
    public Button definitionThree;
    public Button titleButton;
    int wordIndex=0;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }
    public void wireUpLayout(){
        titleButton=(Button)findViewById(R.id.wordTitle);
        definitionOne= (Button)findViewById(R.id.definition1);
        definitionTwo= (Button)findViewById(R.id.definition2);
        definitionThree= (Button)findViewById(R.id.definition3);
        definitionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        definitionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        definitionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
    public void updateDefintionsAndUI(){
        if(Utils.randInt(0,5)> WordListSingleton.getInstance().getWordList().get(wordIndex).getFamiliarityScore()){
            //Skip familiar words more often
            wordIndex++;
            updateDefintionsAndUI();
        }
        else{

        }
    }


}
