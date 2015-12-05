package com.vocab.vocab.AsyncTasks;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.vocab.vocab.Utilities.Utils;
import com.vocab.vocab.Visualize.DefinitionActivity;

import java.net.URL;

/**
 * Created by Hisham on 11/2/2015.
 */
public class FetchImageAsyncTask extends AsyncTask {
    Context mCallingContext;
    DefinitionActivity mCallingActivity;
    String mWord;

    public FetchImageAsyncTask(String mWord, Context mCallingContext, DefinitionActivity mCallingActivity) {
        this.mCallingContext = mCallingContext;
        this.mCallingActivity = mCallingActivity;
        this.mWord = mWord;
    }

    @Override
    protected String doInBackground(Object[] params) {
        JsonObject toRet = null;
        toRet = Utils.queryBingForImage(mWord, mCallingContext);
        Log.d("toREt", toRet.toString());
        URL imageURL = null;
        try {
            imageURL = Utils.parseURLFromBingJSON(toRet, Configuration.ORIENTATION_PORTRAIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageURL.toString();
    }

    @Override
    protected void onPostExecute(Object o) {
        Log.d("URL", o.toString());
        mCallingActivity.setImageView(o.toString());
        super.onPostExecute(o);
    }

}
