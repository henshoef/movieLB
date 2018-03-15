package com.example.android.moviemanagment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.telecom.Call;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Android on 14/03/2018.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    public interface CallBack{
        void onPreExecute();
        void onSuccses(Bitmap result);
    }
    private CallBack callBack;

    public DownloadImageTask(CallBack callback) {
        this.callBack = callback;
    }

    @Override
    protected void onPreExecute() {
        callBack.onPreExecute();
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap bitmap = null;
        try {
            URL url=new URL(urldisplay);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input=connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        callBack.onSuccses(result);
    }

}
