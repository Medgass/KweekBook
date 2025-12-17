package com.kweekbook.database

import androidx.room.*
import com.kweekbook.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<Book>)
    
    @Update
    suspend fun updateBook(book: Book)
    
    @Delete
    suspend fun deleteBook(book: Book)
    
    @Query("SELECT * FROM books ORDER BY title ASC")
    fun getAllBooks(): Flow<List<Book>>
    
    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: Int): Book?
    
    @Query("SELECT * FROM books WHERE title LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%'")
    fun searchBooks(query: String): Flow<List<Book>>
    
    @Query("SELECT * FROM books WHERE status = :status")
    fun getBooksByStatus(status: String): Flow<List<Book>>
    
    @Query("SELECT * FROM books WHERE category = :category")
    fun getBooksByCategory(category: String): Flow<List<Book>>
    
    @Query("SELECT COUNT(*) FROM books")
    suspend fun getBookCount(): Int
    
    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()
}
