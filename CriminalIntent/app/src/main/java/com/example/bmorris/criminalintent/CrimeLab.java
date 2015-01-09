package com.example.bmorris.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Singleton factory for crimes
 * Handles loading and serializing/saving crimes
 * Created by bmorris on 1/5/15.
 */
public class CrimeLab {
    //String constants for debug and crime storage
    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";

    //array list of crimes
    private ArrayList<Crime> mCrimes;
    //Serializer used to convert crimes to JSON format
    private CriminalIntentJSONSerializer mSerializer;

    //private single isntance of crime
    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    //Private constructor forces user to use get(Context)
    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);

        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (Exception e) {
            mCrimes = new ArrayList<Crime>();
            Log.e(TAG, "Error loading crimes: ", e);
        }
    }

    //Get method used to access the singleton
    public static CrimeLab get(Context c) {
        if(sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    //Getter for the crime arraylist
    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    //Getter for a crime, fetches from the list by id
    public Crime getCrime(UUID id) {
        for(Crime c : mCrimes) {
            if(c.getId().equals(id))
                return c;
        }
        return null;
    }

    //Adds a crime to the list
    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    //removes crime from the list
    public void deleteCrime(Crime c) {
        mCrimes.remove(c);
    }

    //Serializes and saves teh crimelist
    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving crimes: ", e);
            return false;
        }
    }
}
