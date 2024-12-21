package com.jostin.planificadorviaje.berpi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.berpi.utils.Resource
import com.jostin.planificadorviaje.berpi.adapter.AdminHotelAdapter
import com.jostin.planificadorviaje.berpi.model.AdminHotel
import com.jostin.planificadorviaje.databinding.FragmentAdminHotelBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminHotelFragment : Fragment() {
    private var _binding: FragmentAdminHotelBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdminHotelViewModel by viewModels()
    private val hotelAdapter = AdminHotelAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.fetchHotelsByCity(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.fabAddHotel.setOnClickListener {
            showAddHotelDialog()
        }
    }


    private fun showAddHotelDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_hotel, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Agregar Hotel")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = dialogView.findViewById<EditText>(R.id.etHotelName).text.toString()
                val ciudad = dialogView.findViewById<EditText>(R.id.etHotelCity).text.toString()
                val precio = dialogView.findViewById<EditText>(R.id.etHotelPrice).text.toString().toDoubleOrNull()
                val imagen = dialogView.findViewById<EditText>(R.id.etHotelImage).text.toString()
                val puntaje = dialogView.findViewById<EditText>(R.id.etHotelPuntaje).text.toString().toDoubleOrNull()

                if (nombre.isNotEmpty() && ciudad.isNotEmpty() && precio != null && puntaje != null) {
                    addHotelToFirestore(nombre, ciudad, precio, imagen, puntaje)
                } else {
                    Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }

    private fun addHotelToFirestore(nombre: String, ciudad: String, precio: Double, imagen: String, puntaje: Double) {
        viewModel.getLastHotelId { lastId ->
            val nextId = (lastId + 1).toString() // Incrementar el último ID

            val hotel = AdminHotel(
                id = nextId, // Asignar el próximo ID
                nombre = nombre,
                ciudad = ciudad,
                precio = precio,
                imagen = imagen,
                puntaje = puntaje
            )

            viewModel.addHotel(hotel)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = hotelAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.hotels.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    hotelAdapter.submitList(resource.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}