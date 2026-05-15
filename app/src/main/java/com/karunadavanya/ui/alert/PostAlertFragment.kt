package com.karunadavanya.ui.alert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.karunadavanya.databinding.FragmentPostAlertBinding
import com.karunadavanya.viewmodel.AlertViewModel

class PostAlertFragment : Fragment() {

    private var _binding: FragmentPostAlertBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlertViewModel by activityViewModels()

    private val animals = listOf(
        Pair("🐘", "Elephant"),
        Pair("🐆", "Leopard"),
        Pair("🐯", "Tiger"),
        Pair("🐗", "Wild Boar")
    )
    private var selectedAnimalIdx = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPostAlertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        // Animal selection grid
        val urgencyOptions = arrayOf("🔴 Urgent — People / livestock at immediate risk",
            "🟡 Active — Nearby, monitor closely", "🟢 Info — Seen, but not dangerous now")
        val urgencyAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, urgencyOptions)
        urgencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerUrgency.adapter = urgencyAdapter

        binding.btnSubmit.setOnClickListener {
            val location = binding.inputLocation.text.toString()
            val desc = binding.inputDesc.text.toString()
            if (location.isBlank() || desc.isBlank()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val urgency = when (binding.spinnerUrgency.selectedItemPosition) {
                0 -> "Urgent"; 1 -> "Active"; else -> "Info"
            }
            val (emoji, name) = animals[selectedAnimalIdx]
            viewModel.postAlert(name, emoji, location, desc, urgency, "You")
        }

        viewModel.postSuccess.observe(viewLifecycleOwner) { success ->
            success ?: return@observe
            if (success) {
                Toast.makeText(requireContext(), "✅ Alert sent to community!", Toast.LENGTH_LONG).show()
                viewModel.resetPostStatus()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "❌ Failed to send. Check your connection.", Toast.LENGTH_SHORT).show()
                viewModel.resetPostStatus()
            }
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
