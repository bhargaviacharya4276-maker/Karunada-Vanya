package com.karunadavanya.ui.sounds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.karunadavanya.R
import com.karunadavanya.adapter.SoundsAdapter
import com.karunadavanya.databinding.FragmentSoundsBinding
import com.karunadavanya.model.SoundItem

class SoundsFragment : Fragment() {
    private var _binding: FragmentSoundsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SoundsAdapter

    // NOTE: Add .mp3 files to res/raw/ folder and reference them here
    // Example: R.raw.eagle_owl_call
    private val sounds = listOf(
        SoundItem("owl", "🦅", "Indian Eagle-Owl", "Bubo bengalensis · Heard at dusk near cliffs", 22, R.raw.placeholder),
        SoundItem("thrush", "🎶", "Malabar Whistling Thrush", "Myophonus horsfieldii · Clear musical whistles", 18, R.raw.placeholder),
        SoundItem("elephant", "🐘", "Elephant Rumble", "Elephas maximus · Low frequency contact call", 15, R.raw.placeholder),
        SoundItem("deer", "🦌", "Barking Deer Alarm", "Muntiacus muntjak · Signals predator presence", 10, R.raw.placeholder),
        SoundItem("cuckoo", "🌿", "Common Hawk-Cuckoo", "Brain-fever bird · Ascending repetitive call", 25, R.raw.placeholder)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSoundsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SoundsAdapter(sounds)
        binding.soundsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.soundsRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        adapter.releasePlayer()
        super.onDestroyView()
        _binding = null
    }
}
