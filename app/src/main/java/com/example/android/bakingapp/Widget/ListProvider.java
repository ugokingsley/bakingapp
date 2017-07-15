package com.example.android.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.example.android.bakingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ugochukwu on 6/24/2017.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private JSONArray  mArray;
    private JSONArray ingredient;
    private String recipeName;

    public ListProvider(Context context, Intent i){
        mContext = context;
        if (i.hasExtra("arrayString")) {
            String s = i.getStringExtra("arrayString");

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            String preferenceString = sharedPreferences.getString(mContext.getResources().getString(R.string.listPreferencekey), mContext.getResources().getString(R.string.nutella_pie_key));

            int Position;

            if (preferenceString == mContext.getResources().getString(R.string.nutella_pie_key)){
                Position = 0;
            }else if (preferenceString == mContext.getResources().getString(R.string.Brownies_key)){
                Position = 1;
            }else if (preferenceString == mContext.getResources().getString(R.string.yello_cake_key)){
                Position = 2;
            }else{
                Position = 3;
            }

            try {
                JSONArray jsonArray = new JSONArray(s);
                mArray = jsonArray;

                JSONObject ingredientObject = mArray.getJSONObject(Position);
                recipeName = ingredientObject.getString("name");
                ingredient = ingredientObject.getJSONArray("ingredients");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredient == null) return 0;
        return ingredient.length();
    }

    @Override
    public RemoteViews getViewAt(int position) {
       RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widgetlistitem);

        String Ingredient = null;
        int quantity = 0;
        String measure = null;
        try {
            JSONObject object = ingredient.getJSONObject(position);

            Ingredient = object.getString("ingredient");
            quantity = object.getInt("quantity");
            measure = object.getString("measure");

        } catch (JSONException e) {
            e.printStackTrace();
        }
            views.setTextViewText(R.id.measure, measure);
            views.setTextViewText(R.id.quantity, Integer.toString(quantity));
            views.setTextViewText(R.id.ingerdient, Ingredient);

        Intent intent = new Intent();
        intent.putExtra("position", position);
        views.setOnClickFillInIntent(R.id.cakeType, intent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
