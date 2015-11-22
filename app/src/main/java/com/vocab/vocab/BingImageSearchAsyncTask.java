package com.vocab.vocab;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;

import java.net.URL;

/**
 * Created by Hisham on 11/2/2015.
 */
public class BingImageSearchAsyncTask extends AsyncTask<String,Integer,URL> {

    private static final String TAG = "ImageSearchAsyncTask";
    private Context mContext;
    private ImageSearchCompletionListener mCompletionListener;

    public interface ImageSearchCompletionListener{
        void imageUrlFound(URL url);
        void imageUrlNotFound();
    }

    public BingImageSearchAsyncTask(Context context, ImageSearchCompletionListener completionListener){
        mContext = context;
        mCompletionListener = completionListener;
    }


    @Override
    protected URL doInBackground(String... query) {

        try{
            JsonObject jsonResult = Utils.queryBingForImage(query[0],mContext);

            int desiredOrientation = mContext.getResources().getConfiguration().orientation;

            return Utils.parseURLFromBingJSON(jsonResult,desiredOrientation);

        }
        catch(Exception e){
            Log.e(TAG, "error:" + e.getMessage());
            return null;
        }

    }

    @Override
    protected void onPostExecute(URL url){
        super.onPostExecute(url);

        if(mCompletionListener != null){
            if(url != null){
                mCompletionListener.imageUrlFound(url);
            }
            else{
                mCompletionListener.imageUrlNotFound();
            }
        }

    }}

