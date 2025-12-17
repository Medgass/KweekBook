package com.kweekbook

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Load book data (from database or sample data)
        loadBookDetails(bookId)
    }

    private fun loadBookDetails(bookId: Int) {
        // TODO: Load from database
        // For now, create sample book
        book = Book(
            id = bookId,
            title = "Livre Example",
            author = "Auteur",
            image = "",
            description = "Description du livre",
            year = 2024,
            genre = "Genre",
            pages = 300,
            isbn = "978-XXX",
            rating = 4.5,
            language = "Français",
            publisher = "Éditeur",
            status = "available",
            category = "Catégorie",
            maxBorrowDays = 30,
            totalCopies = 5,
            availableCopies = 5
        )

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
        findViewById<FloatingActionButton>(R.id.fabFavorite).setOnClickListener {
            isFavorite = !isFavorite
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
