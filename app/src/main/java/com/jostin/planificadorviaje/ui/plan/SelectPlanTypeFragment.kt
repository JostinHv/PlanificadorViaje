// SelectPlanTypeFragment.kt
package com.jostin.planificadorviaje.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.jostin.planificadorviaje.data.model.PlanType
import com.jostin.planificadorviaje.databinding.FragmentSelectPlanTypeBinding

class SelectPlanTypeFragment : Fragment() {

    private var _binding: FragmentSelectPlanTypeBinding? = null
    private val binding get() = _binding!!

    private val planTypes = listOf(
        PlanType.FLIGHT,
        PlanType.ACCOMMODATION,
        PlanType.CAR_RENTAL,
        PlanType.MEETING,
        PlanType.ACTIVITY,
        PlanType.RESTAURANT,
        PlanType.TRANSPORT
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSelectPlanTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarSelectPlanType.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val adapter = PlanTypeAdapter(planTypes) { planType: PlanType ->
            val action = SelectPlanTypeFragmentDirections.actionSelectPlanTypeFragmentToCreatePlanFragment(
                getString(planType.nameRes)
            )
            findNavController().navigate(action)
        }

        binding.planTypesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.planTypesRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
