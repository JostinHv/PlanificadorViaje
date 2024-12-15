package com.jostin.planificadorviaje.ui.plan

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.data.model.PlanType
import com.jostin.planificadorviaje.databinding.FragmentCreatePlanBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CreatePlanFragment : Fragment() {

    private var _binding: FragmentCreatePlanBinding? = null
    private val binding get() = _binding!!
    private val args: CreatePlanFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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

        binding.createPlanButton.setOnClickListener {
            // TODO: Validate and save plan
            findNavController().navigate(R.id.action_createPlanFragment_to_homeFragment)
        }
    }

    private fun setupFormFields(planType: String) {
        when (planType) {
            getString(PlanType.FLIGHT.nameRes) -> setupFlightFields()
            getString(PlanType.ACCOMMODATION.nameRes) -> setupAccommodationFields()
            getString(PlanType.CAR_RENTAL.nameRes)-> setupCarRentalFields()
            getString(PlanType.MEETING.nameRes)-> setupMeetingFields()
            getString(PlanType.ACTIVITY.nameRes) -> setupActivityFields()
            getString(PlanType.RESTAURANT.nameRes) -> setupRestaurantFields()
            getString(PlanType.TRANSPORT.nameRes) -> setupTransportFields()
        }
    }

    private fun setupFlightFields() {
        binding.dynamicFieldsContainer.apply {
            addView(createInputField("Airline"))
            addView(createInputField("Flight Number"))
            addView(createInputField("Departure Airport"))
            addView(createInputField("Arrival Airport"))
            addView(createDateTimeField("Departure Time"))
            addView(createDateTimeField("Arrival Time"))
        }
    }

    private fun setupAccommodationFields() {
        binding.dynamicFieldsContainer.apply {
            addView(createInputField("Hotel Name"))
            addView(createInputField("Address"))
            addView(createDateTimeField("Check-in Date"))
            addView(createDateTimeField("Check-out Date"))
            addView(createInputField("Room Type"))
        }
    }

    private fun setupCarRentalFields() {
        binding.dynamicFieldsContainer.apply {
            addView(createInputField("Rental Company"))
            addView(createInputField("Car Model"))
            addView(createDateTimeField("Pick-up Date"))
            addView(createDateTimeField("Drop-off Date"))
            addView(createInputField("Pick-up Location"))
            addView(createInputField("Drop-off Location"))
        }
    }

    private fun setupMeetingFields() {
        binding.dynamicFieldsContainer.apply {
            addView(createInputField("Meeting Title"))
            addView(createInputField("Location"))
            addView(createDateTimeField("Date and Time"))
            addView(createInputField("Attendees"))
        }
    }

    private fun setupActivityFields() {
        binding.dynamicFieldsContainer.apply {
            addView(createInputField("Activity Name"))
            addView(createInputField("Location"))
            addView(createDateTimeField("Date and Time"))
            addView(createInputField("Duration"))
        }
    }

    private fun setupRestaurantFields() {
        binding.dynamicFieldsContainer.apply {
            addView(createInputField("Restaurant Name"))
            addView(createInputField("Address"))
            addView(createDateTimeField("Date and Time"))
            addView(createInputField("Cuisine Type"))
        }
    }

    private fun setupTransportFields() {
        binding.dynamicFieldsContainer.apply {
            addView(createInputField("Transport Type"))
            addView(createInputField("From"))
            addView(createInputField("To"))
            addView(createDateTimeField("Departure Time"))
            addView(createDateTimeField("Arrival Time"))
        }
    }

    private fun createInputField(hint: String): View {
        val textInputLayout = layoutInflater.inflate(R.layout.item_text_input, binding.dynamicFieldsContainer, false) as TextInputLayout
        textInputLayout.hint = hint
        return textInputLayout
    }

    private fun createDateTimeField(hint: String): View {
        val textInputLayout = layoutInflater.inflate(R.layout.item_text_input, binding.dynamicFieldsContainer, false) as TextInputLayout
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