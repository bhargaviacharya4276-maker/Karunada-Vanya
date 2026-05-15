package com.karunadavanya.adapter

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.karunadavanya.R
import com.karunadavanya.model.SoundItem

class SoundsAdapter(
    private val sounds: List<SoundItem>
) : RecyclerView.Adapter<SoundsAdapter.SoundViewHolder>() {

    private var mediaPlayer: MediaPlayer? = null
    private var currentPlayingPos = -1
    private val handler = Handler(Looper.getMainLooper())
    private var progressRunnable: Runnable? = null

    inner class SoundViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: TextView = view.findViewById(R.id.soundIcon)
        val name: TextView = view.findViewById(R.id.soundName)
        val desc: TextView = view.findViewById(R.id.soundDesc)
        val btnPlay: Button = view.findViewById(R.id.btnPlay)
        val progress: ProgressBar = view.findViewById(R.id.soundProgress)
        val duration: TextView = view.findViewById(R.id.soundDuration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sound_card, parent, false)
        return SoundViewHolder(view)
    }

    override fun onBindViewHolder(holder: SoundViewHolder, position: Int) {
        val sound = sounds[position]
        holder.icon.text = sound.emoji
        holder.name.text = sound.name
        holder.desc.text = sound.description
        holder.duration.text = formatDuration(sound.durationSeconds)

        val isPlaying = currentPlayingPos == position
        holder.btnPlay.text = if (isPlaying) "⏸" else "▶"
        holder.progress.progress = 0

        holder.btnPlay.setOnClickListener {
            if (currentPlayingPos == position) {
                stopPlayback()
            } else {
                playSound(position, sound, holder)
            }
        }
    }

    private fun playSound(position: Int, sound: SoundItem, holder: SoundViewHolder) {
        stopPlayback()
        currentPlayingPos = position

        // NOTE: Replace sound.rawResId with actual R.raw.your_sound_file
        // For prototype, we simulate playback with a handler
        holder.btnPlay.text = "⏸"
        val totalMs = sound.durationSeconds * 1000L
        val startTime = System.currentTimeMillis()

        progressRunnable = object : Runnable {
            override fun run() {
                val elapsed = System.currentTimeMillis() - startTime
                val pct = ((elapsed.toFloat() / totalMs) * 100).toInt().coerceIn(0, 100)
                holder.progress.progress = pct
                val remaining = ((totalMs - elapsed) / 1000).toInt().coerceAtLeast(0)
                holder.duration.text = formatDuration(remaining)
                if (pct < 100) {
                    handler.postDelayed(this, 200)
                } else {
                    stopPlayback()
                    notifyItemChanged(position)
                }
            }
        }
        handler.post(progressRunnable!!)
    }

    private fun stopPlayback() {
        progressRunnable?.let { handler.removeCallbacks(it) }
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        val prev = currentPlayingPos
        currentPlayingPos = -1
        if (prev >= 0) notifyItemChanged(prev)
    }

    private fun formatDuration(seconds: Int): String {
        val m = seconds / 60
        val s = seconds % 60
        return "$m:${s.toString().padStart(2, '0')}"
    }

    override fun getItemCount() = sounds.size

    fun releasePlayer() { stopPlayback() }
}
