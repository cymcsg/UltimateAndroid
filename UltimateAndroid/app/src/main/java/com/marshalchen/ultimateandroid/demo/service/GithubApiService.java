package com.marshalchen.ultimateandroid.demo.service;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.marshalchen.ua.common.commonUtils.basicUtils.BasicUtils;
import com.marshalchen.ultimateandroid.demo.model.RepositoryList;

import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cymcsg on 27/6/16.
 */
public class GithubApiService {
    public static final String BASE_URL = "https://api.github.com/";
    private Retrofit retrofit;


    private GithubApiServiceInterface githubApiServiceInterface;

    private GithubApiService() {
        OkHttpClient.Builder httpclientBuilder = new OkHttpClient.Builder();
        httpclientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        httpclientBuilder.addNetworkInterceptor(new StethoInterceptor());
        retrofit = new Retrofit.Builder().client(httpclientBuilder.build()).addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).baseUrl(BASE_URL).build();
        githubApiServiceInterface = retrofit.create(GithubApiServiceInterface.class);

    }

    private static class GithubApiSingletonHolder {
        private static final GithubApiService INSTANCE = new GithubApiService();
    }

    public static final GithubApiService getInstance() {
        return GithubApiSingletonHolder.INSTANCE;
    }

    public GithubApiServiceInterface getGithubApiServiceInterface() {
        return githubApiServiceInterface;
    }

    public void loadRepositoryInfo(Subscriber<RepositoryList> subscriber, String query, String language, String sort, String order) {
        githubApiServiceInterface.getRepositoryBySearch(BasicUtils.judgeNotNull(language) ? query + URLDecoder.decode("+") + "language:" + language : query, sort, order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void loadRepositoryInfo(Subscriber<RepositoryList> subscriber, String query, String language, String sort) {
        loadRepositoryInfo(subscriber, query, language, sort, "");
    }

    public void loadRepositoryInfo(Subscriber<RepositoryList> subscriber, String query, String language) {
        loadRepositoryInfo(subscriber, query, language, "");
    }

    public void loadRepositoryInfo(Subscriber<RepositoryList> subscriber, String query) {
        loadRepositoryInfo(subscriber, query, "");
    }


}
