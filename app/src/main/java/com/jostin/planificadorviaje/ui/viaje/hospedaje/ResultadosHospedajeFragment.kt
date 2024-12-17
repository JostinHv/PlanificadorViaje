package com.jostin.planificadorviaje.ui.viaje.hospedaje

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jostin.planificadorviaje.databinding.FragmentResultadosHospedajeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultadosHospedajeFragment : Fragment() {
    private var _binding: FragmentResultadosHospedajeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HotelViewModel by viewModels()
    private lateinit var adapter: HotelesAdapter

    // Recibir los argumentos del fragmento anterior
    //private val args: ResultadosHospedajeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultadosHospedajeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        // Usar argumentos pasados
//        val checkInDate = args.checkInDate
//        val checkOutDate = args.checkOutDate
//        val personCount = args.personCount
//        val selectedClass = args.selectedClass
//
//        // Configurar el título de la barra de herramientas
//        binding.toolbar.title = "Resultados para $personCount persona(s)"
//
//        // Mostrar un mensaje con los detalles recibidos
//        Toast.makeText(
//            requireContext(),
//            "Fechas: $checkInDate - $checkOutDate, Clase: $selectedClass",
//            Toast.LENGTH_LONG
//        ).show()

        setupToolbar()
        setupRecyclerView()
        observeHoteles()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        // Configurar el adaptador para manejar los clics en los hoteles
        adapter = HotelesAdapter { hotel ->
            // Navegar al fragmento de Confirmar Reserva
            findNavController().navigate(
                ResultadosHospedajeFragmentDirections.actionToConfirmarReserva(hotel.id)
            )
        }

        // Asignar el adaptador al RecyclerView
        binding.hotelesRecyclerView.adapter = adapter
    }

    private fun observeHoteles() {
        viewLifecycleOwner.lifecycleScope.launch {
            // Observar la lista de hoteles desde el ViewModel
            viewModel.hoteles.collect { hoteles ->
                adapter.submitList(hoteles)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
