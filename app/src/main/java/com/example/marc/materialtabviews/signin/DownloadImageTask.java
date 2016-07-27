package com.example.marc.materialtabviews.signin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;
    private int backupResource;
    private final static String TAG = "DownloadImageTask";

    public DownloadImageTask(ImageView imageView, int backupResource) {
        this.imageView = imageView;
        this.backupResource = backupResource;
    }

    protected Bitmap doInBackground(String... url) {
        Bitmap icon = null;
        try {
            InputStream in = new java.net.URL(url[0]).openStream();
            icon = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            icon = BitmapFactory.decodeResource(null, backupResource);
        }
        return icon;
    }


    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
