package com.example.a3673605.myapplication;


import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class PrefFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.userpreferences);

    }



}
