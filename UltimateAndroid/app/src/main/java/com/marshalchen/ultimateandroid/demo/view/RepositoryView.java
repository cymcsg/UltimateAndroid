package com.marshalchen.ultimateandroid.demo.view;


import com.marshalchen.ultimateandroid.demo.model.RepositoryList;

/**
 * Created by Marshal Chen on 4/7/2016.
 */
public interface RepositoryView<T> {
    void setReposotories(RepositoryList<T> repositoryList);

    void showMessage(String message);

    void showProgressBar();

}
