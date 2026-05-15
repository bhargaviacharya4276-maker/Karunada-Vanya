package com.karunadavanya.repository

import com.karunadavanya.model.WildlifeData
import com.karunadavanya.model.WildlifeItem

class WikiRepository {
    // All data is local — fully offline ready
    fun getAllItems(): List<WildlifeItem> = WildlifeData.items

    fun getItemById(id: String): WildlifeItem? = WildlifeData.items.find { it.id == id }

    fun searchItems(query: String): List<WildlifeItem> {
        if (query.isBlank()) return WildlifeData.items
        val q = query.lowercase()
        return WildlifeData.items.filter {
            it.name.lowercase().contains(q) || it.scientificName.lowercase().contains(q)
        }
    }

    fun filterByCategory(category: String): List<WildlifeItem> {
        if (category == "All") return WildlifeData.items
        return WildlifeData.items.filter { it.category == category }
    }
}
