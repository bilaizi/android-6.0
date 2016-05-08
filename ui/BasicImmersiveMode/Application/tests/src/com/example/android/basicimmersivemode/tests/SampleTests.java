package com.example.android.basicimmersivemode.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;

import com.example.android.basicimmersivemode.BasicImmersiveModeFragment;
import com.example.android.basicimmersivemode.MainActivity;

/**
* Tests for immersive mode sample.
*/
public class SampleTests extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mTestActivity;
    private BasicImmersiveModeFragment mTestFragment;
    public SampleTests() {
        super(MainActivity.class);
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Starts the activity under test using the default Intent with:
        // action = {@link Intent#ACTION_MAIN}
        // flags = {@link Intent#FLAG_ACTIVITY_NEW_TASK}
        // All other fields are null or empty.
        mTestActivity = getActivity();
        mTestFragment = (BasicImmersiveModeFragment)
        mTestActivity.getSupportFragmentManager().getFragments().get(1);
    }

    /**
    * Test if the test fixture has been set up correctly.
    */
    public void testPreconditions() {
        //Try to add a message to add context to your assertions. These messages will be shown if
        //a tests fails and make it easy to understand why a test failed
        assertNotNull("mTestActivity is null", mTestActivity);
        assertNotNull("mTestFragment is null", mTestFragment);
    }

    /**
     * Verify that the UI flags actually changed when the toggle method is called.
     */
    @UiThreadTest
    public void testFlagsChanged() {
        int uiFlags = getActivity().getWindow().getDecorView().getSystemUiVisibility();
        mTestFragment.toggleHideyBar();
        int newUiFlags = getActivity().getWindow().getDecorView().getSystemUiVisibility();
        assertTrue("UI Flags didn't toggle.", uiFlags != newUiFlags);
    }

    /**
     * Verify that the view's height actually changed when the toggle method is called.
     * This should result in a change in height for the DecorView.
     */
    public void testDecorHeightExpanded() {
        // Grab the initial height of the DecorWindow.
        int startingHeight = getActivity().getWindow().getDecorView().getHeight();
        // In order to test that this worked:  Need to toggle the immersive mode on the UI thread,
        // wait a suitable amount of time (this test goes with 200 ms), then check to see if the
        // height changed.
        try {
            Runnable testRunnable = (new Runnable() {
                public void run() {
                    // Toggle immersive mode
                    mTestFragment.toggleHideyBar();
                    synchronized(this) {
                        // Notify any thread waiting on this runnable that it can continue
                        this.notify();
                    }
                }
            });
            synchronized(testRunnable) {
                // Since toggling immersive mode makes changes to the view heirarchy, it needs to run
                // on the UI thread, or crashing will occur.
                mTestActivity.runOnUiThread(testRunnable);
                testRunnable.wait();

            }
            synchronized(this) {
                //Wait about 200ms for the change to take place
                wait(200L);
            }
        } catch (Throwable throwable) {
            fail(throwable.getMessage());
        }
        int expandedHeight = getActivity().getWindow().getDecorView().getHeight();
        assertTrue("Bars aren't hidden.", expandedHeight != startingHeight);
    }
}