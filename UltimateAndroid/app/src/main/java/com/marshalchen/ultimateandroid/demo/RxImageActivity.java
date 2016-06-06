package com.marshalchen.ultimateandroid.demo;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;

import com.marshalchen.ua.common.commonUtils.logUtils.Logs;

import java.io.File;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;


public class RxImageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        File[] files = null;
//        Observable.from(files).flatMap(new Func1<File, Observable<?>>() {
//            @Override
//            public Observable<?> call(File file) {
//                return null;
//            }
//        });
        Observable.just("hello").flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.just(s + "   flatmap");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String o) {
                Logs.d("call     " + o);
            }
        });

    }

}
