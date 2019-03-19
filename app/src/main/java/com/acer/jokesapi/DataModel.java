package com.acer.jokesapi;

public class DataModel {
    String joke;

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public DataModel(String joke) {

        this.joke = joke;
    }
}