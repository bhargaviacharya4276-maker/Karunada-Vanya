package com.karunadavanya.model

data class ForestSound(
    val id: Int,
    val name: String,
    val scientificName: String,
    val description: String,
    val emoji: String,
    val rawResId: Int,        // e.g. R.raw.eagle_owl
    val durationSeconds: Int
)
