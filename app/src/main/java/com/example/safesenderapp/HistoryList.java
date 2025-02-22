package com.example.safesenderapp;

import java.util.List;

public class HistoryList {
    List<String> date;
    List<String> time;
    List<String> name;
    List<String> id;
    List<String> type; // national international
    List<String> category; // outgoing incoming;
    List<String> sign;// (- for outgoing) (+ for incoming)
    List<Float> amount;
    List<String> currency;// which currency is used
    List<Integer> image;// arrow showing incoming outgoing

    public HistoryList(List<String> date, List<String> time,
                       List<String> name, List<String> id,
                       List<String> type, List<String> category,
                       List<String> sign, List<Float> amount,
                       List<String> currency, List<Integer> image) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.id = id;
        this.type = type;
        this.category = category;
        this.sign = sign;
        this.amount = amount;
        this.currency = currency;
        this.image = image;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getSign() {
        return sign;
    }

    public void setSign(List<String> sign) {
        this.sign = sign;
    }

    public List<Float> getAmount() {
        return amount;
    }

    public void setAmount(List<Float> amount) {
        this.amount = amount;
    }

    public List<String> getCurrency() {
        return currency;
    }

    public void setCurrency(List<String> currency) {
        this.currency = currency;
    }

    public List<Integer> getImage() {
        return image;
    }

    public void setImage(List<Integer> image) {
        this.image = image;
    }
}
