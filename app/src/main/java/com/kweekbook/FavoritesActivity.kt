package com.kweekbook

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kweekbook.adapter.BookAdapter
import com.kweekbook.data.BooksData
import com.kweekbook.model.Book
import androidx.recyclerview.widget.RecyclerView
import android.view.View

class FavoritesActivity : AppCompatActivity() {
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_favorites
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    finish()
                    true
                }
                R.id.nav_favorites -> true
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

        setupRecycler()
        loadFavorites()
    }

    override fun onResume() {
        super.onResume()
        loadFavorites()
    }

    private fun setupRecycler() {
        val rv = findViewById<RecyclerView>(R.id.favoritesRecyclerView)
        adapter = BookAdapter(
            onBookClick = { book ->
                val intent = Intent(this, BookDetailActivity::class.java)
                intent.putExtra("BOOK_ID", book.id)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            },
            onBorrowClick = { /* no-op on favorites for now */ }
        )
        rv.layoutManager = GridLayoutManager(this, 2)
        rv.adapter = adapter
    }

    private fun loadFavorites() {
        val prefs = getSharedPreferences("KweekBookPrefs", MODE_PRIVATE)
        val favIds = prefs.getStringSet("favorite_ids", emptySet()) ?: emptySet()
        val favIdInts = favIds.mapNotNull { it.toIntOrNull() }.toSet()
        val favorites: List<Book> = applyStatusOverrides(
            BooksData.sampleBooks.filter { favIdInts.contains(it.id) }
        )

        val emptyView = findViewById<View>(R.id.emptyFavorites)
        val rv = findViewById<RecyclerView>(R.id.favoritesRecyclerView)
        if (favorites.isEmpty()) {
            emptyView.visibility = View.VISIBLE
            rv.visibility = View.GONE
        } else {
            emptyView.visibility = View.GONE
            rv.visibility = View.VISIBLE
        }
        adapter.submitList(favorites)
    }

    private fun applyStatusOverrides(list: List<Book>): List<Book> {
        val prefs = getSharedPreferences("KweekBookPrefs", MODE_PRIVATE)
        val borrowed = prefs.getStringSet("borrowed_ids", emptySet()) ?: emptySet()
        val reserved = prefs.getStringSet("reserved_ids", emptySet()) ?: emptySet()
        return list.map { b ->
            val status = when {
                borrowed.contains(b.id.toString()) -> "borrowed"
                reserved.contains(b.id.toString()) -> "reserved"
                else -> b.status
            }
            val available = if (status == "borrowed") 0 else b.availableCopies
            b.copy(status = status, availableCopies = available)
        }
    }
}
