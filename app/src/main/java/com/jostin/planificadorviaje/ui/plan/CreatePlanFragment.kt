package com.jostin.planificadorviaje.ui.plan

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Timestamp
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.data.model.PlanType
import com.jostin.planificadorviaje.databinding.FragmentCreatePlanBinding
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarCreatePlan.apply {
            title = "Crear ${args.planType}"
            setNavigationOnClickListener { findNavController().navigateUp() }
        }

        // Set up form fields based on plan type
        setupFormFields(args.planType)
        savePlan()
        binding.createPlanButton.setOnClickListener {

            findNavController().navigate(R.id.action_createPlanFragment_to_homeFragment)
        }
    }

    private fun savePlan() {
        val planDetails = mutableMapOf<String, Any>()
        binding.dynamicFieldsContainer.children.forEach { view ->
            val inputField = view.findViewById<EditText>(R.id.text_input_edit_text)
            val hint = view.findViewById<TextInputLayout>(R.id.text_input_layout).hint.toString()
            planDetails[hint] = inputField.text.toString()
        }
        val type = getPlanTypeByNameRes(args.planType)
        val plan = Plan(
            id = UUID.randomUUID().toString(),
            itineraryId = args.itineraryId, // Recibido por Safe Args
            type = type ?: PlanType.FLIGHT,
            name = planDetails["Nombre"]?.toString() ?: "Plan",
            date = Timestamp.now(),
            details = planDetails
        )
        viewModel.savePlan(plan)
    }

    private fun getPlanTypeByNameRes(planNombre: String): PlanType? {
        return PlanType.entries.find { getString(it.nameRes) == planNombre }
    }

    private fun setupFormFields(planType: String) {
        when (planType) {
            getString(PlanType.FLIGHT.nameRes) -> setupFlightFields()
            getString(PlanType.ACCOMMODATION.nameRes) -> setupAccommodationFields()
            getString(PlanType.CAR_RENTAL.nameRes) -> setupCarRentalFields()
            getString(PlanType.MEETING.nameRes) -> setupMeetingFields()
            getString(PlanType.ACTIVITY.nameRes) -> setupActivityFields()
            getString(PlanType.RESTAURANT.nameRes) -> setupRestaurantFields()
            getString(PlanType.TRANSPORT.nameRes) -> setupTransportFields()
            else -> { /* Do nothing */
            }
        }
    }

    //Quisiera hacer que mi plan de Viaje que al seleccionarlo tenga un flujo, donde pasara a otra vista para ingresar
    private fun setupFlightFields() {
        binding.dynamicFieldsContainer.apply {
            addView(createInputField("Aerolínea"))
            addView(createInputField("Número de Vuelo"))
            addView(createInputField("Aeropuerto de Salida"))
            addView(createInputField("Aeropuerto de Llegada"))
            addView(createDateTimeField("Hora de Salida"))
            addView(createDateTimeField("Hora de Llegada"))
        }
    }

    private fun setupAccommodationFields() {
        binding.dynamicFieldsContainer.apply {
            addView(createInputField("Nombre del Hotel"))
            addView(createInputField("Dirección"))
            addView(createDateTimeField("Fecha de Check-in"))
            addView(createDateTimeField("Fecha de Check-out"))
            addView(createInputField("Tipo de Habitación"))
        }
    }

    private fun setupCarRentalFields() {
        binding.dynamicFieldsContainer.apply {
            addView(createInputField("Compañía de Alquiler"))
            addView(createInputField("Modelo del Coche"))
            addView(createDateTimeField("Fecha de Recogida"))
            addView(createDateTimeField("Fecha de Devolución"))
            addView(createInputField("Lugar de Recogida"))
            addView(createInputField("Lugar de Devolución"))
        }
    }

    private fun setupMeetingFields() {
        binding.dynamicFieldsContainer.apply {
            addView(createInputField("Título de la Reunión"))
            addView(createInputField("Ubicación"))
            addView(createDateTimeField("Fecha y Hora"))
            addView(createInputField("Asistentes"))
        }
    }

    private fun setupActivityFields() {
        binding.dynamicFieldsContainer.apply {
            addView(createInputField("Nombre de la Actividad"))
            addView(createInputField("Ubicación"))
            addView(createDateTimeField("Fecha y Hora"))
            addView(createInputField("Duración"))
        }
    }

    private fun setupRestaurantFields() {
        binding.dynamicFieldsContainer.apply {
            addView(createInputField("Nombre del Restaurante"))
            addView(createInputField("Dirección"))
            addView(createDateTimeField("Fecha y Hora"))
            addView(createInputField("Tipo de Cocina"))
        }
    }

    private fun setupTransportFields() {
        binding.dynamicFieldsContainer.apply {
            addView(createInputField("Tipo de Transporte"))
            addView(createInputField("Desde"))
            addView(createInputField("Hacia"))
            addView(createDateTimeField("Hora de Salida"))
            addView(createDateTimeField("Hora de Llegada"))
        }
    }


    private fun createInputField(hint: String): View {
        val textInputLayout = layoutInflater.inflate(
            R.layout.item_text_input,
            binding.dynamicFieldsContainer,
            false
        ) as TextInputLayout
        textInputLayout.hint = hint
        return textInputLayout
    }

    private fun createDateTimeField(hint: String): View {
        val textInputLayout = layoutInflater.inflate(
            R.layout.item_text_input,
            binding.dynamicFieldsContainer,
            false
        ) as TextInputLayout
        textInputLayout.hint = hint
        val editText = textInputLayout.editText
        editText?.inputType = InputType.TYPE_NULL
        editText?.setOnClickListener {
            showDateTimePicker(editText)
        }
        return textInputLayout
    }

    private fun showDateTimePicker(editText: EditText) {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(requireContext(), { _, year, month, day ->
            TimePickerDialog(requireContext(), { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                editText.setText(pickedDateTime.time.format())
            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
    }

    private fun Date.format(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return formatter.format(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}