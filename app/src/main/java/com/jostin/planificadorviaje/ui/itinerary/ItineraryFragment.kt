package com.jostin.planificadorviaje.ui.itinerary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.utils.DateUtils.formatDate
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.data.local.LocalDataSource
import com.jostin.planificadorviaje.data.local.AppDatabase
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import com.jostin.planificadorviaje.databinding.FragmentItineraryBinding

class ItineraryFragment : Fragment() {

    private lateinit var binding: FragmentItineraryBinding

    // Instancia del repositorio y el ViewModel
    private val database by lazy { AppDatabase.getDatabase(requireContext()) }
    private val repository by lazy { ItineraryRepository(LocalDataSource(database)) }
    private val viewModel: ItineraryViewModel by viewModels {
        ItineraryViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItineraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeViewModel()

        // Fetch itineraries
        viewModel.fetchItineraries()
    }

    private fun setupViews() {
        binding.addPlanFab.setOnClickListener {
            // Lógica para agregar un plan
            Toast.makeText(requireContext(), "Agregar plan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.itinerary.observe(viewLifecycleOwner) { itineraries ->
            // Para este ejemplo, asumimos que tomas el primer itinerario de la lista
            // Actualiza esta lógica según tus necesidades
            val itinerary = itineraries.firstOrNull() ?: return@observe

            // Actualiza la imagen de portada
            Glide.with(this)
                .load(itinerary.coverImageUrl)
                .into(binding.itineraryCoverImage)

            // Actualiza el nombre del itinerario
            binding.itineraryName.text = itinerary.name

            // Actualiza las fechas del itinerario
            val startDate = formatDate(itinerary.startDate)
            val endDate = formatDate(itinerary.endDate)
            binding.itineraryDates.text = getString(R.string.itinerary_dates, startDate, endDate)

            // Actualiza la descripción del itinerario
            binding.itineraryDescription.text = itinerary.description
        }
    }
}
