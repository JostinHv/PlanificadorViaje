package com.jostin.planificadorviaje.ui.login

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var loadingDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        // Referencias a la ImageView y al TextView
        val ivLogo = binding.ivLogo
        val tvWelcomeAdmin = binding.tvWelcomeAdmin
        val btnRegister = binding.btnRegister


        // Toggle visibility cuando se presiona la imagen
        ivLogo.setOnClickListener {
            // Alternar visibilidad del TextView
            if (tvWelcomeAdmin.visibility == View.GONE) {
                tvWelcomeAdmin.visibility = View.VISIBLE
                btnRegister.visibility = View.GONE
            } else {
                tvWelcomeAdmin.visibility = View.GONE
                btnRegister.visibility = View.VISIBLE
            }
        }


        // Configurar el botón de inicio de sesión
        binding.btnLogin.setOnClickListener {
            val correo = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            if (correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Por favor, llena todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                showLoadingDialog()
                loginViewModel.login(correo, password, requireContext())
            }
        }

        // Configurar el botón de registro
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }


        // Observa los resultados del inicio de sesión
        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer { rol ->
            hideLoadingDialog()

            when (rol) {
                "Usuario" -> {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    Toast.makeText(requireContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT)
                        .show()
                }

                "admin" -> {
                    findNavController().navigate(R.id.action_loginFragment_to_adminHomeFragment)
                    Toast.makeText(
                        requireContext(),
                        "Inicio de sesión como administrador exitoso",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    // Si el rol es null o no es válido, muestra un mensaje de error
                    Toast.makeText(requireContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoadingDialog() {
        loadingDialog = Dialog(requireContext()).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.login_dialog_loading)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            show()
        }
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }
}
