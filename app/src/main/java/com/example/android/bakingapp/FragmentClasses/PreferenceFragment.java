package com.example.android.bakingapp.FragmentClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;

/**
 * Created by ugochukwu on 6/28/2017.
 */

public class PreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.getDefaultSharedPreferences(getContext()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferencefragmentlayout);

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();

        for(int i = 0; i < count; i++){
            Preference preference = preferenceScreen.getPreference(i);
                setSummary(preference, getContext());
        }
    }

    private void setSummary(Preference preference, Context context){

        if (preference instanceof ListPreference){
            ListPreference listPreference = (ListPreference) preference;
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String s = sharedPreferences.getString(context.getResources().getString(R.string.listPreferencekey), context.getResources().getString(R.string.nutella_pie_key));
            int index = listPreference.findIndexOfValue(s);
            listPreference.setSummary(listPreference.getEntries()[index]);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference preference = findPreference(key);
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            String s = sharedPreferences.getString(key, getContext().getResources().getString(R.string.nutella_pie_key));
            int index = listPreference.findIndexOfValue(s);
            Log.e("checking preference", "ran on preference changed with value " + s);
            listPreference.setSummary(listPreference.getEntries()[index]);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PreferenceManager.getDefaultSharedPreferences(getContext()).unregisterOnSharedPreferenceChangeListener(this);
    }
}
