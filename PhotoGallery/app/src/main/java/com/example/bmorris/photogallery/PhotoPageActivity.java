package com.example.bmorris.photogallery;

import android.support.v4.app.Fragment;

/**
 * Created by bmorris on 1/8/15.
 */
public class PhotoPageActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new PhotoPageFragment();
    }
}
