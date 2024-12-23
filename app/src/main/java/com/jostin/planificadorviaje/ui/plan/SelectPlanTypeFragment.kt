// SelectPlanTypeFragment.kt
package com.jostin.planificadorviaje.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jostin.planificadorviaje.data.model.PlanType
import com.jostin.planificadorviaje.databinding.FragmentSelectPlanTypeBinding

class SelectPlanTypeFragment : Fragment() {

    private var _binding: FragmentSelectPlanTypeBinding? = null
    private val binding get() = _binding!!
    private val args: SelectPlanTypeFragmentArgs by navArgs()

    private val allPlanTypes = listOf(
        PlanType.FLIGHT,
        PlanType.ACCOMMODATION,
        PlanType.CAR_RENTAL,
        PlanType.MEETING,
        PlanType.ACTIVITY,
        PlanType.RESTAURANT,
        PlanType.TRANSPORT,
        //PlanType.PACKAGE_TRIP,
    )

    private val popularPlanTypes = listOf(
        //PlanType.PACKAGE_TRIP,
        PlanType.ACCOMMODATION,
        PlanType.RESTAURANT,
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectPlanTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarSelectPlanType.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        setupPopularPlanTypes()
        setupAllPlanTypes()
    }

    private fun setupPopularPlanTypes() {
        val popularAdapter = PlanTypeAdapter(popularPlanTypes, ::onPlanTypeSelected)
        binding.popularPlanTypesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.popularPlanTypesRecyclerView.adapter = popularAdapter
    }

    private fun setupAllPlanTypes() {
        val allTypesAdapter = PlanTypeAdapter(allPlanTypes, ::onPlanTypeSelected)
        binding.planTypesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.planTypesRecyclerView.adapter = allTypesAdapter
    }

    private fun onPlanTypeSelected(planType: PlanType) {
        when (planType) {
            PlanType.PACKAGE_TRIP -> {
                val action = SelectPlanTypeFragmentDirections.actionSelectPlanTypeFragmentToViajesFragment()
                findNavController().navigate(action)
            }
            PlanType.RESTAURANT -> {
                val action = SelectPlanTypeFragmentDirections.actionSelectPlanTypeFragmentToRestaurantFormFragment(
                    args.itineraryId, args.city
                )
                findNavController().navigate(action)
            }
            PlanType.ACCOMMODATION -> {
                val action = SelectPlanTypeFragmentDirections.actionSelectPlanTypeFragmentToHotelFormFragment(
                    args.itineraryId, args.city
                )
                findNavController().navigate(action)
            }
            else -> {
                val action = SelectPlanTypeFragmentDirections.actionSelectPlanTypeFragmentToCreatePlanFragment(
                    getString(planType.nameRes),
                    args.itineraryId
                )
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
