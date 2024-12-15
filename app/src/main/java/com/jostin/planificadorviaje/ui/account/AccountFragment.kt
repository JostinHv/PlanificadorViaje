package com.jostin.planificadorviaje.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.data.local.AppDatabase
import com.jostin.planificadorviaje.data.local.LocalDataSource
import com.jostin.planificadorviaje.data.repository.UserRepository
import com.jostin.planificadorviaje.data.model.User
import com.jostin.planificadorviaje.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding

    // Inicializa el ViewModel con un repositorio local
    private val viewModel: AccountViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        val localDataSource = LocalDataSource(database)
        AccountViewModelFactory(localDataSource)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeViewModel()

        // Cargar el usuario actual
        viewModel.loadUser()
    }

    private fun setupViews() {
        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Actualiza el usuario en el repositorio
            viewModel.updateUser(User(name = name, email = email))
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout(requireContext()) // Pasa el contexto para limpiar la sesión
            Toast.makeText(requireContext(), "Cierre de sesión exitoso", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_accountFragment_to_loginFragment)
        }
    }

    private fun observeViewModel() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.nameEditText.setText(it.name)
                binding.emailEditText.setText(it.email)
            }
        }
    }
}
