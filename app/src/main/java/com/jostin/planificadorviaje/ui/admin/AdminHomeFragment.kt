package com.jostin.planificadorviaje.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.databinding.FragmentAdminHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class AdminHomeFragment : Fragment() {

    private var _binding: FragmentAdminHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navegación al fragmento de hoteles
        binding.control01.setOnClickListener {
            findNavController().navigate(R.id.action_adminHomeFragment_to_adminHotelFragment)
        }

        // Navegación al fragmento de restaurantes
        binding.control02.setOnClickListener {
            findNavController().navigate(R.id.action_adminHomeFragment_to_restaurantesFragment)
        }

        binding.btnLogout.setOnClickListener {
            findNavController().navigate(R.id.action_adminHomeFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}