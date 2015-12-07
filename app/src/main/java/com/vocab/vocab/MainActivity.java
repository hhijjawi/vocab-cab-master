package com.vocab.vocab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.vocab.vocab.AsyncTasks.GetWordsAndDefinitionsAsyncTask;
import com.vocab.vocab.ModelData.Word;
import com.vocab.vocab.ModelData.WordListSingleton;
import com.vocab.vocab.Verify.VerifyActivity;
import com.vocab.vocab.Visualize.WordListActivity;
import com.vocab.vocab.Vocalize.MusicPlayerActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech tts;
    private int numRuns=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wireUpLayout();

/*
        try {
            if(loadWList()!=null){
    WordListSingleton.getInstance().setWordList(loadWList());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            Log.d("First","First");

            tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        Log.d("Init", "Init");
                        new GetWordsAndDefinitionsAsyncTask(MainActivity.this,tts,true).execute();

                    }

                }

            });

            settings.edit().putBoolean("my_first_time", false).commit();
        }
        else if(WordListSingleton.getInstance().getWordList().isEmpty()){
            Log.d("truefalse","false");

            tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        Log.d("Init", "Init");
                        new GetWordsAndDefinitionsAsyncTask(MainActivity.this, tts, false).execute();

                    }

                }

            });
        }
//        testTTS();

       // getMP3();

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
            calculateProgress();
            return super.onOptionsItemSelected(item);
        }return true;}
    public void calculateProgress(){
        ArrayList<Word> words=WordListSingleton.getInstance().getWordList();
        double runningSum=0;
        double optimalSum=0;
        for(Word word: words){
         runningSum=runningSum+   word.getmFamiliarityScore();
            optimalSum=optimalSum+5;
        }
        double percentLearned=runningSum/optimalSum;
        String shareBody = "I have learned "+ percentLearned+"% of words that show up on the GRE.";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My GRE Progress");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }
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

    public void testTTS() {
         tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //I like the british accent and the cadence kind of slow
                    tts.setLanguage(Locale.UK);
                    tts.setSpeechRate((float) 0.60);
                    tts.speak("hello i like cheese", TextToSpeech.QUEUE_ADD, null);
                    HashMap<String, String> myHashRender = new HashMap();
                    String wakeUpText = "Is this working?";
                    String destFileName = "/wakeUp.wav";
                    myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, wakeUpText);
                    String exStoragePath                = getFilesDir().getAbsolutePath();
                    Log.d("ex",exStoragePath);
                    File appTmpPath                     = new File(exStoragePath);
                    appTmpPath.mkdirs();
                    String tempFilename                 = "tmpaudio.wav";
                    String tempDestFile                 = appTmpPath.getAbsolutePath() + "/" + tempFilename;
                    tts.synthesizeToFile(wakeUpText, myHashRender, tempDestFile);

                }
            }
         }
        );
        Log.d("here", "here");

    }


    public void saveObject(Serializable ser) throws IOException {
        FileOutputStream fos = this.openFileOutput("WordListSingleton", Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(ser);
        os.close();
        fos.close();
    }
    /*
    public WordList loadWList() throws IOException, ClassNotFoundException {
        FileInputStream fis = this.openFileInput("WordListSingleton");
        ObjectInputStream is = new ObjectInputStream(fis);
        WordListSingleton wordListSingleton = (WordListSingleton) is.readObject();
        is.close();
        fis.close();
        return wordList;
    }*/
/*
    @Override
    public void onPause(){
        super.onPause();
        try {
            saveObject(WordListSingleton.getInstance().getWordList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
