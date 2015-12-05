package com.vocab.vocab.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.vocab.vocab.ModelData.Word;
import com.vocab.vocab.ModelData.WordListSingleton;
import com.vocab.vocab.Utilities.MyJsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Hisham on 10/5/2015.
 */
public class GetWordsAndDefinitionsAsyncTask extends AsyncTask {
    ArrayList<String> wordsList = new ArrayList<String>();
    ArrayList<String> definitionsList = new ArrayList<String>();
    ArrayList<ArrayList<String>> wordsAndDefs = new ArrayList<ArrayList<String>>();
    ProgressDialog pd;
    Context callingActivity;
    TextToSpeech tts;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd.setMessage("Loading words...");
        pd.show();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        final Activity currentContext = (Activity) callingActivity;
        Runnable prepDialogueOnUIThread = new Runnable() {
            @Override
            public void run() {


            }
        };
        currentContext.runOnUiThread(prepDialogueOnUIThread);
        String defsJson = MyJsonReader.loadJSONFromAsset(currentContext);
        JSONObject defsJsonAsJson = null;
        try {
            defsJsonAsJson = new JSONObject(defsJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String word;
        String def;
        try {
            for (int i = 0; i <100 ; i++) {
                word = defsJsonAsJson.getJSONObject("results").getJSONArray("collection1").getJSONObject(i).getJSONObject("Word").getString("text");
                def = defsJsonAsJson.getJSONObject("results").getJSONArray("collection1").getJSONObject(i).getString("Definition");
                WordListSingleton.getInstance().getWordList().add(new Word(word, def));
                fetchWordAudio(def, word);
                Log.d(word, def);
            }// defsJsonAsJson.getJSONObject("results").getJSONArray("collection1").length()
        } catch (JSONException e) {
            e.printStackTrace();
        }
        wordsAndDefs.add(wordsList);
        wordsAndDefs.add(definitionsList);

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        pd.dismiss();
    }

    public GetWordsAndDefinitionsAsyncTask(Context context, TextToSpeech tts) {
        this.pd = new ProgressDialog(context);
        this.callingActivity = context;
        this.tts = tts;
    }

    public ArrayList<String> getWordsList() {
        return wordsList;
    }

    public ArrayList<String> getDefinitionsList() {
        return definitionsList;
    }
    public void fetchWordAudio(final String definition, final String word){
        final String exStoragePath                = callingActivity.getFilesDir().getAbsolutePath();
        String tempFilename                 = word+".wav";
        File appTmpPath       = new File(exStoragePath);
        appTmpPath.mkdirs();
        final String tempDestFile                 = appTmpPath.getAbsolutePath() + "/" + tempFilename;
        final String defText = definition;
        final HashMap<String, String> myHashRender = new HashMap();

        tts.synthesizeToFile(defText, myHashRender, tempDestFile);}}
