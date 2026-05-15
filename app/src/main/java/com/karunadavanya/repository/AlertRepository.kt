package com.karunadavanya.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.karunadavanya.model.Alert
import kotlinx.coroutines.tasks.await

class AlertRepository {
    private val db = FirebaseFirestore.getInstance()
    private val alertsCollection = db.collection("alerts")

    // Fetch alerts from Firebase, filter out expired ones
    suspend fun getActiveAlerts(): List<Alert> {
        return try {
            val snapshot = alertsCollection
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(20)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Alert::class.java)?.copy(id = doc.id)
            }.filter { !it.isExpired() }
        } catch (e: Exception) {
            Log.e("AlertRepository", "Error getting active alerts", e)
            emptyList()
        }
    }

    // Post a new alert with current timestamp
    suspend fun postAlert(alert: Alert): Boolean {
        return try {
            alertsCollection.add(alert).await()
            true
        } catch (e: Exception) {
            Log.e("AlertRepository", "Error posting alert", e)
            false
        }
    }

    // Delete expired alerts from Firestore (call periodically)
    @Suppress("unused")
    suspend fun cleanupExpiredAlerts() {
        try {
            val snapshot = alertsCollection.get().await()
            snapshot.documents.forEach { doc ->
                val alert = doc.toObject(Alert::class.java)
                if (alert?.isExpired() == true) {
                    doc.reference.delete().await()
                }
            }
        } catch (e: Exception) {
            Log.e("AlertRepository", "Error cleaning up expired alerts", e)
        }
    }
}
