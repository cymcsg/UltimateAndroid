package com.marshalchen.ultimateandroid.demo.service;


import com.marshalchen.ultimateandroid.demo.model.Repository;
import com.marshalchen.ultimateandroid.demo.model.RepositoryList;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Marshal Chen on 27/6/16.
 */
public interface GithubApiServiceInterface {
    @GET("search/repositories")
    Observable<RepositoryList<List<Repository>>> getRepositoryBySearch(@Query("q") String query, @Query("sort") String sort,
                                                                       @Query("order") String order);

    @GET("search/repositories")
    Observable<RepositoryList> getRepositoryBySearch(@Query("q") String query, @Query("sort") String sort
    );


    @GET("search/repositories")
    Observable<RepositoryList> getRepositoryBySearch(@Query("q") String query);

}
