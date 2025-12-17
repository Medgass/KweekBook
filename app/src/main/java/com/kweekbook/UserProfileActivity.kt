package com.kweekbook

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.kweekbook.adapter.BookAdapter
import com.kweekbook.data.BooksData
import com.kweekbook.model.Book

class UserProfileActivity : AppCompatActivity() {

    private lateinit var favoritesAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_profile
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
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }
                R.id.nav_profile -> true
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
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
        val favCount = (sharedPrefs.getStringSet("favorite_ids", emptySet()) ?: emptySet()).size
        findViewById<TextView>(R.id.favoritesCount).text = favCount.toString()
        findViewById<TextView>(R.id.reviewsCount).text = "0"

        // Setup favorites preview list
        setupFavoritesPreview()
        loadFavoritesPreview()

        // Set up edit fields
        val editName = findViewById<TextInputEditText>(R.id.editName)
        val editEmail = findViewById<TextInputEditText>(R.id.editEmail)
        editName.setText(userName)
        editEmail.setText(userEmail)

        // Save changes
        val btnSaveProfile = findViewById<MaterialButton>(R.id.btnSaveProfile)
        btnSaveProfile.setOnClickListener {
            val newName = editName.text.toString().trim()
            val newEmail = editEmail.text.toString().trim()

            if (newName.isEmpty() || newEmail.isEmpty()) {
                Toast.makeText(this, "Tous les champs sont requis", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sharedPrefs.edit().apply {
                putString("user_name", newName)
                putString("user_email", newEmail)
                apply()
            }

            findViewById<TextView>(R.id.userName).text = newName
            findViewById<TextView>(R.id.userEmail).text = newEmail
            Toast.makeText(this, "Profil mis à jour!", Toast.LENGTH_SHORT).show()
        }

        // Logout button
        findViewById<MaterialButton>(R.id.logoutButton).setOnClickListener {
            logout()
        }

        // Back button (simple finish)
        findViewById<MaterialButton>(R.id.buttonBack).setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        
        // Apply fade in animation
        window.decorView.startAnimation(android.view.animation.AnimationUtils.loadAnimation(this, R.anim.fade_in))
    }

    override fun onResume() {
        super.onResume()
        // Refresh counts and preview when coming back
        val sharedPrefs = getSharedPreferences("KweekBookPrefs", MODE_PRIVATE)
        val favCount = (sharedPrefs.getStringSet("favorite_ids", emptySet()) ?: emptySet()).size
        findViewById<TextView>(R.id.favoritesCount).text = favCount.toString()
        loadFavoritesPreview()
    }

    private fun setupFavoritesPreview() {
        val rv = findViewById<RecyclerView>(R.id.favoritesRecyclerView)
        favoritesAdapter = BookAdapter(
            onBookClick = { book ->
                val intent = android.content.Intent(this, BookDetailActivity::class.java)
                intent.putExtra("BOOK_ID", book.id)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            },
            onBorrowClick = { /* no-op in profile preview */ }
        )
        rv.layoutManager = GridLayoutManager(this, 2)
        rv.adapter = favoritesAdapter
    }

    private fun loadFavoritesPreview() {
        val prefs = getSharedPreferences("KweekBookPrefs", MODE_PRIVATE)
        val favIds = prefs.getStringSet("favorite_ids", emptySet()) ?: emptySet()
        val idSet = favIds.mapNotNull { it.toIntOrNull() }.toSet()
        val favorites: List<Book> = BooksData.sampleBooks.filter { idSet.contains(it.id) }

        val empty = findViewById<View>(R.id.emptyState)
        val rv = findViewById<RecyclerView>(R.id.favoritesRecyclerView)
        if (favorites.isEmpty()) {
            empty.visibility = View.VISIBLE
            rv.visibility = View.GONE
        } else {
            empty.visibility = View.GONE
            rv.visibility = View.VISIBLE
        }
        // Limit preview to 4 items to keep layout tidy
        favoritesAdapter.submitList(favorites.take(4))
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
