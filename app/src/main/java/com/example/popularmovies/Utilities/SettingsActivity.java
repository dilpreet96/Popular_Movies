package com.example.popularmovies.Utilities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.popularmovies.R;
import com.example.popularmovies.UI.MainActivity;

/**
 * Created by dilpreet on 24/2/16.
 */
public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new PrefFrag()).commit();
    }
    public static class PrefFrag extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting_activity);

            bindPreference(findPreference(getString(R.string.sort_key)));
        }

        private void bindPreference(Preference preference){
            preference.setOnPreferenceChangeListener(this);
            onPreferenceChange(preference, PreferenceManager
                                            .getDefaultSharedPreferences(preference.getContext())
                                            .getString(preference.getKey(), ""));
        }
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String value=newValue.toString();
            if(preference instanceof ListPreference){
                ListPreference listPreference=(ListPreference)preference;
                int index=listPreference.findIndexOfValue(value);

                try {
                    String oldSUm = listPreference.getSummary().toString();
                    listPreference.setSummary(listPreference.getEntries()[index]);
                    String newSUm = listPreference.getSummary().toString();

                    if (newSUm.compareToIgnoreCase(oldSUm) != 0)
                        MainActivity.restored = false;
                }catch (NullPointerException e){
                    listPreference.setSummary(listPreference.getEntries()[index]);
                }

            }
            return true;
        }
    }
}
