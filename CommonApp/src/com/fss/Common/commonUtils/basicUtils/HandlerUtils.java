package com.fss.Common.commonUtils.basicUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.fss.Common.commonUtils.logUtils.Logs;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class HandlerUtils {
    public static void dismissView() {

    }

    public static Handler dismissViewHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if (msg.obj instanceof View) {
                    ((View) msg.obj).setVisibility(View.GONE);
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
                Logs.e(e, "");
            }
        }
    };
    public static Handler invisiablViewHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if (msg.obj instanceof View) {
                    ((View) msg.obj).setVisibility(View.INVISIBLE);
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
                Logs.e(e, "");
            }
        }
    };
    public static Handler visiablViewHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if (msg.obj instanceof View) {
                    ((View) msg.obj).setVisibility(View.VISIBLE);
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
                Logs.e(e, "");
            }
        }
    };

    public static void sendMessageHandler(Handler handler, int what) {
        Message message = new Message();
        message.what = what;
        handler.sendMessage(message);
    }

    public static void sendMessageHandler(Handler handler, int what, Object obj) {
        Message message = new Message();
        message.what = what;
        message.obj = obj;
        handler.sendMessage(message);
    }

    public static void sendMessageHandler(Handler handler, int what, String key, String value) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public static void sendMessageHandler(Handler handler, int what, String key, int value) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putInt(key, value);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public static void sendMessageHandlerDelay(Handler handler, int what, String key, String value, long delayTime) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        message.setData(bundle);
        // handler.sendMessage(message);
        handler.sendMessageDelayed(message, delayTime);
    }

    public static void sendMessageHandlerDelay(Handler handler, int what, String key, int value, long delayTime) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putInt(key, value);
        message.setData(bundle);
        // handler.sendMessage(message);
        handler.sendMessageDelayed(message, delayTime);
    }

    public static void sendMessageHandlerDelay(Handler handler, int what, long delayTime) {
        Message message = new Message();
        message.what = what;
        handler.sendMessageDelayed(message, delayTime);
    }

    public static void sendMessageHandlerDelay(Handler handler, int what, Object obj, long delayTime) {
        Message message = new Message();
        message.what = what;
        message.obj = obj;
        handler.sendMessageDelayed(message, delayTime);
    }

    public static void sendMessageHandler(Handler handler, int what, Bundle bundle) {
        Message message = new Message();
        message.what = what;
        message.setData(bundle);
        handler.sendMessage(message);
    }

}
