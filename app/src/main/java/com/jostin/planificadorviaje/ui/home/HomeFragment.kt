package com.jostin.planificadorviaje.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.model.Itinerary
import com.jostin.planificadorviaje.databinding.FragmentHomeBinding
import com.jostin.planificadorviaje.adapter.ItineraryAdapter
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
        setupToolbar()
        setupRecyclerView()
        setupFab()
        setupEmptyState()
        observeViewModel()

        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) == appBarLayout.totalScrollRange) {
                // Collapsed
                binding.toolbar.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            } else {
                // Expanded or in between
                binding.toolbar.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.transparent
                    )
                )
            }
        })

    }

    override fun onResume() {
        super.onResume()
        homeViewModel.fetchItineraries()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            // Handle navigation icon press
        }

    }

    private fun setupRecyclerView() {
        binding.itinerariesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itineraryAdapter
        }
    }

    private fun setupFab() {
        binding.addItineraryFab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createItineraryFragment)
        }
    }

    private fun setupEmptyState() {
        binding.emptyStateLayout.createFirstTripButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createItineraryFragment)
        }
    }

    private fun observeViewModel() {
        homeViewModel.itineraries.observe(viewLifecycleOwner) { itineraries ->
            itineraryAdapter.submitList(itineraries)
            toggleEmptyState(itineraries.isEmpty())
        }

        homeViewModel.upcomingItinerary.observe(viewLifecycleOwner) { itinerary ->
            if (itinerary != null) {
                displayUpcomingItinerary(itinerary)
                binding.upcomingTripCard.root.visibility = View.VISIBLE
            } else {
                binding.upcomingTripCard.root.visibility = View.GONE
            }
        }
    }

    private fun toggleEmptyState(isEmpty: Boolean) {
        binding.emptyStateLayout.root.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.upcomingTripCard.root.visibility = if (isEmpty) View.GONE else View.VISIBLE
        binding.yourItinerariesTitle.visibility = if (isEmpty) View.GONE else View.VISIBLE
        binding.itinerariesRecyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
        binding.addItineraryFab.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun displayUpcomingItinerary(itinerary: Itinerary) {
        binding.upcomingTripCard.apply {
            val city = homeViewModel.getCityByName(itinerary.destination).value
            upcomingTripName.text = itinerary.name
            upcomingTripDates.text = formatDateRange(itinerary.startDate, itinerary.endDate)

            // Load image using Glide
            if (city != null) {
                Glide.with(this@HomeFragment)
                    .load(city.imageUrl ?: R.drawable.item_itinerary_bg)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .centerCrop()
                    .into(upcomingTripImage)
            }

            viewTripButton.setOnClickListener {
                navigateToItineraryDetails(itinerary.id)
            }
        }
    }

    private fun navigateToItineraryDetails(itineraryId: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToItineraryDetailFragment(itineraryId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

