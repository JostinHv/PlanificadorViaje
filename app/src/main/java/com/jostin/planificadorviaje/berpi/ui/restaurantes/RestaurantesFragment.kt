package com.jostin.planificadorviaje.berpi.ui.restaurantes

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jostin.planificadorviaje.R
import com.jostin.planificadorviaje.berpi.adapter.AdminRestauranteAdapter
import com.jostin.planificadorviaje.berpi.model.AdminRestaurante
import com.jostin.planificadorviaje.berpi.utils.Resource
import com.jostin.planificadorviaje.databinding.FragmentRestaurantesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantesFragment : Fragment() {

    private var _binding: FragmentRestaurantesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdminRestauranteViewModel by viewModels()
    private val restauranteAdapter = AdminRestauranteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        // Configurar FAB para agregar restaurante
        binding.fabAddRestaurant.setOnClickListener {
            showAddRestaurantDialog()
        }

        // Configurar el SearchView
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.fetchRestaurantesByCity(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = restauranteAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.restaurantes.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    restauranteAdapter.submitList(resource.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showAddRestaurantDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_restaurant, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Agregar Restaurante")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = dialogView.findViewById<EditText>(R.id.etRestaurantName).text.toString()
                val ciudad = dialogView.findViewById<EditText>(R.id.etRestaurantCity).text.toString()
                val precio = dialogView.findViewById<EditText>(R.id.etRestaurantPrice).text.toString().toDoubleOrNull()
                val puntaje = dialogView.findViewById<EditText>(R.id.etRestaurantRating).text.toString().toDoubleOrNull()
                val imagen = dialogView.findViewById<EditText>(R.id.etRestaurantImage).text.toString()

                if (nombre.isNotEmpty() && ciudad.isNotEmpty() && precio != null && puntaje != null) {
                    addResturanteToFirestore(nombre, ciudad, precio, imagen, puntaje)
                } else {
                    Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }

    private fun addResturanteToFirestore(nombre: String, ciudad: String, precio: Double, imagen: String, puntaje: Double) {
        viewModel.getLastRestauranteId { lastId ->
            val nextId = (lastId + 1).toString() // Incrementar el último ID

            val restaurante = AdminRestaurante(
                id = nextId, // Asignar el próximo ID
                nombre = nombre,
                ciudad = ciudad,
                precio = precio,
                imagen = imagen,
                puntaje = puntaje
            )

            viewModel.addRestaurant(restaurante)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}