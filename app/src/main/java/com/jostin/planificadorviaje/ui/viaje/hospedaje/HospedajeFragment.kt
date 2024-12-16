package com.jostin.planificadorviaje.ui.viaje.hospedaje

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jostin.planificadorviaje.databinding.FragmentHospedajeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HospedajeFragment : Fragment() {

    private var _binding: FragmentHospedajeBinding? = null
    private val binding get() = _binding!!

    private var personCount = 1
    private var selectedClass = "Seleccionar"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHospedajeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupDatePickers()
        setupPersonCounter()
        setupClassSelector()
        setupSearchButton()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupDatePickers() {
        binding.checkInText.setOnClickListener {
            showDatePicker { date -> binding.checkInText.text = date }
        }

        binding.checkOutText.setOnClickListener {
            showDatePicker { date -> binding.checkOutText.text = date }
        }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(selectedDate.time)
                onDateSelected(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun setupPersonCounter() {
        binding.personsCard.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Seleccionar cantidad de personas")
                .setItems(arrayOf("1", "2", "3", "4", "5")) { _, which ->
                    personCount = which + 1
                    binding.personCountText.text = personCount.toString()
                }
                .show()
        }
    }

    private fun setupClassSelector() {
        binding.classCard.setOnClickListener {
            val classOptions = arrayOf("Económica", "Primera Clase")
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Seleccionar clase")
                .setItems(classOptions) { _, which ->
                    selectedClass = classOptions[which]
                    binding.classText.text = selectedClass
                }
                .show()
        }
    }

    private fun setupSearchButton() {
        binding.searchButton.setOnClickListener {
            val checkInDate = binding.checkInText.text.toString()
            val checkOutDate = binding.checkOutText.text.toString()

            if (checkInDate == "Fecha de Entrada" || checkOutDate == "Fecha de Salida") {
                Toast.makeText(requireContext(), "Por favor selecciona las fechas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedClass == "Seleccionar") {
                Toast.makeText(requireContext(), "Por favor selecciona una clase", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Navegar al fragmento ResultadosHospedajeFragment
            val action = HospedajeFragmentDirections.actionHospedajeToResultadosHospedaje(
//                checkInDate = checkInDate,
//                checkOutDate = checkOutDate,
//                personCount = personCount,
//                selectedClass = selectedClass
            )
            findNavController().navigate(action)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
