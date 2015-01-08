package com.example.bmorris.draganddraw;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class DragAndDrawActivity extends SingleFragmentActivity {

    @Override
     public Fragment createFragment() {
        return new DragAndDrawFragment();
    }

}
