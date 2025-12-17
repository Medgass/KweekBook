package com.kweekbook.network

import com.kweekbook.model.Book
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApiService {
    
    @GET("api/books")
    suspend fun getAllBooks(): List<Book>
    
    @GET("api/books/{id}")
    suspend fun getBookById(@Path("id") id: Int): Book
    
    @GET("api/books/search")
    suspend fun searchBooks(@Query("q") query: String): List<Book>
    
    @GET("api/books/category/{category}")
    suspend fun getBooksByCategory(@Path("category") category: String): List<Book>
}
