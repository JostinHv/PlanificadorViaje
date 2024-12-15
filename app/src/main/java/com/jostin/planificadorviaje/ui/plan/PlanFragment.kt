package com.jostin.planificadorviaje.ui.plan

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.databinding.FragmentPlanBinding

class PlanFragment : Fragment() {
    private lateinit var binding: FragmentPlanBinding
    private val viewModel: PlanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        // Set up views and listeners
    }

    private fun observeViewModel() {
        viewModel.plan.observe(viewLifecycleOwner) { plan ->
            // Update UI with plan details
        }
    }
}



