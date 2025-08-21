package ml.docilealligator.infinityforreddit.settings;

import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;

import ml.docilealligator.infinityforreddit.customviews.preference.CustomFontPreferenceFragmentCompat;

public abstract class SearchablePreferenceFragment extends CustomFontPreferenceFragmentCompat {

    public abstract Integer getPreferenceScreenResId();

        @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, String rootKey) {
        try {
            setPreferencesFromResource(getPreferenceScreenResId(), rootKey);
        } catch (IllegalArgumentException e) {
            // Log the error, and try to load without a rootKey
            // This happens when a non-PreferenceScreen key is passed as rootKey,
            // likely from search results trying to open a specific preference.
            // We'll load the full preference screen and then try to highlight the item.
            setPreferencesFromResource(getPreferenceScreenResId(), null);
            // TODO: In a proper fix, we would then find the preference and scroll to it or highlight it.
            // For now, just loading the full screen is better than crashing.
            // You might want to add a proper logging mechanism here.
            System.err.println("Error loading preferences with rootKey: " + rootKey + ". Loading full screen instead. Error: " + e.getMessage());

            if (rootKey != null) {
                Preference preference = findPreference(rootKey);
                System.err.println("Attempted to find preference with key: " + rootKey + ". Found: " + (preference != null));
                if (preference != null) {
                    scrollToPreference(preference);
                }
            }
        }
    }
}
