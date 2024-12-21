package com.jostin.planificadorviaje.ui.itinerary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.data.local.AppDatabase
import com.jostin.planificadorviaje.databinding.FragmentItineraryDetailBinding
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import com.jostin.planificadorviaje.ui.plan.PlanAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ItineraryDetailFragment : Fragment() {

    private var _binding: FragmentItineraryDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ItineraryDetailFragmentArgs by navArgs()
    private lateinit var planAdapter: PlanAdapter
    private val viewModel: ItineraryDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItineraryDetailBinding.inflate(inflater, container, false)
        // Set up RecyclerView
        planAdapter = PlanAdapter(emptyList()) // Initialize with empty list
        binding.recyclerViewPlans.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = planAdapter
        }
        loadItineraryDetails()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        observeViewModel()
        setupFab()
        viewModel.loadItineraryDetails(args.itineraryId)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {
        viewModel.itinerary.observe(viewLifecycleOwner) { itinerary ->
            displayItineraryDetails(itinerary)
        }
    }

    private fun loadItineraryDetails() {

        viewModel.getItineraryDetails(args.itineraryId).observe(viewLifecycleOwner) { itineraryWithPlans ->
            val itinerary = itineraryWithPlans.itinerary
            binding.textViewItineraryName.text = itinerary.name
            binding.textViewDestination.text = itinerary.destination
            binding.textViewDateRange.text = "${itinerary.startDate} - ${itinerary.endDate}"
            binding.textViewDescription.text = itinerary.description

            // Configure shared users
            binding.travelersChipGroup.removeAllViews()
            itinerary.sharedWith.forEach { user ->
                val chip = Chip(requireContext()).apply {
                    text = user.name
                    isCheckable = false
                }
                binding.travelersChipGroup.addView(chip)
            }
            // Update the plans in the adapter
            planAdapter = PlanAdapter(itineraryWithPlans.plans)
            binding.recyclerViewPlans.adapter = planAdapter
        }
    }

    private fun displayItineraryDetails(itinerary: Itinerary) {
        binding.apply {
            textViewItineraryName.text = itinerary.name
            textViewDestination.text = itinerary.destination
            textViewDateRange.text = formatDateRange(itinerary.startDate, itinerary.endDate)
            textViewDescription.text = itinerary.description

            // Configure shared users
            travelersChipGroup.removeAllViews()
            itinerary.sharedWith.forEach { user ->
                val chip = Chip(requireContext()).apply {
                    text = user.name
                    isCheckable = false
                }
                travelersChipGroup.addView(chip)
            }
        }
    }

    private fun setupFab() {
        binding.fabAddPlan.setOnClickListener {
            findNavController().navigate(
                ItineraryDetailFragmentDirections.actionItineraryDetailFragmentToSelectPlanTypeFragment(
                    args.itineraryId
                )
            )
        }
    }

    private fun formatDateRange(startDate: Date, endDate: Date): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return "${dateFormat.format(startDate)} - ${dateFormat.format(endDate)}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
