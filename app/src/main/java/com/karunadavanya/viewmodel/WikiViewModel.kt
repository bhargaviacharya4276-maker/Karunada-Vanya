package com.karunadavanya.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karunadavanya.model.WildlifeItem
import com.karunadavanya.repository.WikiRepository

class WikiViewModel : ViewModel() {
    private val repository = WikiRepository()

    private val _items = MutableLiveData<List<WildlifeItem>>()
    val items: LiveData<List<WildlifeItem>> = _items

    private val _selectedItem = MutableLiveData<WildlifeItem?>()
    val selectedItem: LiveData<WildlifeItem?> = _selectedItem

    private var currentQuery = ""
    private var currentCategory = "All"

    init { loadItems() }

    fun loadItems() {
        _items.value = repository.getAllItems()
    }

    fun search(query: String) {
        currentQuery = query
        applyFilters()
    }

    fun filterByCategory(category: String) {
        currentCategory = category
        applyFilters()
    }

    private fun applyFilters() {
        var result = repository.getAllItems()
        if (currentCategory != "All") {
            result = result.filter { it.category == currentCategory }
        }
        if (currentQuery.isNotBlank()) {
            val q = currentQuery.lowercase()
            result = result.filter {
                it.name.lowercase().contains(q) || it.scientificName.lowercase().contains(q)
            }
        }
        _items.value = result
    }

    fun selectItem(id: String) {
        _selectedItem.value = repository.getItemById(id)
    }
}
