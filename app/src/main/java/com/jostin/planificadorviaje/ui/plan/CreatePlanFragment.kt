package com.jostin.planificadorviaje.ui.plan

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Timestamp
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.databinding.FragmentCreatePlanBinding
import com.jostin.planificadorviaje.model.Plan
import com.jostin.planificadorviaje.model.PlanType
import com.jostin.planificadorviaje.ui.plan.hotel.HotelFormFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

@AndroidEntryPoint
class CreatePlanFragment : Fragment() {

    private var _binding: FragmentCreatePlanBinding? = null
    private val binding get() = _binding!!
    private val args: CreatePlanFragmentArgs by navArgs()
    private val viewModel: CreatePlanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup toolbar
        binding.toolbarCreatePlan.apply {
            title = "Crear ${args.planType}"
            setNavigationOnClickListener { findNavController().navigateUp() }
        }

        // Setup form fields dynamically
        setupFormFields(args.planType)

        // Configure date input to open a calendar
        setupDateInput()

        // Save Plan button
        binding.createPlanButton.setOnClickListener { savePlan() }
    }

    private fun setupDateInput() {
        binding.dateInput.editText?.apply {
            inputType = InputType.TYPE_NULL // Disable manual input
            setOnClickListener { showDatePicker(this) }
        }
    }

    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            calendar.set(selectedYear, selectedMonth, selectedDay)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            editText.setText(dateFormat.format(calendar.time))
        }, year, month, day).show()
    }

    private fun savePlan() {
        val planDetails = mutableMapOf<String, Any>()

        // Collect data from dynamically created fields
        binding.dynamicFieldsContainer.children.forEach { view ->
            if (view is TextInputLayout) {
                val hint = view.hint?.toString() ?: ""
                val inputValue = view.editText?.text?.toString() ?: ""
                planDetails[hint] = inputValue
            }
        }

        val title = binding.titleInput.editText?.text?.toString() ?: ""
        if (title.isBlank()) {
            binding.titleInput.error = "El título es obligatorio"
            return
        }

        val dateString = binding.dateInput.editText?.text?.toString() ?: ""
        if (dateString.isBlank()) {
            binding.dateInput.error = "La fecha es obligatoria"
            return
        }

        val date = try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.parse(dateString) ?: Date()
        } catch (e: Exception) {
            binding.dateInput.error = "Formato de fecha no válido"
            return
        }

        // Construct the plan object
        val type =
            PlanType.entries.find { getString(it.nameRes) == args.planType } ?: PlanType.FLIGHT
        val plan = Plan(
            id = UUID.randomUUID().toString(),
            itineraryId = args.itineraryId,
            type = type,
            name = title,
            date = Timestamp(date),
            details = planDetails
        )

        // Save plan using ViewModel
        viewModel.savePlan(plan)
        Toast.makeText(context, getString(R.string.plan_saved), Toast.LENGTH_SHORT).show()
        findNavController().navigate(
            CreatePlanFragmentDirections.actionCreatePlanFragmentToItineraryDetailFragment(args.itineraryId)
        )
    }

    private fun setupFormFields(planType: String) {
        val dynamicFields = binding.dynamicFieldsContainer

        when (planType) {
            getString(PlanType.FLIGHT.nameRes) -> {
                dynamicFields.apply {
                    addView(createInputField("Aerolínea"))
                    addView(createInputField("Número de Vuelo"))
                    addView(createInputField("Aeropuerto de Salida"))
                    addView(createInputField("Aeropuerto de Llegada"))
                    addView(createDateTimeField("Hora de Salida"))
                    addView(createDateTimeField("Hora de Llegada"))
                }
            }

            getString(PlanType.ACCOMMODATION.nameRes) -> {
                dynamicFields.apply {
                    addView(createInputField("Nombre del Hotel"))
                    addView(createInputField("Dirección"))
                    addView(createDateTimeField("Fecha de Check-in"))
                    addView(createDateTimeField("Fecha de Check-out"))
                }
            }

            getString(PlanType.CAR_RENTAL.nameRes) -> {
                dynamicFields.apply {
                    addView(createInputField("Compañía de Alquiler"))
                    addView(createInputField("Modelo del Vehículo"))
                    addView(createInputField("Lugar de Recogida"))
                    addView(createInputField("Lugar de Devolución"))
                    addView(createDateTimeField("Fecha y Hora de Recogida"))
                    addView(createDateTimeField("Fecha y Hora de Devolución"))
                }
            }

            getString(PlanType.MEETING.nameRes) -> {
                dynamicFields.apply {
                    addView(createInputField("Título de la Reunión"))
                    addView(createInputField("Ubicación"))
                    addView(createDateTimeField("Fecha y Hora"))
                    addView(createInputField("Participantes"))
                }
            }

            getString(PlanType.ACTIVITY.nameRes) -> {
                dynamicFields.apply {
                    addView(createInputField("Nombre de la Actividad"))
                    addView(createInputField("Lugar"))
                    addView(createDateTimeField("Fecha y Hora"))
                    addView(createInputField("Duración (Horas)"))
                }
            }

            getString(PlanType.RESTAURANT.nameRes) -> {
                dynamicFields.apply {
                    addView(createInputField("Nombre del Restaurante"))
                    addView(createInputField("Dirección"))
                    addView(createDateTimeField("Fecha y Hora de Reserva"))
                    addView(createInputField("Número de Personas"))
                }
            }

            getString(PlanType.TRANSPORT.nameRes) -> {
                dynamicFields.apply {
                    addView(createInputField("Tipo de Transporte"))
                    addView(createInputField("Origen"))
                    addView(createInputField("Destino"))
                    addView(createDateTimeField("Hora de Salida"))
                    addView(createDateTimeField("Hora de Llegada"))
                }
            }
        }
    }


    private fun createInputField(hint: String): View {
        val inputField = layoutInflater.inflate(
            R.layout.item_text_input, binding.dynamicFieldsContainer, false
        ) as TextInputLayout
        inputField.hint = hint
        return inputField
    }

    private fun createDateTimeField(hint: String): View {
        val dateTimeField = layoutInflater.inflate(
            R.layout.item_text_input, binding.dynamicFieldsContainer, false
        ) as TextInputLayout
        dateTimeField.hint = hint

        val editText = dateTimeField.editText
        editText?.apply {
            inputType = InputType.TYPE_NULL
            setOnClickListener { showDateTimePicker(this) }
        }
        return dateTimeField
    }

    private fun showDateTimePicker(editText: EditText) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)
        val hour = currentDate.get(Calendar.HOUR_OF_DAY)
        val minute = currentDate.get(Calendar.MINUTE)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                val selectedDateTime = Calendar.getInstance()
                selectedDateTime.set(
                    selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute
                )
                val formattedDateTime =
                    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(
                        selectedDateTime.time
                    )
                editText.setText(formattedDateTime)
            }, hour, minute, false).show()
        }, year, month, day).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
