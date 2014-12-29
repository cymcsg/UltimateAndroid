package com.marshalchen.common.commonUtils.basicUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.marshalchen.common.commonUtils.logUtils.Logs;

/**
 * An easy way to send Handler
 * <p>{@link #dismissViewHandler}</p>
 * <p>{@link #visiablViewHandler}</p>
 * <p>{@link #invisiablViewHandler}</p>
 * <p>{@link #sendMessageHandler(android.os.Handler, int)}</p>
 * <p>{@link #sendMessageHandler(android.os.Handler, int, android.os.Bundle)}</p>
 * <p>{@link #sendMessageHandler(android.os.Handler, int, Object)}</p>
 * <p>{@link #sendMessageHandler(android.os.Handler, int, String, int)}</p>
 * <p>{@link #sendMessageHandler(android.os.Handler, int, String, String)}</p>
 * <p>{@link #sendMessageHandlerDelay(android.os.Handler, int, long)}</p>
 * <p>{@link #sendMessageHandlerDelay(android.os.Handler, int, Object, long)}</p>
 * <p>{@link #sendMessageHandlerDelay(android.os.Handler, int, String, String, long)}</p>
 * <p>{@link #sendMessageHandlerDelay(android.os.Handler, int, String, int, long)}</p>
 */
public class HandlerUtils {
    /**
     * Set visibility of the view which in message's obj visibility to GONE
     */
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

    /**
     * Set visibility of the view which in message's obj visibility to INVISIBLE
     */
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

    /**
     * Set visibility of the view which in message's obj visibility to VISIBLE
     */
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

    /**
     * Send an empty message which default what value is 0
     *
     * @param handler
     */
    public static void sendMessageHandler(Handler handler) {
        sendMessageHandler(handler, 0);
    }

    /**
     * Send an empty message containing only the what value.
     * @param handler
     * @param what
     */
    public static void sendMessageHandler(Handler handler, int what) {
        handler.sendEmptyMessage(what);
    }

    /**
     * Pushes a message onto the end of the message queue after all pending messages before the current time.
     * The message contains what value and object.
     * @param handler
     * @param what
     * @param obj
     */
    public static void sendMessageHandler(Handler handler, int what, Object obj) {
        Message message = new Message();
        message.what = what;
        message.obj = obj;
        handler.sendMessage(message);
    }

    /**
     * Pushes a message onto the end of the message queue after all pending messages before the current time.
     * The message contains what value and a bundle with key and a String value.
     * @param handler
     * @param what
     * @param key
     * @param value
     */
    public static void sendMessageHandler(Handler handler, int what, String key, String value) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    /**
     * Pushes a message onto the end of the message queue after all pending messages before the current time.
     * The message contains what value and a bundle with key and a int value.
     * @param handler
     * @param what
     * @param key
     * @param value
     */
    public static void sendMessageHandler(Handler handler, int what, String key, int value) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putInt(key, value);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    /**
     * Enqueue a message into the message queue after all pending messages before (current time + delayMillis).
     * The message contains what value and a bundle with key and a String value.
     * @param handler
     * @param what
     * @param key
     * @param value
     * @param delayTime
     */
    public static void sendMessageHandlerDelay(Handler handler, int what, String key, String value, long delayTime) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        message.setData(bundle);
        // handler.sendMessage(message);
        handler.sendMessageDelayed(message, delayTime);
    }

    /**
     * Enqueue a message into the message queue after all pending messages before (current time + delayMillis).
     * The message contains what value and a bundle with key and a int value.
     * @param handler
     * @param what
     * @param key
     * @param value
     * @param delayTime
     */
    public static void sendMessageHandlerDelay(Handler handler, int what, String key, int value, long delayTime) {
        Message message = new Message();
        message.what = what;
        Bundle bundle = new Bundle();
        bundle.putInt(key, value);
        message.setData(bundle);
        // handler.sendMessage(message);
        handler.sendMessageDelayed(message, delayTime);
    }

    /**
     * Sends a Message containing only the what value, to be delivered after the specified amount of time elapses.
     * @param handler
     * @param what
     * @param delayTime
     */
    public static void sendMessageHandlerDelay(Handler handler, int what, long delayTime) {
        handler.sendEmptyMessageDelayed(what,delayTime);
    }

    /**
     * Enqueue a message containing what value and object into the message queue after all pending messages before (current time + delayMillis).
     *
     * @param handler
     * @param what
     * @param obj
     * @param delayTime
     */
    public static void sendMessageHandlerDelay(Handler handler, int what, Object obj, long delayTime) {
        Message message = new Message();
        message.what = what;
        message.obj = obj;
        handler.sendMessageDelayed(message, delayTime);
    }

    /**
     * Pushes a message containing bundle onto the end of the message queue after all pending messages before the current time.
     *
     * @param handler
     * @param what
     * @param bundle
     */
    public static void sendMessageHandler(Handler handler, int what, Bundle bundle) {
        Message message = new Message();
        message.what = what;
        message.setData(bundle);
        handler.sendMessage(message);
    }

}
