package com.example.bookemp.models

import com.google.firebase.Timestamp

data class Library(
    val id: String = "",
    val name: String = "",
    val userId: String = "",
    val createdAt: Timestamp = Timestamp.now()
)
