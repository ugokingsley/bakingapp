package com.example.android.bakingapp;

import android.support.test.espresso.IdlingResource;

/**
 * Created by AGWU SMART ELEZUO on 6/29/2017.
 */

public class ProgressIdlingResource implements IdlingResource {

    private ResourceCallback resourceCallback;
    private MainActivity mainActivity;

    public ProgressIdlingResource() {
        mainActivity = new MainActivity();
    }

    @Override
    public String getName() {
        return ProgressIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {

        return false;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;

        if (isIdleNow() && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
    }
}
