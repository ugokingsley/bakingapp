package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.bakingapp.FragmentClasses.CakeRecipeFragment;
import com.example.android.bakingapp.FragmentClasses.VideoFragment;

/**
 * Created by ugochukwu on 6/24/2017.
 */

public class StepActivity extends AppCompatActivity {

    private int Position;
    ActionBar actionBar;
    private CakeRecipeFragment cakeRecipeFragment;
    private VideoFragment videoFragment;
    String mDescription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steplayout);

        FragmentManager fragmentManager = getSupportFragmentManager();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        cakeRecipeFragment = new CakeRecipeFragment();
        videoFragment = new VideoFragment();

        //if framelayout is not null, then device is a tablet. so we place the videofragment in the framelayout
        if (findViewById(R.id.videofragment) != null){
            fragmentManager.beginTransaction().add(R.id.videofragment, videoFragment).commit();
        }

        //Retrieve all passed data from the intent as Bundle
        Intent intent = getIntent();
        Bundle bundle = new Bundle();

        if (intent.hasExtra("position") && intent.hasExtra("name")) {
            bundle = intent.getExtras();
            Position = bundle.getInt("position");
            actionBar.setTitle(bundle.getString("name"));
        }

        //if videoFragment is already added to the activity, do not add cakeRecipeFragment
        if (savedInstanceState != null){
            boolean b = savedInstanceState.getBoolean("isFragmentAdded");
            if (b == true){
                //do something
                actionBar.setTitle(mDescription);
            }
        }else {

            cakeRecipeFragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(R.id.cakerecipefragment, cakeRecipeFragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }else if (item.getItemId() == R.id.settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    //handles each recyclerview click from the Cake recipe fragment
    public void onClickSteps(String Description, String videoURL, String sDescription, String thumbnailUrl) {

        VideoFragment videoFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        mDescription = sDescription;
        bundle.putString("description", Description);
        bundle.putString("videoURL", videoURL);
        bundle.putString("thumbnailUrl", thumbnailUrl);

        if (bundle != null) {
            if (!isFinishing()) {

                videoFragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                //if framelayout is not null, then device is a tablet.
                // so we replace initial video fragment with a new one carrying data as argument
                if (findViewById(R.id.videofragment) != null){
                    transaction.replace(R.id.videofragment, videoFragment);
                    transaction.commit();
                }else {
                    transaction.replace(R.id.cakerecipefragment, videoFragment).addToBackStack(null);
                    transaction.commit();
                }

            }
        }

        actionBar.setTitle(sDescription);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        boolean isFragmentAdded = videoFragment.isAdded();
            outState.putBoolean("isFragmentAdded", isFragmentAdded);
        }
}
