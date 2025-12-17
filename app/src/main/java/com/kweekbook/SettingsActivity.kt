package com.kweekbook

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_settings
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    finish()
                    true
                }
                R.id.nav_favorites -> {
                    startActivity(Intent(this, FavoritesActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    finish()
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, UserProfileActivity::class.java))
                    overridePendingTransition(R.anim.slide_up, R.anim.fade_out)
                    finish()
                    true
                }
                R.id.nav_settings -> true
                else -> false
            }
        }

        // Dark mode toggle (persist + apply)
        val darkModeSwitch = findViewById<SwitchCompat>(R.id.darkModeSwitch)
        val prefs = getSharedPreferences("KweekBookPrefs", MODE_PRIVATE)
        val isDarkSaved = prefs.getBoolean("dark_mode", false)
        darkModeSwitch.isChecked = isDarkSaved

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            // Ensure immediate UI update for this screen
            recreate()
        }
    }
}
