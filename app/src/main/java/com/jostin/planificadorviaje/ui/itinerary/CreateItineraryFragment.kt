package com.jostin.planificadorviaje.ui.itinerary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.jostin.planificadorviaje.PlanificadorViajeApplication
import com.jostin.planificadorviaje.data.repository.CityRepository
import com.jostin.planificadorviaje.databinding.FragmentCreateItineraryBinding
import com.jostin.planificadorviaje.utils.UserSessionManager
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class CreateItineraryFragment : Fragment() {

    private var _binding: FragmentCreateItineraryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateItineraryViewModel by viewModels()

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
        // Inicializar el autocompletado
        setupDestinationAutocomplete()
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

    private fun setupDestinationAutocomplete() {
        val cityRepository = CityRepository()
        cityRepository.getCities { cities ->
            if (cities.isNotEmpty()) {
                // Crear un mapa para asociar los nombres con los objetos City
                val cityMap = cities.associateBy { it.name }

                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    cityMap.keys.toList()
                )

                binding.destinationEditText.setAdapter(adapter)

                // Listener para manejar la selección de una ciudad
                binding.destinationEditText.setOnItemClickListener { _, _, position, _ ->
                    val selectedCityName =
                        adapter.getItem(position) // Nombre de la ciudad seleccionada
                    if (selectedCityName != null) {
                        val selectedCity = cityMap[selectedCityName]
                        if (selectedCity != null) {
                            // Establece el texto del campo solo con el nombre seleccionado
                            binding.destinationEditText.setText(selectedCity.name, false)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Ciudad no encontrada",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "No se encontraron ciudades", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun showDateRangePicker() {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Selecciona un rango de fechas")
            .build()

        dateRangePicker.addOnPositiveButtonClickListener { dateRange ->
            val timeZone = TimeZone.getDefault()

            // Ajustar la fecha inicial
            val startCalendar = Calendar.getInstance(timeZone)
            startCalendar.timeInMillis = dateRange.first ?: 0
            startCalendar.add(Calendar.DAY_OF_MONTH, 1) // Sumar un día
            startDate = startCalendar.time

            // Ajustar la fecha final
            val endCalendar = Calendar.getInstance(timeZone)
            endCalendar.timeInMillis = dateRange.second ?: 0
            endCalendar.add(Calendar.DAY_OF_MONTH, 1) // Sumar un día
            endDate = endCalendar.time

            // Formatear las fechas para mostrarlas
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
