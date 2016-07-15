package com.marshalchen.ultimateandroid.demo.model;


/**
 * Created by Marshal Chen on 28/6/16.
 */
public class RepositoryList<T> {
    private int total_count;
    private boolean incomplete_results;
    private T items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public T getRepositories() {
        return items;
    }

    public void setRepositories(T repositories) {
        this.items = repositories;
    }
}
