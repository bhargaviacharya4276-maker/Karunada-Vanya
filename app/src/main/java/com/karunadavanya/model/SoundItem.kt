package com.karunadavanya.model

data class SoundItem(
    val id: String,
    val emoji: String,
    val name: String,
    val description: String,
    val durationSeconds: Int,
    val rawResId: Int       // R.raw.xxx
)
