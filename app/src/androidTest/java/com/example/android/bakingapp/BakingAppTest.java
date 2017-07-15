package com.example.android.bakingapp;

import android.support.annotation.Nullable;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicBoolean;

import static android.support.test.espresso.Espresso.onData;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Created by AGWU SMART ELEZUO on 6/27/2017.
 */
@RunWith(AndroidJUnit4.class)
public class BakingAppTest {

    private ProgressIdlingResource resource;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onClickRecyclerViewItem() {

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.textView)).check(matches(withText(R.string.ingredientlabel)));
    }

    @Test
    public void checkIfIngerdientListIsDesplayed() {

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycle)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfStepListIsDesplayed() {

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (resource != null) {
            Espresso.unregisterIdlingResources(resource);
        }
    }
}
