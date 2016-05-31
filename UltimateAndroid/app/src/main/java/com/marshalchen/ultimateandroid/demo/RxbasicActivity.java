package com.marshalchen.ultimateandroid.demo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.marshalchen.ua.common.commonUtils.basicUtils.RxUtils;
import com.marshalchen.ua.common.commonUtils.logUtils.Logs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxbasicActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rxBasicListview)
    ListView rxBasicListview;

    List<String> dataList= new ArrayList<>();
    DataAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxbasic);
        ButterKnife.bind(this);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dataAdapter = new DataAdapter(this,dataList);
        rxBasicListview.setAdapter(dataAdapter);
    }

    @OnClick(R.id.fab_rx_basic)
    public void loadRxData() {
        addData("fab click");
        subscription = getObservable().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(getObserver());

    }

    Subscription subscription;

    private Observable<Boolean> getObservable() {
        return Observable.just(true).map(new Func1<Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean aBoolean) {
                addData("Within Observable");
                _doSomeLongOperation_thatBlocksCurrentThread();
                return false;
            }
        });
    }

    private Observer<Boolean> getObserver() {
        return new Observer<Boolean>() {
            @Override
            public void onCompleted() {
                addData("complete");
            }

            @Override
            public void onError(Throwable e) {
                addData("error     " + e.getMessage());
            }

            @Override
            public void onNext(Boolean aBoolean) {
                addData(String.format("onNext with return value \"%b\"", aBoolean));
            }
        };
    }


    private void _doSomeLongOperation_thatBlocksCurrentThread() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Logs.e("Operation was interrupted");
        }
    }

    private void addData(String logMsg) {
        Logs.d("addData---"+dataList.size());
        dataList.add(0,logMsg + " (main thread) ");
        dataAdapter.notifyDataSetChanged();
//        if (RxUtils.isCurrentlyOnMainThread()) {
//            dataList.add(0,logMsg + " (main thread) ");
//            dataAdapter.clear();
//            dataAdapter.addAll(dataList);
//        } else {
//            dataList.add(0,logMsg + " (NOT main thread) ");
//
//            // You can only do below stuff on main thread.
//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//
//                @Override
//                public void run() {
//                    dataAdapter.clear();
//                    dataAdapter.addAll(dataList);
//
//                }
//            });
//        }
    }



    private class DataAdapter
            extends ArrayAdapter<String> {

        public DataAdapter(Context context, List<String> logs) {
            super(context, R.layout.item_listview_activity_main, R.id.main_activity_listview_item_textview, logs);
        }
    }
}
