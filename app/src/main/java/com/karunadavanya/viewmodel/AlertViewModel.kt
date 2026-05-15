package com.karunadavanya.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.karunadavanya.model.Alert
import com.karunadavanya.repository.AlertRepository
import kotlinx.coroutines.launch

class AlertViewModel : ViewModel() {
    private val repository = AlertRepository()

    private val _alerts = MutableLiveData<List<Alert>>()
    val alerts: LiveData<List<Alert>> = _alerts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _postSuccess = MutableLiveData<Boolean?>()
    val postSuccess: LiveData<Boolean?> = _postSuccess

    fun loadAlerts() {
        viewModelScope.launch {
            _isLoading.value = true
            _alerts.value = repository.getActiveAlerts()
            _isLoading.value = false
        }
    }

    fun postAlert(
        animalType: String,
        animalEmoji: String,
        location: String,
        description: String,
        urgency: String,
        reporterName: String
    ) {
        viewModelScope.launch {
            val alert = Alert(
                animalType = animalType,
                animalEmoji = animalEmoji,
                location = location,
                description = description,
                urgency = urgency,
                reporterName = reporterName,
                timestamp = Timestamp.now()
            )
            val success = repository.postAlert(alert)
            _postSuccess.value = success
            if (success) loadAlerts()
        }
    }

    fun resetPostStatus() { _postSuccess.value = null }
}
