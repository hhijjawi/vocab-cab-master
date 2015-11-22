package com.vocab.vocab;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hisham on 10/5/2015.
 */
public class getWordsAndDefinitionsTask extends AsyncTask {
        ProgressDialog pd;
        Context callingActivity;

    static ArrayList<String> wordsList= new ArrayList<String>();
    static ArrayList<String> definitionsList= new ArrayList<String>();
    static ArrayList<ArrayList<String>> wordsAndDefs= new ArrayList<ArrayList<String>>();

    public getWordsAndDefinitionsTask(Context context) {
        this.pd = new ProgressDialog(context);
        this.callingActivity=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd.setMessage("Loading words...");
        pd.show();
    }

    public static ArrayList<String> getWordsList() {
        return wordsList;
    }

    public static ArrayList<String> getDefinitionsList() {
        return definitionsList;
    }




    @Override
    protected Object doInBackground(Object[] params) {
        final Activity currentContext=(Activity)callingActivity;
        Runnable prepDialogueOnUIThread=new Runnable() {
            @Override
                    public void run() {


            }
        };
        currentContext.runOnUiThread(prepDialogueOnUIThread);

        String defsJson=myJsonReader.loadJSONFromAsset(currentContext);
        JSONObject defsJsonAsJson=null;
        try {
             defsJsonAsJson=new JSONObject(defsJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
String word;
        String def;
        try {
            for(int i=0; i<defsJsonAsJson.getJSONObject("results").getJSONArray("collection1").length();i++) {
                word=defsJsonAsJson.getJSONObject("results").getJSONArray("collection1").getJSONObject(i).getJSONObject("Word").getString("text");
                def=defsJsonAsJson.getJSONObject("results").getJSONArray("collection1").getJSONObject(i).getString("Definition");
                wordsList.add(word);
                definitionsList.add(def);
                Log.d(word,def);
            }
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
}
