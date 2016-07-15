package com.marshalchen.ultimateandroid.demo.presenter;


import com.marshalchen.ua.common.commonUtils.logUtils.Logs;
import com.marshalchen.ultimateandroid.demo.model.Repository;
import com.marshalchen.ultimateandroid.demo.model.RepositoryList;
import com.marshalchen.ultimateandroid.demo.service.GithubApiService;
import com.marshalchen.ultimateandroid.demo.view.RepositoryView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Marshal Chen on 28/6/16.
 */
public class RepositoryPresenter {
    private RepositoryView repositoryView;

    public RepositoryPresenter(RepositoryView repositoryView) {
        this.repositoryView = repositoryView;
    }

    public void loadRepositoryInfo() {
        repositoryView.showProgressBar();
        Subscriber subscriber = new Subscriber<RepositoryList<List<Repository>>>() {
            @Override
            public void onCompleted() {
                Logs.d("completed");
            }

            @Override
            public void onError(Throwable e) {
                Logs.e(e.getMessage());
            }

            @Override
            public void onNext(RepositoryList<List<Repository>> repositoryList) {
                repositoryView.setReposotories(repositoryList);
            }
        };
      //  GithubApiService.getInstance().loadRepositoryInfo(subscriber, "ultimate", "java");
        GithubApiService.getInstance().loadRepositoryInfo(subscriber, "stars:>100", "java");
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}
