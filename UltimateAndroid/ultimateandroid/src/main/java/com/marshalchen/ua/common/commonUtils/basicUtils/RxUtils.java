package com.marshalchen.ua.common.commonUtils.basicUtils;

import android.os.Looper;

import rx.Subscription;

/**
 * Created by cymcsg on 31/5/16.
 */
public class RxUtils {

    public static boolean isCurrentlyOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
