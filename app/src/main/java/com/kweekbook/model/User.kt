package com.kweekbook.model

data class User(
    val name: String,
    val email: String,
    val memberSince: String = java.text.SimpleDateFormat("MMMM yyyy", java.util.Locale.FRENCH)
        .format(java.util.Date())
)
