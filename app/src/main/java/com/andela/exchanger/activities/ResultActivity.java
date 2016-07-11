package com.andela.exchanger.activities;

import android.os.Bundle;
import android.renderscript.Double2;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.exchanger.R;
import com.andela.exchanger.currency.CurrencyFetcher;

public class ResultActivity extends AppCompatActivity {

    private TextView labelresult;

    private TextView viewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initializeViews();
        populateResults(getIntent().getStringExtra("TRANS"));
    }

    private void populateResults(String trans) {
        String[] results = trans.split(";");
        labelresult.setText(results[1] + " USD in " + results[0] + ":" );
        double baseRate = CurrencyFetcher.list.get(results[0]);
        double currentAmount = Double.parseDouble(results[1]);
        double conversion = currentAmount * baseRate;
        viewResult.setText(Double.toString(conversion));
    }

    private void initializeViews() {
        labelresult = (TextView) findViewById(R.id.label_result);
        viewResult = (TextView) findViewById(R.id.view_result);
    }

}
