package com.karunadavanya.ui.wiki

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.karunadavanya.R
import com.karunadavanya.adapter.WikiAdapter
import com.karunadavanya.databinding.FragmentWikiBinding
import com.karunadavanya.viewmodel.WikiViewModel

class WikiFragment : Fragment() {

    private var _binding: FragmentWikiBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WikiViewModel by viewModels()
    private lateinit var adapter: WikiAdapter

    private val categories = listOf("All", "Animal", "Bird", "Plant")
    private var selectedCategory = "All"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWikiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupChips()
        setupSearch()
        observeData()
    }

    private fun setupRecycler() {
        adapter = WikiAdapter(emptyList()) { item ->
            val bundle = Bundle().apply { putString("speciesId", item.id) }
            findNavController().navigate(R.id.action_wiki_to_detail, bundle)
        }
        binding.wikiRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.wikiRecycler.adapter = adapter
    }

    private fun setupChips() {
        categories.forEach { category ->
            val chip = TextView(requireContext()).apply {
                text = category
                textSize = 12f
                setPadding(40, 20, 40, 20)
                val params = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply { marginEnd = 16 }
                layoutParams = params
                setOnClickListener { onCategorySelected(category) }
            }
            updateChipStyle(chip, category == selectedCategory)
            binding.chipGroup.addView(chip)
        }
    }

    private fun onCategorySelected(category: String) {
        selectedCategory = category
        viewModel.filterByCategory(category)
        for (i in 0 until binding.chipGroup.childCount) {
            val chip = binding.chipGroup.getChildAt(i) as TextView
            updateChipStyle(chip, chip.text == category)
        }
    }

    private fun updateChipStyle(chip: TextView, isActive: Boolean) {
        if (isActive) {
            chip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.forest_dark))
            chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else {
            chip.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_muted))
        }
    }

    private fun setupSearch() {
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { viewModel.search(s.toString()) }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun observeData() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.updateItems(items)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
