package com.kweekbook

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kweekbook.data.BooksData
import com.kweekbook.model.Book

class BookDetailActivity : AppCompatActivity() {

    private lateinit var book: Book
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        // Get book from intent
        val bookId = intent.getIntExtra("BOOK_ID", -1)
        if (bookId == -1) {
            finish()
            return
        }

        // No toolbar (header removed)

        // Load book data from in-memory dataset
        loadBookDetails(bookId)
    }

    private fun loadBookDetails(bookId: Int) {
        val found = BooksData.sampleBooks.firstOrNull { it.id == bookId }
        if (found == null) {
            finish()
            return
        }
        book = found

        // Initialize favorite state from SharedPreferences
        val prefs = getSharedPreferences("KweekBookPrefs", MODE_PRIVATE)
        val favIds = prefs.getStringSet("favorite_ids", emptySet()) ?: emptySet()
        isFavorite = favIds.contains(book.id.toString())

        displayBookDetails()
    }

    private fun displayBookDetails() {
        findViewById<ImageView>(R.id.bookImage).apply {
            Glide.with(this@BookDetailActivity)
                .load(book.image)
                .placeholder(R.drawable.ic_placeholder)
                .into(this)
        }

        findViewById<TextView>(R.id.bookTitle).text = book.title
        findViewById<TextView>(R.id.bookAuthor).text = book.author
        findViewById<TextView>(R.id.bookRating).text = book.rating.toString()
        findViewById<TextView>(R.id.bookDescription).text = book.description
        findViewById<TextView>(R.id.bookYear).text = book.year.toString()
        findViewById<TextView>(R.id.bookPages).text = "${book.pages} pages"
        findViewById<TextView>(R.id.bookLanguage).text = book.language
        findViewById<TextView>(R.id.bookPublisher).text = book.publisher
        findViewById<TextView>(R.id.bookISBN).text = book.isbn
        findViewById<TextView>(R.id.availableCopies).text = "${book.availableCopies}/${book.totalCopies}"

        // Status badge
        val statusText = findViewById<TextView>(R.id.statusText)
        val statusIcon = findViewById<ImageView>(R.id.statusIcon)
        when (book.status) {
            "available" -> {
                statusText.text = "Disponible"
                statusIcon.setImageResource(R.drawable.ic_check)
            }
            "borrowed" -> {
                statusText.text = "Emprunté"
                statusIcon.setImageResource(R.drawable.ic_clock)
            }
            "reserved" -> {
                statusText.text = "Réservé"
                statusIcon.setImageResource(R.drawable.ic_clock)
            }
        }

        // Genre and category chips
        findViewById<TextView>(R.id.genreChip).text = book.genre
        findViewById<TextView>(R.id.categoryChip).text = book.category

        // Favorite button
        val fab = findViewById<FloatingActionButton>(R.id.fabFavorite)
        updateFavoriteIcon()
        fab.setOnClickListener {
            isFavorite = !isFavorite
            persistFavoriteState(isFavorite)
            updateFavoriteIcon()
            Toast.makeText(this, if (isFavorite) "Ajouté aux favoris" else "Retiré des favoris", Toast.LENGTH_SHORT).show()
        }

        // Borrow button
        findViewById<MaterialButton>(R.id.borrowButton).apply {
            isEnabled = book.status == "available"
            text = when (book.status) {
                "available" -> "Emprunter"
                "borrowed" -> "Emprunté"
                else -> "Réservé"
            }
            setOnClickListener {
                Toast.makeText(this@BookDetailActivity, "Emprunter: ${book.title}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateFavoriteIcon() {
        findViewById<FloatingActionButton>(R.id.fabFavorite).setImageResource(
            if (isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart
        )
    }

    private fun persistFavoriteState(favorite: Boolean) {
        val prefs = getSharedPreferences("KweekBookPrefs", MODE_PRIVATE)
        val current = prefs.getStringSet("favorite_ids", emptySet())?.toMutableSet() ?: mutableSetOf()
        if (favorite) current.add(book.id.toString()) else current.remove(book.id.toString())
        prefs.edit().putStringSet("favorite_ids", current).apply()
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
