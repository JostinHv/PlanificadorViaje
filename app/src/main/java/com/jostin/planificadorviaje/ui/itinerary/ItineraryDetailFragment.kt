package com.jostin.planificadorviaje.ui.itinerary

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.data.model.City
import com.jostin.planificadorviaje.databinding.FragmentItineraryDetailBinding
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.ui.adapter.PlanAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ItineraryDetailFragment : Fragment() {

    private var _binding: FragmentItineraryDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ItineraryDetailFragmentArgs by navArgs()
    private lateinit var planAdapter: PlanAdapter
    private val viewModel: ItineraryDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItineraryDetailBinding.inflate(inflater, container, false)
        // Set up RecyclerView
        planAdapter = PlanAdapter(emptyList()) { plan, isSelected ->
            toggleDeleteButtonVisibility()
        }
        binding.recyclerViewPlans.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = planAdapter
        }
        loadItineraryDetails()
        return binding.root
    }

    private fun toggleDeleteButtonVisibility() {
        val hasSelectedPlans = planAdapter.getSelectedPlans().isNotEmpty()
        binding.fabDeletePlans.visibility = if (hasSelectedPlans) View.VISIBLE else View.GONE
    }


    private fun deleteSelectedPlans() {
        val selectedPlans = planAdapter.getSelectedPlans()
        if (selectedPlans.isEmpty()) {
            Toast.makeText(requireContext(), "No hay planes seleccionados", Toast.LENGTH_SHORT)
                .show()
            return
        }

        // Lógica para eliminar planes seleccionados
        viewModel.deletePlans(selectedPlans) {
            Toast.makeText(
                requireContext(),
                "Planes eliminados satisfactoriamente",
                Toast.LENGTH_SHORT
            )
                .show()
            viewModel.loadItineraryDetails(args.itineraryId)
            loadItineraryDetails()
            toggleDeleteButtonVisibility() // Actualiza visibilidad después de eliminar
        }
    }

    private fun setupFabDeletePlans() {
        binding.fabDeletePlans.setOnClickListener {
            deleteSelectedPlans()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        observeViewModel()
        setupFabDeletePlans()
        viewModel.loadItineraryDetails(args.itineraryId)
        setupFab()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_itineraryDetailFragment_to_homeFragment)
        }
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_delete -> {
                    showDeleteConfirmationDialog()
                    true
                }

                else -> false
            }
        }
    }

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.confirm_delete))
        builder.setMessage(getString(R.string.confirm_delete_message))
        builder.setPositiveButton(getString(R.string.delete)) { _, _ ->
            deleteItinerary()
        }
        builder.setNegativeButton(getString(R.string.cancel), null)
        builder.show()
    }

    private fun deleteItinerary() {
        viewModel.deleteItinerary(args.itineraryId,
            onSuccess = {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.itinerary_deleted),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigateUp()
            },
            onError = { e ->
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_deleting_itinerary),
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("ItineraryDetail", "Error deleting itinerary", e)
            }
        )
    }


    private fun observeViewModel() {
        viewModel.itinerary.observe(viewLifecycleOwner) { itinerary ->
            displayItineraryDetails(itinerary)
        }
    }

    private fun loadItineraryDetails() {

        viewModel.getItineraryDetails(args.itineraryId)
            .observe(viewLifecycleOwner) { itineraryWithPlans ->
                val itinerary = itineraryWithPlans.itinerary
                binding.textViewItineraryName.text = itinerary.name
                binding.textViewDestination.text = itinerary.destination
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                binding.textViewDateRange.text =
                    "${dateFormat.format(itinerary.startDate)} - ${dateFormat.format(itinerary.endDate)}"
                binding.textViewDescription.text = itinerary.description
                binding.cardViewDescription.visibility =
                    if (itinerary.description.isEmpty()) View.GONE else View.VISIBLE
                // Configure shared users
                binding.travelersChipGroup.removeAllViews()
                itinerary.sharedWith.forEach { user ->
                    val chip = Chip(requireContext()).apply {
                        text = user.name
                        isCheckable = false
                    }
                    binding.travelersChipGroup.addView(chip)
                }
                // Update the plans in the adapter
                planAdapter = PlanAdapter(itineraryWithPlans.plans) { _, _ ->
                    toggleDeleteButtonVisibility() // Actualiza visibilidad al seleccionar/deseleccionar
                }
                binding.recyclerViewPlans.adapter = planAdapter
                toggleDeleteButtonVisibility() // Refresca visibilidad al cargar
            }
    }

    private fun displayItineraryDetails(itinerary: Itinerary) {
        binding.apply {
            textViewItineraryName.text = itinerary.name
            textViewDestination.text = itinerary.destination
            textViewDateRange.text = formatDateRange(itinerary.startDate, itinerary.endDate)
            textViewDescription.text = itinerary.description

            // Configure shared users
            travelersChipGroup.removeAllViews()
            itinerary.sharedWith.forEach { user ->
                val chip = Chip(requireContext()).apply {
                    text = user.name
                    isCheckable = false
                }
                travelersChipGroup.addView(chip)
            }
        }
    }

    private fun setupFab() {
        binding.fabAddPlan.setOnClickListener {
            val cityLiveData = viewModel.getCityByName(binding.textViewDestination.text.toString())
            cityLiveData.observe(viewLifecycleOwner) { city: City? ->
                if (city != null) {
                    findNavController().navigate(
                        ItineraryDetailFragmentDirections.actionItineraryDetailFragmentToSelectPlanTypeFragment(
                            args.itineraryId, city
                        )
                    )
                } else {
                    Log.e("City", "City not found")
                }
            }
        }
    }

    private fun formatDateRange(startDate: Date, endDate: Date): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return "${dateFormat.format(startDate)} - ${dateFormat.format(endDate)}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
