package com.andela.exchanger.remote;


import android.os.AsyncTask;

public class DataFetcher extends AsyncTask<String, Integer, String> {//can u see this?

    private JsonCallback callback;

    public DataFetcher(JsonCallback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        HttpHelper helper = new HttpHelper();
        return helper.fetchData(url);
    }

    @Override
    protected void onPostExecute(String result) {
        callback.onJsonReceived(result);
    }
}
