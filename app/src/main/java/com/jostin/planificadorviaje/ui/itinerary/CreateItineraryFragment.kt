package com.jostin.planificadorviaje.ui.itinerary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.jostin.planificadorviaje.PlanificadorViajeApplication
import com.jostin.planificadorviaje.databinding.FragmentCreateItineraryBinding
import com.jostin.planificadorviaje.utils.UserSessionManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateItineraryFragment : Fragment() {

    private var _binding: FragmentCreateItineraryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateItineraryViewModel by viewModels {
        CreateItineraryViewModelFactory(
            (requireActivity().application as PlanificadorViajeApplication).itineraryRepository
        )
    }

    private var startDate: Date? = null
    private var endDate: Date? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateItineraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Botón para crear el itinerario
        binding.createItineraryButton.setOnClickListener {
            saveItinerary()
        }

        // Botón de navegación (cerrar)
        binding.toolbarCreateItinerary.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // Selección del rango de fechas
        binding.dateRangeEditText.setOnClickListener {
            showDateRangePicker()
        }
    }

    private fun saveItinerary() {
        val name = binding.itineraryNameEditText.text.toString()
        val destination = binding.destinationEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()

        if (name.isEmpty() || destination.isEmpty() || startDate == null || endDate == null) {
            Toast.makeText(
                requireContext(),
                "Por favor, completa todos los campos",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val userId = UserSessionManager.getCurrentUser()?.id ?: ""
        val sharedWith = listOf(UserSessionManager.getCurrentUser()!!)
        // Crear el itinerario usando el ViewModel
        viewModel.createItinerary(
            name,
            userId,
            destination,
            startDate!!,
            endDate!!,
            description,
            emptyList(),
            sharedWith,
        )
        Toast.makeText(requireContext(), "Itinerario creado exitosamente", Toast.LENGTH_SHORT)
            .show()
        // Navegar hacia atrás
        findNavController().navigateUp()
    }

    private fun showDateRangePicker() {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Selecciona un rango de fechas")
            .build()

        dateRangePicker.addOnPositiveButtonClickListener { dateRange ->
            startDate = Date(dateRange.first ?: 0)
            endDate = Date(dateRange.second ?: 0)

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedStartDate = dateFormat.format(startDate!!)
            val formattedEndDate = dateFormat.format(endDate!!)
            binding.dateRangeEditText.setText("$formattedStartDate - $formattedEndDate")

        }

        dateRangePicker.show(parentFragmentManager, "Rango de Fechas")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
