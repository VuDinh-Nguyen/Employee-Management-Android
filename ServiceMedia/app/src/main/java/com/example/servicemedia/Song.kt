package com.example.servicemedia

import java.io.Serializable

data class Song(
    val title: String,
    val artist: String,
    val audioResId: Int
) : Serializable