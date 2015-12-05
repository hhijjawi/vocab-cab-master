package com.vocab.vocab.Utilities;


import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.vocab.vocab.ModelData.Constants;

import java.net.URL;
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
}

