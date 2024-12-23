package com.jostin.planificadorviaje.ui.plan.restaurant

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.data.model.City
import com.jostin.planificadorviaje.data.model.Place
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.data.model.PlanType
import com.jostin.planificadorviaje.databinding.FragmentRestaurantFormBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

@AndroidEntryPoint
class RestaurantFormFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantFormBinding
    private val viewModel: RestaurantFormViewModel by viewModels()
    private val args: RestaurantFormFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupDatePicker()
        setupTimePicker()
        setupMealTypeDropdown()
        setupAddPlaceButton()
        setupSaveButton()
        observeViewModel()
        // Observe selected place from RestaurantMapFragment
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Place>("selected_place")
            ?.observe(viewLifecycleOwner) { place ->
                viewModel.setSelectedPlace(place)
            }
    }

    private fun setupDatePicker() {
        binding.etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.etDate.setText(selectedDate)
            }, year, month, day).show()
        }
    }

    private fun setupTimePicker() {
        binding.etTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                binding.etTime.setText(selectedTime)
            }, hour, minute, true).show()
        }
    }

    private fun setupMealTypeDropdown() {
        val mealTypes = arrayOf("Desayuno", "Almuerzo", "Cena")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, mealTypes)
        binding.actvMealType.setAdapter(adapter)
    }

    private fun setupAddPlaceButton() {
        binding.btnAddPlace.setOnClickListener {
            findNavController().navigate(
                RestaurantFormFragmentDirections.actionRestaurantFormFragmentToRestaurantMapFragment(
                    args.city
                )
            )
        }
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            savePlan()
            findNavController().navigate(
                RestaurantFormFragmentDirections.actionRestaurantFormFragmentToItineraryDetailFragment(
                    args.itineraryId
                )
            )
        }
    }

    private fun observeViewModel() {
        viewModel.selectedPlace.observe(viewLifecycleOwner) { place ->
            updatePlaceUI(place)
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun updatePlaceUI(place: Place?) {
        if (place != null) {
            binding.tvRestaurantName.text = place.name
            binding.tvRestaurantName.visibility = View.VISIBLE
            binding.tvRestaurantAddress.text = place.address
            binding.tvRestaurantAddress.visibility = View.VISIBLE
        } else {
            binding.tvRestaurantName.visibility = View.GONE
            binding.tvRestaurantAddress.visibility = View.GONE
        }
    }

    private fun savePlan() {
        try {
            val dateText = binding.etDate.text.toString()
            val timeText = binding.etTime.text.toString()

            if (dateText.isEmpty() || timeText.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Por favor, ingrese la fecha y la hora.",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            // Combinar la fecha y la hora para parsearlas correctamente
            val dateTimeText = "$dateText $timeText"
            val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val parsedDate = dateTimeFormat.parse(dateTimeText)

            // Crear el mapa de detalles solo con valores no nulos y no vacíos
            val details = mutableMapOf<String, String>()
            viewModel.selectedPlace.value?.let { place ->
                if (place.name.isNotBlank()) details["Restaurante"] = place.name
                if (place.address.isNotBlank()) details["Dirección"] = place.address
            }
            if (timeText.isNotBlank()) details["Hora"] = timeText
            if (dateText.isNotBlank()) details["Día"] = dateText
            if (binding.actvMealType.text.toString().isNotBlank()) details["Tipo de Comida"] =
                binding.actvMealType.text.toString()

            // Crear el plan con los datos ingresados
            val plan = Plan(
                id = UUID.randomUUID().toString(),
                type = PlanType.RESTAURANT,
                name = binding.etPlanName.text.toString(),
                date = parsedDate!!, // El parse asegura que no sea null
                details = details,
                itineraryId = args.itineraryId
            )
            viewModel.savePlan(plan)
            Toast.makeText(requireContext(), "Plan guardado exitosamente", Toast.LENGTH_SHORT)
                .show()

        } catch (e: ParseException) {
            // Manejo de errores de formato de fecha
            Toast.makeText(
                requireContext(),
                "Formato de fecha u hora inválido.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}