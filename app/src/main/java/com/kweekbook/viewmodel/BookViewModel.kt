package com.kweekbook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kweekbook.database.KweekBookDatabase
import com.kweekbook.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = KweekBookDatabase.getInstance(application)
    private val bookDao = database.bookDao()
    
    val allBooks: Flow<List<Book>> = bookDao.getAllBooks()
    
    private val _searchResults = MutableStateFlow<List<Book>>(emptyList())
    val searchResults: StateFlow<List<Book>> = _searchResults
    
    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook: StateFlow<Book?> = _selectedBook
    
    fun searchBooks(query: String) {
        viewModelScope.launch {
            bookDao.searchBooks(query).collect { results ->
                _searchResults.value = results
            }
        }
    }
    
    fun getBooksByStatus(status: String): Flow<List<Book>> {
        return bookDao.getBooksByStatus(status)
    }
    
    fun getBooksByCategory(category: String): Flow<List<Book>> {
        return bookDao.getBooksByCategory(category)
    }
    
    fun setSelectedBook(book: Book?) {
        _selectedBook.value = book
    }
    
    fun insertBooks(books: List<Book>) {
        viewModelScope.launch {
            bookDao.insertBooks(books)
        }
    }
    
    fun updateBook(book: Book) {
        viewModelScope.launch {
            bookDao.updateBook(book)
        }
    }
    
    fun deleteBook(book: Book) {
        viewModelScope.launch {
            bookDao.deleteBook(book)
        }
    }
}
