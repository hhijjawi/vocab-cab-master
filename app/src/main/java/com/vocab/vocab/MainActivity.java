package com.vocab.vocab;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.vocab.vocab.AsyncTasks.GetWordsAndDefinitionsAsyncTask;
import com.vocab.vocab.ModelData.WordListSingleton;
import com.vocab.vocab.Utilities.Utils;
import com.vocab.vocab.Verify.VerifyActivity;
import com.vocab.vocab.Visualize.WordListActivity;
import com.vocab.vocab.Vocalize.MusicPlayerActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private int numRuns=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wireUpLayout();
        loadDefinitionsAndAudioFiles(this);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share_progress) {
            Utils.calculateProgress(this);
            return super.onOptionsItemSelected(item);
        }return true;}

    public void wireUpLayout() {
        Button vocalizeButton = (Button) findViewById(R.id.VocalizeButton);
        Button visualizeButton = (Button) findViewById(R.id.VisualizeButton);
        Button verifyButton = (Button) findViewById(R.id.VerifyButton);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "pacifico.ttf");
        vocalizeButton.setTypeface(typeface);
        visualizeButton.setTypeface(typeface);
        verifyButton.setTypeface(typeface);
        visualizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openDictionary = new Intent(MainActivity.this, WordListActivity.class);
                startActivity(openDictionary);
            }
        });
        vocalizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MusicPlayerActivity.class);
                startActivity(i);
            }
        });
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, VerifyActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onPause(){
        super.onPause();
        //If we have loaded the definitions, ratings, and audio, save them so we don't waste time doing it again.
        try {
            Utils.saveObject(WordListSingleton.getInstance(),this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method that gets our definitions into a Singleton. If it's the first time running the app, then Parse it from JSON. Otherwise, get it from a persitent file on storage to save time.
    public static void loadDefinitionsAndAudioFiles(final Activity mCallingActivity){
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = mCallingActivity.getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean("my_first_time", true)) {

                        new GetWordsAndDefinitionsAsyncTask(mCallingActivity, true).execute();

            settings.edit().putBoolean("my_first_time", false).commit();
        }
        else if(WordListSingleton.getInstance().getWordList().isEmpty()){
            Log.d("truefalse","false");
            try {
                WordListSingleton.setOurInstance(Utils.loadWList(mCallingActivity));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
