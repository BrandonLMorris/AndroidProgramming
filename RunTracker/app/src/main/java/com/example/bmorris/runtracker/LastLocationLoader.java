package com.example.bmorris.runtracker;

import android.content.Context;
import android.location.Location;

/**
 * Created by bmorris on 1/8/15.
 */
public class LastLocationLoader extends DataLoader<Location> {
    private long mRunId;

    public LastLocationLoader(Context context, long runId) {
        super(context);
        mRunId = runId;
    }

    @Override
    public Location loadInBackground() {
        return RunManager.get(getContext()).getLastLocationForRun(mRunId);
    }

}
