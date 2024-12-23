package com.jostin.planificadorviaje.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.data.model.User
import com.jostin.planificadorviaje.databinding.FragmentAccountBinding
import com.jostin.planificadorviaje.databinding.DialogEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private val accountViewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeViewModel()
        accountViewModel.loadUser()
    }

    private fun setupViews() {
        binding.logoutButton.setOnClickListener {
            accountViewModel.logout(requireContext())
            Toast.makeText(requireContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_accountFragment_to_loginFragment)
        }

        binding.editProfileButton.setOnClickListener {
            showEditProfileDialog()
        }
    }

    private fun observeViewModel() {
        accountViewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                updateProfileInfo(it)
            }
        }

        accountViewModel.updateResult.observe(viewLifecycleOwner) { result ->
            if (result.success) {
                Toast.makeText(requireContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Error al actualizar: ${result.error}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateProfileInfo(user: User) {
        binding.nameText.text = "${user.name} ${user.lastname}"
        binding.roleText.text = user.role
        val itemBinding = binding.root.findViewById<View>(R.id.itemProfileInfo)

        // Actualizar los TextViews dentro de item_profile_info
        val fullNameText = itemBinding.findViewById<TextView>(R.id.fullNameText)
        val emailText = itemBinding.findViewById<TextView>(R.id.emailText)

        fullNameText.text = "${user.name} ${user.lastname}"
        emailText.text = user.email
    }

    private fun showEditProfileDialog() {
        val dialogBinding = DialogEditProfileBinding.inflate(layoutInflater)
        val currentUser = accountViewModel.user.value ?: return

        // Rellenar los datos actuales del usuario en el formulario del diálogo
        dialogBinding.nameInput.setText(currentUser.name)
        dialogBinding.lastnameInput.setText(currentUser.lastname)
        dialogBinding.emailInput.setText(currentUser.email)
        dialogBinding.passwordInput.setText("")
        // Crear y configurar el diálogo
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .create()

        // Configurar los botones del diseño del diálogo
        dialogBinding.saveButton.setOnClickListener {
            val updatedUser = currentUser.copy(
                id = currentUser.id,
                name = dialogBinding.nameInput.text.toString(),
                lastname = dialogBinding.lastnameInput.text.toString(),
                email = dialogBinding.emailInput.text.toString(),
                password = if (dialogBinding.passwordInput.text?.isNotEmpty() == true) {
                    dialogBinding.passwordInput.text.toString()
                } else {
                    currentUser.password
                },
                role = currentUser.role,
            )
            accountViewModel.updateUser(updatedUser, requireContext())
            updateProfileInfo(updatedUser)
            dialog.dismiss()
        }

        dialogBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        // Mostrar el diálogo
        dialog.show()
    }
}