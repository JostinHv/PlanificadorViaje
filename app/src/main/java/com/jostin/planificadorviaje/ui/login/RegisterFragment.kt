package com.jostin.planificadorviaje.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jostin.planificadorviaje.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        // Cuando se hace clic en el botón de registro
        binding.btnRegisterUser.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Llamar al ViewModel para registrar al usuario en Firestore
                loginViewModel.registerUser(name, email, password)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Por favor complete todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        loginViewModel.registrationResult.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                // Si el registro es exitoso, volver al LoginFragment
                Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp() // Volver al LoginFragment
            } else {
                Toast.makeText(requireContext(), "Error al registrar usuario", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}