package com.andela.exchanger.currency;

import android.content.Context;
import android.os.SystemClock;

import com.andela.exchanger.config.Constants;
import com.andela.exchanger.remote.DataFetcher;
import com.andela.exchanger.remote.JsonCallback;
import com.andela.exchanger.utils.PreferenceHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CurrencyFetcher {

    private CurrencyListCallback currencyListCallback;

    public static Map<String, Double> list;

    private Context context;

    private String cachedData;

    private String currentData;

    PreferenceHelper preferenceHelper;

    public CurrencyFetcher(Context context, CurrencyListCallback currencyListCallback) {
        this.currencyListCallback = currencyListCallback;
        this.context = context;
        preferenceHelper = new PreferenceHelper(context);
    }

    public void getCurrencyList() {
        if (!isDataAvailable()) {
            DataFetcher fetcher = new DataFetcher(jsonCallback);
            fetcher.execute(Constants.API_URL);
        } else {
            generateCurrencyList(cachedData);
        }
    }

    private boolean isDataAvailable() {
        cachedData = preferenceHelper.getCachedJsonData();
        if (cachedData != null && !cachedData.isEmpty()) {
            long timestamp = preferenceHelper.getLastSaveTime();
            long currentMillis = SystemClock.elapsedRealtime();
            if ((currentMillis - timestamp) < 172800000) {
                return true;
            }
        }
        return false;
    }

    private JsonCallback jsonCallback = new JsonCallback() {
        @Override
        public void onJsonReceived(String json) {
            currentData = json;
            generateCurrencyList(json);
        }
    };

    private void generateCurrencyList(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json)
                    .getJSONObject("rates");
            Iterator<String> keys = jsonObject.keys();
            HashMap<String, Double> currencyList = new HashMap<>();
            while (keys.hasNext()) {
                String key = keys.next();
                Double value = Double.parseDouble(jsonObject.getString(key));
                currencyList.put(key, value);
            }
            storeCurrencyList(currencyList);
            cacheData();
            currencyListCallback.onCurrencyListObtained(currencyList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cacheData() {
        if (currentData != null) {
            preferenceHelper.setCachedJsonData(currentData);
            preferenceHelper.setLastSavedTime(SystemClock.elapsedRealtime());
        }
    }

    private void storeCurrencyList(Map<String, Double> currencyList) {
        CurrencyFetcher.list = currencyList;
    }
}
