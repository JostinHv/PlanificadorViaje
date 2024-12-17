package com.jostin.planificadorviaje.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.data.local.AppDatabase
import com.jostin.planificadorviaje.data.local.datasource.LocalDataSource
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import com.jostin.planificadorviaje.databinding.FragmentHomeBinding
import com.jostin.planificadorviaje.utils.DateUtils.Companion.formatDateRange
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    private val itineraryAdapter by lazy {
        ItineraryAdapter { itinerary ->
            navigateToItineraryDetails(itinerary.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFab()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.fetchItineraries()
    }

    /**
     * Set up RecyclerView for displaying itineraries.
     */
    private fun setupRecyclerView() {
        binding.itinerariesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itineraryAdapter
        }
    }

    /**
     * Set up FloatingActionButton for adding a new itinerary.
     */
    private fun setupFab() {
        binding.addItineraryFab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createItineraryFragment)
        }
    }

    /**
     * Observe changes in ViewModel data.
     */
    private fun observeViewModel() {
        homeViewModel.itineraries.observe(viewLifecycleOwner) { itineraries ->
            itineraryAdapter.submitList(itineraries)
            toggleEmptyState(itineraries.isEmpty())
        }

        homeViewModel.upcomingItinerary.observe(viewLifecycleOwner) { itinerary ->
            itinerary?.let { displayUpcomingItinerary(itinerary) }
        }
    }

    /**
     * Toggle visibility of the empty state view.
     */
    private fun toggleEmptyState(isEmpty: Boolean) {
        binding.emptyStateLayout.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.upcomingTripLayout.visibility = if (isEmpty) View.GONE else View.VISIBLE
        binding.yourItinerariesTitle.visibility = if (isEmpty) View.GONE else View.VISIBLE
        binding.itinerariesRecyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    /**
     * Display details of the upcoming itinerary.
     */
    private fun displayUpcomingItinerary(itinerary: Itinerary) {
        binding.upcomingTripName.text = itinerary.name
        binding.upcomingTripDates.text = formatDateRange(itinerary.startDate, itinerary.endDate)
    }


    /**
     * Navigate to the details screen of a specific itinerary.
     */
    private fun navigateToItineraryDetails(itineraryId: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToItineraryDetailFragment(itineraryId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
