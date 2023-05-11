package at.technikum_wien.polzert.newsclassic.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import at.technikum_wien.polzert.newsclassic.R
import at.technikum_wien.polzert.newsclassic.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val showImagesCheckbox = findViewById<CheckBox>(R.id.checkBoxShowImages)
        val newsFeedUrlEditText = findViewById<EditText>(R.id.editTextNewsFeedUrl)
        val applySettingsButton = findViewById<Button>(R.id.btnApplySettings)

        val sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)
        showImagesCheckbox.isChecked = sharedPreferences.getBoolean("ShowImages", true)
        newsFeedUrlEditText.setText(sharedPreferences.getString("NewsFeedUrl", ""))

        applySettingsButton.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putBoolean("ShowImages", showImagesCheckbox.isChecked)
            editor.putString("NewsFeedUrl", newsFeedUrlEditText.text.toString())
            editor.apply()

            finish()
        }
    }
}
