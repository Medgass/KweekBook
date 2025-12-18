package com.kweekbook

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kweekbook.adapter.BookAdapter
import com.kweekbook.data.BooksData
import com.kweekbook.model.Book

class FavoritesActivity : AppCompatActivity() {
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        // Gestion du bouton retour
        findViewById<View>(R.id.buttonBack)?.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_favorites
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (this !is MainActivity) {
                        startActivity(Intent(this, MainActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    }
                    true
                }
                R.id.nav_favorites -> true
                R.id.nav_profile -> {
                    if (this !is UserProfileActivity) {
                        startActivity(Intent(this, UserProfileActivity::class.java))
                        overridePendingTransition(R.anim.slide_up, R.anim.fade_out)
                    }
                    true
                }
                R.id.nav_settings -> {
                    if (this !is SettingsActivity) {
                        startActivity(Intent(this, SettingsActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
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
        // Mise à jour de l'adaptateur avec les 4 paramètres requis
        adapter = BookAdapter(
            onBookClick = { book ->
                val intent = Intent(this, BookDetailActivity::class.java)
                intent.putExtra("BOOK_ID", book.id)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            },
            onBorrowClick = { book ->
                toggleBorrow(book)
            },
            onReserveClick = { book ->
                toggleReserve(book)
            }
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

        // Correction de l'ID pour correspondre au nouveau XML (emptyFavorites)
        val emptyView = findViewById<View>(R.id.emptyFavorites)
        val rv = findViewById<RecyclerView>(R.id.favoritesRecyclerView)
        
        if (favorites.isEmpty()) {
            emptyView?.visibility = View.VISIBLE
            rv?.visibility = View.GONE
        } else {
            emptyView?.visibility = View.GONE
            rv?.visibility = View.VISIBLE
        }
        adapter.submitList(favorites)
    }

    private fun toggleBorrow(book: com.kweekbook.model.Book) {
        val prefs = getSharedPreferences("KweekBookPrefs", MODE_PRIVATE)
        val borrowed = prefs.getStringSet("borrowed_ids", emptySet())?.toMutableSet() ?: mutableSetOf()
        if (borrowed.contains(book.id.toString())) {
            borrowed.remove(book.id.toString())
            Toast.makeText(this, "Retour: ${book.title}", Toast.LENGTH_SHORT).show()
        } else {
            borrowed.add(book.id.toString())
            Toast.makeText(this, "Emprunté: ${book.title}", Toast.LENGTH_SHORT).show()
        }
        prefs.edit().putStringSet("borrowed_ids", borrowed).apply()
        loadFavorites()
    }

    private fun toggleReserve(book: com.kweekbook.model.Book) {
        val prefs = getSharedPreferences("KweekBookPrefs", MODE_PRIVATE)
        val reserved = prefs.getStringSet("reserved_ids", emptySet())?.toMutableSet() ?: mutableSetOf()
        if (reserved.contains(book.id.toString())) {
            reserved.remove(book.id.toString())
            Toast.makeText(this, "Annulé: ${book.title}", Toast.LENGTH_SHORT).show()
        } else {
            reserved.add(book.id.toString())
            Toast.makeText(this, "Réservé: ${book.title}", Toast.LENGTH_SHORT).show()
        }
        prefs.edit().putStringSet("reserved_ids", reserved).apply()
        loadFavorites()
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
