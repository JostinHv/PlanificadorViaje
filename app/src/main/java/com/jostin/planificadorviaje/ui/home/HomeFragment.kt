// HomeFragment.kt
package com.jostin.planificadorviaje.ui.home

import android.os.Bundle
import android.text.format.DateUtils.formatDateRange
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.data.local.AppDatabase
import com.jostin.planificadorviaje.data.local.LocalDataSource
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import com.jostin.planificadorviaje.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val database by lazy { AppDatabase.getDatabase(requireContext()) }
    private val repository by lazy { ItineraryRepository(LocalDataSource(database)) }

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(repository)
    }
    private lateinit var itineraryAdapter: ItineraryAdapter


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
        viewModel.loadUpcomingItinerary()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadItineraries()
    }


    private fun setupRecyclerView() {
        itineraryAdapter = ItineraryAdapter { itinerary ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToItineraryDetailFragment(itinerary.id)
            findNavController().navigate(action)
        }
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

    private fun observeViewModel() {
        // Observa los itinerarios y actualiza el adaptador
        viewModel.itineraries.observe(viewLifecycleOwner) { itineraries ->
            itineraryAdapter.submitList(itineraries) // Actualiza el adaptador con la lista

        }

        // Observa el itinerario próximo y actualiza la UI
        viewModel.upcomingItinerary.observe(viewLifecycleOwner) { itinerary ->
            displayUpcomingItinerary(itinerary)
        }
    }

    private fun displayUpcomingItinerary(itinerary: Itinerary) {
        binding.upcomingTripName.text = itinerary.name
        binding.upcomingTripDates.text = formatDateRange(itinerary.startDate, itinerary.endDate)
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