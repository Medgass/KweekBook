package com.kweekbook.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey val id: Int,
    val title: String,
    val author: String,
    val image: String,
    val description: String,
    val year: Int,
    val genre: String,
    val pages: Int,
    val isbn: String,
    val rating: Double,
    val language: String,
    val publisher: String,
    val status: String,  // 'available', 'borrowed', 'reserved'
    val category: String,
    val borrowedBy: String? = null,
    val borrowDate: String? = null,
    val returnDate: String? = null,
    val maxBorrowDays: Int,
    val totalCopies: Int,
    val availableCopies: Int,
    val reservedBy: String? = null,
    val reservedDate: String? = null
)
