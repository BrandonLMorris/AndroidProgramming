package com.example.bmorris.runtracker;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by bmorris on 1/8/15.
 */
public abstract class SQLiteCursorLoader extends android.support.v4.content.AsyncTaskLoader<Cursor> {
    private Cursor mCursor;

    public SQLiteCursorLoader(Context context) {
        super(context);
    }

    protected abstract Cursor loadCursor();

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = loadCursor();
        if(cursor != null) {
            //Ensure that the cotent window if filled
            cursor.getCount();
        }
        return cursor;
    }

    @Override
    public void deliverResult(Cursor data) {
        Cursor oldCursor = mCursor;
        mCursor = data;
        if(isStarted()) {
            super.deliverResult(data);
        }

        if(oldCursor != null && oldCursor != data && !oldCursor.isClosed()) {
            oldCursor.close();
        }
    }

    @Override
    protected void onStartLoading() {
        if(mCursor != null) {
            deliverResult(mCursor);
        }
        if(takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        //Attempt to cancel the current load task if possibl
        cancelLoad();
    }

    @Override
    public void onCanceled(Cursor cursor) {
        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        //Ensure the loader is stopped
        onStopLoading();
        if(mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        mCursor = null;
    }

}
