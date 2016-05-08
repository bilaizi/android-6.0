package com.example.android.basicaccessibility;

import android.app.Activity;
import android.os.Bundle;

/**
 * Basic activity class.
 *
 * <p>Responsible for rendering layout, and displaying some toasts to give buttons feedback.
 * There's nothing terribly interesting in this class. All the interesting stuff is in
 * res/layout/activity_main.xml and {@link DialView}.
 */
public class MainActivity extends Activity {

    /**
     * Standard onCreate() implementation. Sets R.layout.activity_main as the layout.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_main);
    }
}
