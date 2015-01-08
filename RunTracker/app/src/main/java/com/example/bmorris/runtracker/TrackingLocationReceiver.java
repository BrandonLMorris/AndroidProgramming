package com.example.bmorris.runtracker;

import android.content.Context;
import android.location.Location;

/**
 * Created by bmorris on 1/8/15.
 */
public class TrackingLocationReceiver extends LocationReceiver {

    @Override
    protected void onLocationReceived(Context c, Location loc) {
        RunManager.get(c).insertLocation(loc);
    }
}
