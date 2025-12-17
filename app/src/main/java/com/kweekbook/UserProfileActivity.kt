package com.kweekbook

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Mon Profil"

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_profile
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    true
                }
                R.id.nav_favorites -> {
                    startActivity(Intent(this, FavoritesActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    true
                }
                R.id.nav_profile -> true
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    true
                }
                else -> false
            }
        }

        // Get user data
        val sharedPrefs = getSharedPreferences("KweekBookPrefs", MODE_PRIVATE)
        val userName = sharedPrefs.getString("user_name", "Utilisateur") ?: "Utilisateur"
        val userEmail = sharedPrefs.getString("user_email", "") ?: ""

        // Display user info
        findViewById<TextView>(R.id.userName).text = userName
        findViewById<TextView>(R.id.userEmail).text = userEmail

        // Stats
        findViewById<TextView>(R.id.borrowedCount).text = "0"
        findViewById<TextView>(R.id.favoritesCount).text = "0"
        findViewById<TextView>(R.id.reviewsCount).text = "0"

        // Logout button
        findViewById<MaterialButton>(R.id.logoutButton).setOnClickListener {
            logout()
        }
        
        // Apply fade in animation
        window.decorView.startAnimation(android.view.animation.AnimationUtils.loadAnimation(this, R.anim.fade_in))

        // Edit Profile button
        findViewById<MaterialButton>(R.id.editProfileButton).setOnClickListener {
            // TODO: Implement edit profile
        }

        // Quick links
        findViewById<MaterialButton>(R.id.btnSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        findViewById<MaterialButton>(R.id.btnNotifications).setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        findViewById<MaterialButton>(R.id.btnFavorites).setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        findViewById<MaterialButton>(R.id.btnBorrowHistory).setOnClickListener {
            startActivity(Intent(this, BorrowHistoryActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun logout() {
        val sharedPrefs = getSharedPreferences("KweekBookPrefs", MODE_PRIVATE)
        sharedPrefs.edit().apply {
            putBoolean("is_logged_in", false)
            apply()
        }

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
