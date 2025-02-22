package com.example.safesenderapp;

import java.util.List;

public class MainList {
    List<Float> amount;
    List<String> symbol;
    List<String> currencyName;
    List<Integer> image;
    public MainList(){

    }

    public MainList(List<Float> amount, List<String> symbol, List<String> currencyName, List<Integer> image) {
        this.amount = amount;
        this.symbol = symbol;
        this.currencyName = currencyName;
        this.image = image;
    }

    public List<Float> getAmount() {
        return amount;
    }

    public List<String> getSymbol() {
        return symbol;
    }

    public List<String> getCurrencyName() {
        return currencyName;
    }

    public List<Integer> getImage() {
        return image;
    }

    public void setAmount(List<Float> amount) {
        this.amount = amount;
    }

    public void setSymbol(List<String> symbol) {
        this.symbol = symbol;
    }

    public void setCurrencyName(List<String> currencyName) {
        this.currencyName = currencyName;
    }

    public void setImage(List<Integer> image) {
        this.image = image;
    }
}
