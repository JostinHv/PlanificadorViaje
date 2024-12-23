package com.jostin.planificadorviaje.ui.viaje

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jostin.planificadorviaje.databinding.FragmentViajesBinding
import com.jostin.planificadorviaje.utils.SampleDataGenerator
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ViajesFragment : Fragment() {

    private var _binding: FragmentViajesBinding? = null
    private val binding get() = _binding!!

    private val cities = SampleDataGenerator.getCities()
    private var personCount = 1
    private var selectedDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViajesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAutoCompleteFields()
        setupPersonCounter()
        setupDateChips()
        setupSearchButton()
    }

    private fun setupAutoCompleteFields() {
        val cityNames = cities.map { it.name }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            cityNames
        )

        binding.originAutoCompleteTextView.setAdapter(adapter)
        binding.destinationAutoCompleteTextView.setAdapter(adapter)

        binding.originAutoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedCity = cityNames[position]
            Toast.makeText(requireContext(), "Origen seleccionado: $selectedCity", Toast.LENGTH_SHORT).show()
        }

        binding.destinationAutoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedCity = cityNames[position]
            Toast.makeText(requireContext(), "Destino seleccionado: $selectedCity", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupPersonCounter() {
        binding.personCountTextView.text = personCount.toString()

        binding.decreaseButton.setOnClickListener {
            if (personCount > 1) {
                personCount--
                binding.personCountTextView.text = personCount.toString()
            } else {
                Toast.makeText(requireContext(), "Mínimo de 1 persona", Toast.LENGTH_SHORT).show()
            }
        }

        binding.increaseButton.setOnClickListener {
            if (personCount < 5) {
                personCount++
                binding.personCountTextView.text = personCount.toString()
            } else {
                Toast.makeText(requireContext(), "Máximo de 5 personas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupDateChips() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Acción para `todayChip`
        binding.todayChip.setOnClickListener {
            selectedDate = dateFormat.format(calendar.time)
            updateSelectedDateUI("Hoy ($selectedDate)")
        }

        // Acción para `tomorrowChip`
        binding.tomorrowChip.setOnClickListener {
            calendar.add(Calendar.DAY_OF_YEAR, 1) // Añadir un día
            selectedDate = dateFormat.format(calendar.time)
            updateSelectedDateUI("Mañana ($selectedDate)")
        }

        // Acción para `chooseDateChip`
        binding.chooseDateChip.setOnClickListener {
            showDatePicker()
        }
    }

    private fun updateSelectedDateUI(text: String) {
        binding.chooseDateChip.isChecked = false // Deseleccionar "Elegir fecha"
        binding.todayChip.isChecked = false // Deseleccionar "Hoy"
        binding.tomorrowChip.isChecked = false // Deseleccionar "Mañana"
        Toast.makeText(requireContext(), "Fecha seleccionada: $text", Toast.LENGTH_SHORT).show()
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val date = Calendar.getInstance().apply {
                    set(year, month, day)
                }
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                selectedDate = dateFormat.format(date.time)
                updateSelectedDateUI("Seleccionada ($selectedDate)")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun setupSearchButton() {
        binding.searchButton.setOnClickListener {
            val origin = binding.originAutoCompleteTextView.text.toString()
            val destination = binding.destinationAutoCompleteTextView.text.toString()

            if (origin.isBlank() || destination.isBlank()) {
                Toast.makeText(requireContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (origin == destination) {
                Toast.makeText(requireContext(), "El origen y destino no pueden ser iguales", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedDate.isNullOrBlank()) {
                Toast.makeText(requireContext(), "Por favor selecciona una fecha", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Navega a la siguiente pantalla pasando los datos como Safe Args
            val action = ViajesFragmentDirections.actionViajesFragmentToViajeOpcionesFragment(
                origin,
                destination,
                personCount,
                selectedDate!!
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
