package com.nguyen.wifibruteforce;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.util.Log;

import com.codekidlabs.storagechooser.StorageChooser;

public class DictionaryTask extends AsyncTask<String, Integer, Integer> {
    Activity activity;

    public DictionaryTask(Activity activity) {
        this.activity = activity;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected Integer doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }
}
