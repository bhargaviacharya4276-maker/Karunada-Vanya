package com.karunadavanya.ui.alert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.karunadavanya.R
import com.karunadavanya.adapter.AlertAdapter
import com.karunadavanya.databinding.FragmentAlertBinding
import com.karunadavanya.viewmodel.AlertViewModel

class AlertFragment : Fragment() {

    private var _binding: FragmentAlertBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlertViewModel by activityViewModels()
    private lateinit var adapter: AlertAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAlertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AlertAdapter(emptyList())
        binding.alertRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.alertRecycler.adapter = adapter

        binding.btnReport.setOnClickListener {
            findNavController().navigate(R.id.action_alert_to_post)
        }

        viewModel.alerts.observe(viewLifecycleOwner) { alerts ->
            adapter.updateAlerts(alerts)
            binding.alertCount.text = "${alerts.size} active alert${if (alerts.size != 1) "s" else ""} near you"
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            if (loading) binding.alertCount.text = "Loading alerts..."
        }

        viewModel.loadAlerts()
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
