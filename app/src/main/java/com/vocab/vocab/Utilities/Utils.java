package com.vocab.vocab.Utilities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.vocab.vocab.ModelData.Constants;
import com.vocab.vocab.ModelData.Word;
import com.vocab.vocab.ModelData.WordListSingleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


/**
 * Created by jared on 4/16/15.
 */
public class Utils {

    public static URL parseURLFromBingJSON(JsonObject jsonObject, int desiredOrientation) throws Exception{
        JsonArray imageResults = jsonObject
                .getAsJsonObject("d").getAsJsonArray("results").get(0).getAsJsonObject().getAsJsonArray("Image");
        if (imageResults != null && imageResults.size() > 0) {
            for (int i = 0; i < imageResults.size(); i++) {
                JsonObject imageResult = imageResults.get(i).getAsJsonObject();
                boolean tooBig = imageResult.get("FileSize").getAsInt() > Constants.MAX_IMAGE_FILE_SIZE_IN_BYTES;

                if (tooBig == false) {
                    int width = imageResult.get("Width").getAsInt();
                    int height = imageResult.get("Height").getAsInt();

                    if (desiredOrientation == Configuration.ORIENTATION_PORTRAIT) {
                        if (height > width) {
                            return new URL(imageResult.get("MediaUrl").getAsString());
                        }
                    } else if (desiredOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                        if (width > height) {
                            return new URL(imageResult.get("MediaUrl").getAsString());
                        }
                    }
                }
            }
        }

        return null;
    }

    public static JsonObject queryBingForImage(String query, Context context) {
        try {

            return Ion.with(context).load(Constants.BING_SEARCH_URL).addQuery("Sources","'image'")
                    .addHeader("Authorization", "Basic " + Base64.encodeToString((Constants.BING_SEARCH_API_TOKEN + ":" + Constants.BING_SEARCH_API_TOKEN).getBytes("UTF-8"), Base64.NO_WRAP))
                    .addQuery("Adult","'Strict'")
                    .addQuery("Query","'" + query
                            + "'")
                    .addQuery("$format","JSON")
                    .asJsonObject().get();

        } catch (Exception e) {
            Log.d("e", e.toString());

            return null;
        }
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

//From stack overflow
    public static int randInt(int min, int max) {

        Random rand=new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    public static void  saveObject(Serializable ser,Activity callingActivity) throws IOException {
        Log.d("Saved","Saved");
        FileOutputStream fos = callingActivity.openFileOutput("WordListSingleton", Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(ser);
        os.close();
        fos.close();
    }
    public static WordListSingleton loadWList(Activity mCallingActivity) throws IOException, ClassNotFoundException {
        Log.d("LoadedNotSaved","LoadedNotSaved");
        FileInputStream fis = mCallingActivity.openFileInput("WordListSingleton");
        ObjectInputStream is = new ObjectInputStream(fis);
        WordListSingleton wordListSingleton = (WordListSingleton) is.readObject();
        is.close();
        fis.close();
        return wordListSingleton;
    }
    public static void calculateProgress(Activity mCallingActivity){
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
        mCallingActivity.startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }
/* Old helper function for debugging. Might be useful again.

    public void testTTS() {
        mTTS = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //I like the british accent and the cadence kind of slow
                    mTTS.setLanguage(Locale.UK);
                    mTTS.setSpeechRate((float) 0.60);
                    mTTS.speak("hello i like cheese", TextToSpeech.QUEUE_ADD, null);
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
                    mTTS.synthesizeToFile(wakeUpText, myHashRender, tempDestFile);

                }
            }
        }
        );
        Log.d("here", "here");

    }
    */

}

