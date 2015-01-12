package com.example.bmorris.photogallery;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.session.MediaSession;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Dedicated class to take care of downloading of the individual thumbnails of the photos
 * A subclass of HandlerThread, ThumbnailDownloader is a message loop consisting of its
 * own dedicated thread and a looper from HandlerThread
 * Created by bmorris on 1/7/15.
 */
//Generic argument Token is the type of object to identify each download
public class ThumbnailDownloader<Token> extends HandlerThread {
    //String constant for debug
    private static final String TAG = "ThumbnailDownloader";
    //the what constant for the Messages
    private static final int MESSAGE_DOWNLOAD = 0;

    //Handler reference for this thread
    Handler mHandler;
    //Synchronized map for each url to its image
    Map<Token, String> requestMap = Collections.synchronizedMap(new HashMap<Token, String>());
    //Reference to the response handler passed in from the main thread
    Handler mResponseHandler;
    Listener<Token> mListener;

    //Constructor that accepts a handler (from the main thread for UI update)
    public ThumbnailDownloader(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }

    public void setListener(Listener<Token> listener) {
        mListener = listener;
    }

    //Creates the handler when ready and defines handleMessage()
    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == MESSAGE_DOWNLOAD) {
                    @SuppressWarnings("unchecked")
                    Token token = (Token)msg.obj;
                    Log.i(TAG, "Got a request for url: " + requestMap.get(token));
                    handleRequest(token);
                }
            }
        };
    }

    //Called from the GridView adapter to create and queue the Messages for the thumbnails
    public void queueThumbnail(Token token, String url) {
        Log.i(TAG, "Got an URL: " + url);
        requestMap.put(token, url);

        mHandler.obtainMessage(MESSAGE_DOWNLOAD, token).sendToTarget();
    }

    //Called from handleMessage(), actually downloads the image and post()'s it back to the responseHandler
    private void handleRequest(final Token token) {
        try {
            final String url = requestMap.get(token);
            if(url == null) return;

            byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            Log.i(TAG, "Bitmap created");

            mResponseHandler.post(new Runnable() {
                public void run() {
                    if(requestMap.get(token) != url) return;
                    requestMap.remove(token);
                    mListener.onThumbnailDownloaded(token, bitmap);
                }
            });
        } catch (IOException ioe) {
            Log.e(TAG, "error downloading image", ioe);
        }
    }

    //Called in PhotoGalleryFragment's onDestroy() to prevent bad things for stuff like screen rotation
    public void clearQueue() {
        mHandler.removeMessages(MESSAGE_DOWNLOAD);
        requestMap.clear();
    }

    public interface Listener<Token> {
        void onThumbnailDownloaded(Token token, Bitmap thumbnail);
    }

}
