package at.technikum_wien.polzert.newsclassic.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import at.technikum_wien.polzert.newsclassic.R

class SettingsFragment : PreferenceFragmentCompat() {
  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    addPreferencesFromResource(R.xml.pref_general)
  }
}
