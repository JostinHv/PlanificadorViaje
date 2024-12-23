package com.jostin.planificadorviaje.ui.plan.hotel

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.chip.Chip
import com.google.firebase.Timestamp
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.model.Place
import com.jostin.planificadorviaje.model.Plan
import com.jostin.planificadorviaje.model.PlanType
import com.jostin.planificadorviaje.databinding.FragmentHotelFormBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HotelFormFragment : Fragment() {

    private val viewModel: HotelFormViewModel by viewModels()
    private lateinit var binding: FragmentHotelFormBinding
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val args: HotelFormFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotelFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupDatePickers()
        setupPlaceSelection()
        setupSaveButton()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun setupDatePickers() {
        binding.etCheckIn.setOnClickListener { showDatePicker(true) }
        binding.etCheckOut.setOnClickListener { showDatePicker(false) }
    }

    private fun showDatePicker(isCheckIn: Boolean) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                calendar.set(year, month, day)
                val date = calendar.time
                if (isCheckIn) {
                    viewModel.setCheckInDate(date)
                    binding.etCheckIn.setText(dateFormatter.format(date))
                } else {
                    viewModel.setCheckOutDate(date)
                    binding.etCheckOut.setText(dateFormatter.format(date))
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun setupPlaceSelection() {
        binding.btnAddPlace.setOnClickListener {
            findNavController().navigate(
                HotelFormFragmentDirections.actionHotelFormFragmentToHotelMapFragment(args.city)
            )
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.let { handle ->
            handle.getLiveData<Place>("selected_place").observe(viewLifecycleOwner) { place ->
                viewModel.setSelectedPlace(place)
            }
        }
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            if (validateForm()) {
                savePlan()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.selectedPlace.observe(viewLifecycleOwner) { place ->
            updatePlaceUI(place)
        }
    }

    private fun updatePlaceUI(place: Place?) {
        if (place != null) {
            binding.cvPlace.visibility = View.VISIBLE
            binding.tvHotelName.text = place.name
            binding.tvHotelAddress.text = place.address
            binding.rbHotelRating.rating = place.rating ?: 0f
            binding.tvHotelPrice.text =
                getString(R.string.price_per_night, place.price?.toLong() ?: 150)
            Glide.with(requireContext()).load(place.imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.hotel_placeholder).error(R.drawable.hotel_placeholder)
                .into(binding.ivHotelImage)
            // Actualizar las amenidades
            binding.chipGroupAmenities.removeAllViews() // Elimina los chips existentes
            place.details.forEach { amenity ->
                val chip = Chip(requireContext()).apply {
                    text = amenity
                    isClickable = false
                    isCheckable = false
                }
                binding.chipGroupAmenities.addView(chip) // Agrega el chip al ChipGroup
            }
            binding.btnAddPlace.text = getString(R.string.change_hotel)
        } else {
            binding.cvPlace.visibility = View.GONE
            binding.btnAddPlace.text = getString(R.string.add_hotel)
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (binding.etPlanName.text.isNullOrBlank()) {
            binding.tilPlanName.error = getString(R.string.error_plan_name_required)
            isValid = false
        } else {
            binding.tilPlanName.error = null
        }

        if (!viewModel.validateDates()) {
            Toast.makeText(
                context, getString(R.string.error_invalid_dates), Toast.LENGTH_SHORT
            ).show()
            isValid = false
        }

        if (viewModel.selectedPlace.value == null) {
            Toast.makeText(
                context, getString(R.string.error_hotel_required), Toast.LENGTH_SHORT
            ).show()
            isValid = false
        }

        return isValid
    }

    private fun savePlan() {
        val details = mutableMapOf<String, String>()

        viewModel.selectedPlace.value?.let { place ->
            details["Hotel"] = place.name
            details["Dirección"] = place.address
            details["Precio por Noche"] = place.price.toString()
        }

        viewModel.checkInDate.value?.let { checkInDate ->
            details["Fecha de Check-In"] = dateFormatter.format(checkInDate)
        }

        viewModel.checkOutDate.value?.let { checkOutDate ->
            details["Fecha de Check-Out"] = dateFormatter.format(checkOutDate)
        }

        val plan = Plan(
            id = UUID.randomUUID().toString(),
            type = PlanType.ACCOMMODATION,
            name = binding.etPlanName.text.toString(),
            date = Timestamp(viewModel.checkInDate.value ?: Date()), // Usa la fecha actual si es nulo
            details = details,
            itineraryId = args.itineraryId
        )
        viewModel.savePlan(plan)
        Toast.makeText(context, getString(R.string.plan_saved), Toast.LENGTH_SHORT).show()
        findNavController().navigate(
            HotelFormFragmentDirections.actionHotelFormFragmentToItineraryDetailFragment(args.itineraryId)
        )
    }
}
