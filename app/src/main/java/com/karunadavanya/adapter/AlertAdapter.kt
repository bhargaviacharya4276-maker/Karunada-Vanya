package com.karunadavanya.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.karunadavanya.R
import com.karunadavanya.model.Alert

class AlertAdapter(
    private var alerts: List<Alert>
) : RecyclerView.Adapter<AlertAdapter.AlertViewHolder>() {

    inner class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val emoji: TextView = view.findViewById(R.id.alertEmoji)
        val type: TextView = view.findViewById(R.id.alertType)
        val location: TextView = view.findViewById(R.id.alertLocation)
        val badge: TextView = view.findViewById(R.id.alertBadge)
        val desc: TextView = view.findViewById(R.id.alertDesc)
        val progress: ProgressBar = view.findViewById(R.id.expiryBar)
        val time: TextView = view.findViewById(R.id.alertTime)
        val reporter: TextView = view.findViewById(R.id.alertReporter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alert_card, parent, false)
        return AlertViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        val alert = alerts[position]
        holder.emoji.text = alert.animalEmoji
        holder.type.text = alert.animalType
        holder.location.text = "📍 ${alert.location}"
        holder.desc.text = alert.description
        holder.time.text = "⏱ ${alert.timeAgoString()}"
        holder.reporter.text = "by ${alert.reporterName}"

        val pct = alert.expiryProgressPercent()
        holder.progress.progress = pct

        when (alert.urgency) {
            "Urgent" -> {
                holder.badge.text = "URGENT"
                holder.badge.setTextColor(Color.parseColor("#C0392B"))
                holder.badge.setBackgroundColor(Color.parseColor("#FDE8E8"))
            }
            "Active" -> {
                holder.badge.text = "ACTIVE"
                holder.badge.setTextColor(Color.parseColor("#1B4332"))
                holder.badge.setBackgroundColor(Color.parseColor("#E8F5E9"))
            }
            else -> {
                holder.badge.text = "INFO"
                holder.badge.setTextColor(Color.parseColor("#6B7280"))
                holder.badge.setBackgroundColor(Color.parseColor("#F3F4F6"))
            }
        }
    }

    override fun getItemCount() = alerts.size

    fun updateAlerts(newAlerts: List<Alert>) {
        alerts = newAlerts
        notifyDataSetChanged()
    }
}
