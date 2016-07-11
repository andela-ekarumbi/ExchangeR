package com.andela.exchanger.currency;

import java.util.Map;

public interface CurrencyListCallback {
    void onCurrencyListObtained(Map<String, Double> currencyList);
}
