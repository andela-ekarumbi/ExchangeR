package com.andela.exchanger.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.exchanger.R;
import com.andela.exchanger.currency.CurrencyFetcher;
import com.andela.exchanger.currency.CurrencyListCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private Spinner spinner;

    private EditText currencyEdit;

    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        CurrencyFetcher currencyFetcher = new CurrencyFetcher(this, currencyListCallback);
        initializeViews();
        setClickHandler();
        progressDialog = ProgressDialog.show(this, "Loading",
                "Fetching currencies, please wait...",
                true);
        currencyFetcher.getCurrencyList();
    }

    private void setClickHandler() {
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currencyVal = currencyEdit.getText().toString();
                if (!currencyVal.isEmpty()) {
                    String transportString = spinner.getSelectedItem().toString()
                            + ";"
                            + currencyVal;
                    Intent intent = new Intent(getBaseContext(), ResultActivity.class);
                    intent.putExtra("TRANS", transportString);
                    startActivity(intent);
                }
            }
        });
    }

    private void initializeViews() {
        spinner = (Spinner) findViewById(R.id.currencies);
        currencyEdit = (EditText) findViewById(R.id.currencyEdit);
        convertButton = (Button) findViewById(R.id.convertButton);
        currencyEdit.setText("1");
    }

    private CurrencyListCallback currencyListCallback = new CurrencyListCallback() {
        @Override
        public void onCurrencyListObtained(Map<String, Double> currencyList) {
            progressDialog.dismiss();
            populateCurrencySpinner(currencyList);
        }
    };

    private void populateCurrencySpinner(Map<String, Double> currencyList) {
        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, list);
        for (String s : currencyList.keySet()) {
            list.add(s);
        }
        Collections.sort(list);
        spinner.setAdapter(adapter);
    }
}
