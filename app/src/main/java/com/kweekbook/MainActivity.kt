package com.kweekbook

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.kweekbook.adapter.BookAdapter
import com.kweekbook.data.BooksData
import com.kweekbook.databinding.ActivityMainBinding
import com.kweekbook.model.Book

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var bookAdapter: BookAdapter
    private var isDarkMode = false
    private var selectedCategory = "Tous"
    private var allBooksCached: List<Book> = emptyList()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            // Setup View Binding
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Erreur d'initialisation", Toast.LENGTH_SHORT).show()
            return
        }

        // Bottom navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_home
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_favorites -> {
                    startActivity(Intent(this, FavoritesActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, UserProfileActivity::class.java))
                    overridePendingTransition(R.anim.slide_up, R.anim.fade_out)
                    finish()
                    true
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }
                else -> false
            }
        }
        
        // Initialize Adapter with sample data
        bookAdapter = BookAdapter(
            onBookClick = { book ->
                val intent = Intent(this, BookDetailActivity::class.java)
                intent.putExtra("BOOK_ID", book.id)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            },
            onBorrowClick = { book ->
                Toast.makeText(this, "Emprunter: ${book.title}", Toast.LENGTH_SHORT).show()
            }
        )
        
        // Setup RecyclerView
        binding.recyclerViewBooks.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = bookAdapter
        }
        
        // Setup SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            
            override fun onQueryTextChange(newText: String?): Boolean {
                filterBooks(newText ?: "")
                return true
            }
        })
        
        // Setup Category Chips
        setupCategoryChips()
        setupFooterReveal()
        
        // Load sample data directly without database
        allBooksCached = BooksData.sampleBooks
        bookAdapter.submitList(allBooksCached)
        updateEmptyState(allBooksCached.isEmpty())
    }
    
    private fun setupCategoryChips() {
        val categories = listOf("Tous", "Classiques", "Philosophie", "Jeunesse", "Poésie")

        val accent = ContextCompat.getColor(this, R.color.accent)
        val accentDark = ContextCompat.getColor(this, R.color.accent_dark)
        val neutral = ContextCompat.getColor(this, R.color.secondary_background)
        val textPrimary = ContextCompat.getColor(this, R.color.text_primary)

        val chipBackground = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
            intArrayOf(accent, neutral)
        )
        val chipTextColors = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
            intArrayOf(ContextCompat.getColor(this, R.color.white), textPrimary)
        )
        val chipStroke = ColorStateList.valueOf(accentDark)

        categories.forEach { category ->
            val chip = Chip(this).apply {
                text = category
                isCheckable = true
                isChecked = category == "Tous"
                chipBackgroundColor = chipBackground
                setTextColor(chipTextColors)
                rippleColor = ColorStateList.valueOf(accentDark)
                setEnsureMinTouchTargetSize(false)
                setPadding(24, 12, 24, 12)
                chipStrokeWidth = 1f
                chipStrokeColor = chipStroke
                isChipIconVisible = false
                setOnClickListener {
                    selectedCategory = category
                    filterBooks(binding.searchView.query.toString())
                }
            }
            binding.categoryChipGroup.addView(chip)
        }
    }

    private fun setupFooterReveal() {
        val footer = findViewById<View>(R.id.footerMain) ?: return
        val recyclerView = binding.recyclerViewBooks

        fun updateFooterVisibility() {
            val atBottom = !recyclerView.canScrollVertically(1)
            footer.visibility = if (atBottom) View.VISIBLE else View.GONE
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                updateFooterVisibility()
            }
        })
        recyclerView.post { updateFooterVisibility() }
    }
    
    private fun filterBooks(query: String) {
        var filtered = allBooksCached
        if (selectedCategory != "Tous") {
            filtered = filtered.filter { it.category == selectedCategory }
        }
        if (query.isNotEmpty()) {
            filtered = filtered.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.author.contains(query, ignoreCase = true) ||
                it.genre.contains(query, ignoreCase = true)
            }
        }
        bookAdapter.submitList(filtered)
        updateEmptyState(filtered.isEmpty())
    }
    
    private fun updateEmptyState(isEmpty: Boolean) {
        binding.emptyStateLayout.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.recyclerViewBooks.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }
    
    private fun showLoading(show: Boolean) {
        binding.loadingIndicator.visibility = if (show) View.VISIBLE else View.GONE
        binding.recyclerViewBooks.visibility = if (show) View.GONE else View.VISIBLE
        binding.emptyStateLayout.visibility = View.GONE
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                val intent = Intent(this, UserProfileActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_up, R.anim.fade_out)
                true
            }
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                true
            }
            R.id.action_notifications -> {
                startActivity(Intent(this, NotificationsActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                true
            }
            R.id.action_favorites -> {
                startActivity(Intent(this, FavoritesActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                true
            }
            R.id.action_borrow_history -> {
                startActivity(Intent(this, BorrowHistoryActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                true
            }
            R.id.action_dark_mode -> {
                toggleDarkMode()
                true
            }
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun toggleDarkMode() {
        isDarkMode = !isDarkMode
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
    
    private fun logout() {
        getSharedPreferences("KweekBookPrefs", MODE_PRIVATE).edit().apply {
            putBoolean("is_logged_in", false)
            apply()
        }
        
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}
