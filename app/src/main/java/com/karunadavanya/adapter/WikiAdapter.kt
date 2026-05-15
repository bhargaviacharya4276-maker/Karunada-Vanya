package com.karunadavanya.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.karunadavanya.R
import com.karunadavanya.model.WildlifeItem

class WikiAdapter(
    private var items: List<WildlifeItem>,
    private val onClick: (WildlifeItem) -> Unit
) : RecyclerView.Adapter<WikiAdapter.WikiViewHolder>() {

    inner class WikiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val emoji: TextView = view.findViewById(R.id.itemEmoji)
        val name: TextView = view.findViewById(R.id.itemName)
        val sci: TextView = view.findViewById(R.id.itemSci)
        val tagContainer: LinearLayout = view.findViewById(R.id.tagContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WikiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wiki_card, parent, false)
        return WikiViewHolder(view)
    }

    override fun onBindViewHolder(holder: WikiViewHolder, position: Int) {
        val item = items[position]
        holder.emoji.text = item.emoji
        holder.name.text = item.name
        holder.sci.text = item.scientificName

        // Build tags dynamically
        holder.tagContainer.removeAllViews()
        addTag(holder.tagContainer, item.category)
        addTag(holder.tagContainer, item.status, isDanger = true)

        holder.itemView.setOnClickListener { onClick(item) }
    }

    private fun addTag(container: LinearLayout, text: String, isDanger: Boolean = false) {
        val ctx = container.context
        val tv = TextView(ctx).apply {
            this.text = text
            textSize = 10f
            setPadding(16, 6, 16, 6)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.marginEnd = 6
            layoutParams = params
            if (isDanger) {
                setBackgroundColor(Color.parseColor("#FCE4EC"))
                setTextColor(Color.parseColor("#C62828"))
            } else {
                setBackgroundColor(Color.parseColor("#E8F5E9"))
                setTextColor(Color.parseColor("#2E7D32"))
            }
        }
        container.addView(tv)
    }

    override fun getItemCount() = items.size

    fun updateItems(newItems: List<WildlifeItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
