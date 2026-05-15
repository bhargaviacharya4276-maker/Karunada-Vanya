package com.karunadavanya.ui.wiki

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.karunadavanya.R
import com.karunadavanya.databinding.FragmentWikiDetailBinding
import com.karunadavanya.viewmodel.WikiViewModel

class WikiDetailFragment : Fragment() {

    private var _binding: FragmentWikiDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WikiViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWikiDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val speciesId = arguments?.getString("speciesId") ?: return
        viewModel.selectItem(speciesId)
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        viewModel.selectedItem.observe(viewLifecycleOwner) { item ->
            item ?: return@observe
            binding.detailEmoji.text = item.emoji
            binding.detailName.text = item.name
            binding.detailSci.text = item.scientificName
            binding.detailDesc.text = item.description
            binding.detailRole.text = item.ecologicalRole
            binding.factGrid.removeAllViews()
            listOf(
                "Habitat" to item.habitat,
                "Diet" to item.diet,
                "Weight" to item.weight,
                "Status" to item.status
            ).forEach { (label, value) -> addFactItem(label, value) }
        }
    }

    private fun addFactItem(label: String, value: String) {
        val ctx = requireContext()
        val container = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundResource(R.drawable.surface_bg)
            setPadding(24, 20, 24, 20)
            val params = GridLayout.LayoutParams().apply {
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED)
                setMargins(0, 0, 8, 8)
                width = 0
            }
            layoutParams = params
        }
        container.addView(TextView(ctx).apply {
            text = label; textSize = 10f
            setTextColor(Color.parseColor("#6B7280"))
        })
        container.addView(TextView(ctx).apply {
            text = value; textSize = 13f
            setTypeface(null, Typeface.BOLD)
        })
        binding.factGrid.addView(container)
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
