package com.kweekbook

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.kweekbook.adapter.BookAdapter
import com.kweekbook.data.BooksData
import com.kweekbook.model.Book

class UserProfileActivity : AppCompatActivity() {

    private lateinit var favoritesAdapter: BookAdapter
    private lateinit var historyAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_user_profile)
            setupBottomNavigation()
            setupUserData()
            setupAdapters()
            loadData()
            setupButtons()
        } catch (e: Exception) {
            android.util.Log.e("KweekBook", "Crash in UserProfileActivity: ${e.message}", e)
            Toast.makeText(this, "Erreur lors de l'ouverture du profil", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun setupButtons() {
        // Retour
        findViewById<View>(R.id.buttonBack)?.setOnClickListener { 
            onBackPressed() 
        }

        // Déconnexion
        findViewById<View>(R.id.logoutButton)?.setOnClickListener { 
            logout() 
        }

        // Voir tout l'historique (désactivé, vue absente)
        // findViewById<View>(R.id.viewAllHistory)?.setOnClickListener {
        //     startActivity(Intent(this, BorrowHistoryActivity::class.java))
        //     overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        // }
        // findViewById<View>(R.id.statsBorrowedCard)?.setOnClickListener {
        //     startActivity(Intent(this, BorrowHistoryActivity::class.java))
        // }
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation) ?: return
        bottomNav.selectedItemId = R.id.nav_profile
        bottomNav.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.nav_profile) {
                // Déjà sur le profil, ne rien faire
                return@setOnItemSelectedListener true
            }
            when (item.itemId) {
                R.id.nav_home -> {
                    if (this !is MainActivity) {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    true
                }
                R.id.nav_favorites -> {
                    if (this !is FavoritesActivity) {
                        startActivity(Intent(this, FavoritesActivity::class.java))
                    }
                    true
                }
                R.id.nav_settings -> {
                    if (this !is SettingsActivity) {
                        startActivity(Intent(this, SettingsActivity::class.java))
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun setupUserData() {
        val sharedPrefs = getSharedPreferences("KweekBookPrefs", MODE_PRIVATE)
        val userName = sharedPrefs.getString("user_name", "Utilisateur") ?: "Utilisateur"
        val userEmail = sharedPrefs.getString("user_email", "email@example.com") ?: "email@example.com"

        // Affichage
        findViewById<TextView>(R.id.userName)?.text = userName
        findViewById<TextView>(R.id.userEmail)?.text = userEmail

        // Edition
        val editName = findViewById<TextInputEditText>(R.id.editName)
        val editEmail = findViewById<TextInputEditText>(R.id.editEmail)
        editName?.setText(userName)
        editEmail?.setText(userEmail)

        findViewById<MaterialButton>(R.id.btnSaveProfile)?.setOnClickListener {
            val newName = editName?.text.toString().trim()
            val newEmail = editEmail?.text.toString().trim()

            if (newName.isEmpty() || newEmail.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sharedPrefs.edit().apply {
                putString("user_name", newName)
                putString("user_email", newEmail)
                apply()
            }

            findViewById<TextView>(R.id.userName)?.text = newName
            findViewById<TextView>(R.id.userEmail)?.text = newEmail
            Toast.makeText(this, "Profil mis à jour !", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupAdapters() {
        val actionClick = { book: Book -> 
            val intent = Intent(this, BookDetailActivity::class.java).apply { putExtra("BOOK_ID", book.id) }
            startActivity(intent)
        }
        favoritesAdapter = BookAdapter(actionClick, { book ->
            toggleBorrow(book)
        }, { book ->
            toggleReserve(book)
        })

        findViewById<RecyclerView>(R.id.favoritesRecyclerView)?.apply {
            layoutManager = GridLayoutManager(this@UserProfileActivity, 2)
            adapter = favoritesAdapter
        }

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
        loadData()
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
        loadData()
    }

    private fun loadData() {
        val sharedPrefs = getSharedPreferences("KweekBookPrefs", MODE_PRIVATE)
        val favIds = sharedPrefs.getStringSet("favorite_ids", emptySet()) ?: emptySet()
        val favBooks = BooksData.sampleBooks.filter { favIds.contains(it.id.toString()) }

        // Mettre à jour la liste des favoris
        favoritesAdapter.submitList(favBooks.take(4))

        // Mettre à jour les compteurs
        findViewById<TextView>(R.id.favoritesCount)?.text = favBooks.size.toString()
        // findViewById<TextView>(R.id.borrowedCount)?.text = histBooks.size.toString() // désactivé
        findViewById<TextView>(R.id.reviewsCount)?.text = "0"

        // Gérer les états vides
        findViewById<View>(R.id.emptyState)?.visibility = if (favBooks.isEmpty()) View.VISIBLE else View.GONE
        // findViewById<View>(R.id.emptyHistoryState)?.visibility = if (histBooks.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun logout() {
        getSharedPreferences("KweekBookPrefs", MODE_PRIVATE).edit().putBoolean("is_logged_in", false).apply()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        // Guard against platform-level SecurityException when a receiver is registered during start
        try {
            super.onStart()
        } catch (e: SecurityException) {
            android.util.Log.e("KweekBook", "SecurityException in onStart (receiver registration): ${e.message}", e)
            // Don't rethrow: avoid crashing the activity when device enforces receiver exported flags
        } catch (e: Exception) {
            android.util.Log.e("KweekBook", "Exception in onStart: ${e.message}", e)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
