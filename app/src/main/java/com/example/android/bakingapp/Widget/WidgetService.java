package com.example.android.bakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.MainActivityAdapter;
import com.example.android.bakingapp.Utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ugochukwu on 6/24/2017.
 */

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(final Intent intent) {
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        return new ListProvider(getApplicationContext(), intent);
    }
    }

