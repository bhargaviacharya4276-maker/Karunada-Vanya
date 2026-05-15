package com.karunadavanya.model

import com.google.firebase.Timestamp

data class Alert(
    val id: String = "",
    val animalType: String = "",
    val animalEmoji: String = "",
    val location: String = "",
    val description: String = "",
    val urgency: String = "Active",       // Urgent, Active, Info
    val reporterName: String = "",
    val timestamp: Timestamp = Timestamp.now()
) {
    // Expiry check: 6-hour rule
    fun isExpired(): Boolean {
        val sixHoursMillis = 6 * 60 * 60 * 1000L
        val ageMillis = System.currentTimeMillis() - timestamp.toDate().time
        return ageMillis > sixHoursMillis
    }

    fun expiryProgressPercent(): Int {
        val sixHoursMillis = 6 * 60 * 60 * 1000L
        val ageMillis = System.currentTimeMillis() - timestamp.toDate().time
        val remaining = sixHoursMillis - ageMillis
        return ((remaining.toFloat() / sixHoursMillis) * 100).toInt().coerceIn(0, 100)
    }

    fun timeAgoString(): String {
        val ageMillis = System.currentTimeMillis() - timestamp.toDate().time
        val minutes = ageMillis / 60000
        return when {
            minutes < 60 -> "${minutes}m ago"
            else -> "${minutes / 60}h ${minutes % 60}m ago"
        }
    }
}
