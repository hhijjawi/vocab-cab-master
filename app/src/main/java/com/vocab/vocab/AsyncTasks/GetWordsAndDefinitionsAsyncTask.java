package com.vocab.vocab.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.vocab.vocab.ModelData.Word;
import com.vocab.vocab.ModelData.WordListSingleton;
import com.vocab.vocab.R;
import com.vocab.vocab.Utilities.MyJsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * Created by Hisham on 10/5/2015.
 */
public class GetWordsAndDefinitionsAsyncTask extends AsyncTask {
    private final boolean isFirstTime;
    ArrayList<String> mWordsList = new ArrayList<String>();
    ArrayList<String> mDefinitionsList = new ArrayList<String>();
    ArrayList<ArrayList<String>> mWordsAndDefs = new ArrayList<ArrayList<String>>();
    ProgressDialog mPd;
    Context mCallingActivity;
    TextToSpeech mTTS;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mPd.setMessage("Loading words... this might take a few minutes");
        mPd.show();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        final Activity currentContext = (Activity) mCallingActivity;
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
       // defsJsonAsJson.getJSONObject("results").getJSONArray("collection1").length()-1
            try {
                for (int i = 0; i < defsJsonAsJson.getJSONObject("results").getJSONArray("collection1").length() - 1; i++) {
                    Log.d("i", "" + i);
                    word = defsJsonAsJson.getJSONObject("results").getJSONArray("collection1").getJSONObject(i).getJSONObject("Word").getString("text");
                    def = defsJsonAsJson.getJSONObject("results").getJSONArray("collection1").getJSONObject(i).getString("Definition");
                    WordListSingleton.getInstance().getWordList().add(new Word(word, def));

                    Log.d(word, def);
                }// defsJsonAsJson.getJSONObject("results").getJSONArray("collection1").length()
            } catch (JSONException e) {
                e.printStackTrace();
            }

        mWordsAndDefs.add(mWordsList);
        mWordsAndDefs.add(mDefinitionsList);
        if(isFirstTime) {
            getMP3();

        }


        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        mPd.dismiss();
    }

    public GetWordsAndDefinitionsAsyncTask(Context context, TextToSpeech mTTS,boolean isFirstTime) {
        this.mPd = new ProgressDialog(context);
        this.mCallingActivity = context;
        this.mTTS = mTTS;
        this.isFirstTime=isFirstTime;
    }

    public ArrayList<String> getmWordsList() {
        return mWordsList;
    }

    public ArrayList<String> getDefinitionsList() {
        return mDefinitionsList;
    }
    public void fetchWordAudio(final String definition, final String word){

        final String exStoragePath                = Environment.getExternalStorageDirectory().getAbsolutePath()+"/mp3";
        Log.d("Path",exStoragePath);
        String tempFilename                 = word+".wav";
        File appTmpPath       = new File(exStoragePath);
        appTmpPath.mkdirs();

        final String tempDestFile                 = appTmpPath.getAbsolutePath() + "/" + tempFilename;
        final String defText = definition;
        final HashMap<String, String> myHashRender = new HashMap();

        mTTS.synthesizeToFile(defText, myHashRender, tempDestFile);}
    public void getMP3(){


        final LinkedList<Integer> mSongs=new LinkedList<Integer>();
        for(int i= R.raw.abase; i<R.raw.zephyr;i++){
            mSongs.add(i);

        }
        ArrayList<String> toBeScanned = new ArrayList<String>();

        for (int i = 0; i < mSongs.size(); i++) {
            try {
                String path = Environment.getExternalStorageDirectory() + "/audioFiles";
                File dir = new File(path);
                if (dir.mkdirs() || dir.isDirectory()) {
                    toBeScanned.add(dir.getAbsolutePath());
                    String fullPath = mCallingActivity.getResources().getResourceName(i+R.raw.abase) ;
                    int index = fullPath.lastIndexOf("/");
                    String fileName = fullPath.substring(index + 1)+".mp3";
                    Log.d("filename",fileName);

                    CopyRAWtoSDCard(mSongs.get(i), path + File.separator + fileName);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } String[] toScan= new String[toBeScanned.size()];
        for(int i=0;i<toBeScanned.size();i++){
            toScan[i]=toBeScanned.get(i);
        }
        MediaScannerConnection.scanFile(mCallingActivity, toScan, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {

            }
        });}
    private void CopyRAWtoSDCard(int id, String path) throws IOException {
        InputStream in = mCallingActivity.getResources().openRawResource(id);
        FileOutputStream out = new FileOutputStream(path);
        byte[] buff = new byte[1024];
        int read = 0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } finally {
            in.close();
            out.close();
        }
    }
}
