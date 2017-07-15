package com.example.android.bakingapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingapp.Utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONArray>, MainActivityAdapter.ClickRecyclerView{

    private RecyclerView mRecyclerView;
    private MainActivityAdapter adapter;
    private static final int LOADERMANAGER_ID = 1;
    private ProgressBar mProgressBar;
    public boolean progress;
    private TextView mTextView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerviewlayout);

        //find all views by id in recyclerviewlayout
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTextView= (TextView) findViewById(R.id.errorText);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        //use the specified tool bar as our action bar
        setSupportActionBar(mToolbar);

        //initialise the adapter for the recyclerview
        adapter = new MainActivityAdapter(this, this);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);

        //Check for internet connectivity before initializing the loader with loadermanager
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null  && connectivityManager.getActiveNetworkInfo().isConnected()){
            getSupportLoaderManager().initLoader(LOADERMANAGER_ID, null, this);
        }else {
            onError();
            mTextView.setText("No internet Connection on device!");
        }

    }

    public void onError(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Start loading for data
    @Override
    public Loader<JSONArray> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<JSONArray>(this) {

            JSONArray mArray;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                if (mArray != null){
                    deliverResult(mArray);
                }else {

                    mProgressBar.setVisibility(View.VISIBLE);
                    progress = true;
                    forceLoad();
                }
            }

            @Override
            public JSONArray loadInBackground() {
                URL mUrl = NetworkUtils.getUrl();
                String jsonString = null;
                JSONArray cakeArray = null;

                try {
                    jsonString = NetworkUtils.getResponseFromHttpUrl(mUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    cakeArray = new JSONArray(jsonString);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return cakeArray;
            }

            @Override
            public void deliverResult(JSONArray data) {
                super.deliverResult(data);
                mArray = data;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<JSONArray> loader, JSONArray data) {

        mProgressBar.setVisibility(View.INVISIBLE);
        progress = false;
        if (data == null){
            onError();
            mTextView.setText("No result Retrieved!");
        }else {
            //send our retrieved data to the recyclerview's adapter
            adapter.swapValue(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<JSONArray> loader) {

        getSupportLoaderManager().initLoader(LOADERMANAGER_ID, null, this);
    }

    //Method that handles each recyclerview item click
    @Override
    public void onClick(int position, String name) {

        Intent intent = new Intent(this, StepActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("name", name);
        startActivity(intent);

        }

        public boolean getProgress(){
            return progress;
        }
    }

