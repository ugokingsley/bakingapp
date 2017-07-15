package com.example.android.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.StepActivity;
import com.example.android.bakingapp.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String arrayString) {

        // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String preferenceString = sharedPreferences.getString(context.getResources().getString(R.string.listPreferencekey), context.getResources().getString(R.string.nutella_pie_key));

        String name;

        if (preferenceString == context.getResources().getString(R.string.nutella_pie_key)){
            name = "Nutella Pie";
        }else if (preferenceString == context.getResources().getString(R.string.Brownies_key)){
            name = "Brownies";
        }else if (preferenceString == context.getResources().getString(R.string.yello_cake_key)){
            name ="Yellow Cake";
        }else{
            name = "Cheese Cake";
        }

        views.setTextViewText(R.id.recipeName, name);
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.Linearlayout1, pendingIntent);

            Intent intentService = new Intent(context, WidgetService.class);

            intentService.putExtra("arrayString", arrayString);
            views.setRemoteAdapter(appWidgetId, R.id.listView, intentService);
            views.setPendingIntentTemplate(R.id.listView, pendingIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (final int appWidgetId : appWidgetIds) {

            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            boolean isNetworkAvailable = manager.getActiveNetworkInfo().isAvailable();
            if (isNetworkAvailable == false) {

                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widgeterrorlayout);
                appWidgetManager.updateAppWidget(appWidgetId, views);

            }else {

                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        URL url = NetworkUtils.getUrl();
                        String s = null;

                        try {
                            s = NetworkUtils.getResponseFromHttpUrl(url);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return s;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        updateAppWidget(context, appWidgetManager, appWidgetId, s);
                    }
                }.execute();
            }

        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

