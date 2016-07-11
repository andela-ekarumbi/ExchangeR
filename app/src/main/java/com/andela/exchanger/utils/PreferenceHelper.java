package com.andela.exchanger.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHelper {

    private Context context;

    private final String TIMESTAMP = "CUR";

    private final String JSON = "JSON";

    private SharedPreferences preferences;

    private SharedPreferences.Editor editor;

    public PreferenceHelper(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public long getLastSaveTime() {
        return preferences.getLong(TIMESTAMP, 0);
    }

    public void setLastSavedTime(long timestamp) {
        editor.putLong(TIMESTAMP, timestamp);
        editor.commit();
    }

    public String getCachedJsonData() {
        return preferences.getString(JSON, null);
    }

    public void setCachedJsonData(String data) {
        store(JSON, data);
    }

    private void store(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
}
