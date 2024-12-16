package com.jostin.planificadorviaje.ui.viaje.opciones

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jostin.planificadorviaje.data.model.OpcionViaje
import com.jostin.planificadorviaje.databinding.FragmentViajeOpcionesBinding
import com.jostin.planificadorviaje.ui.viaje.adapter.OpcionesAdapter

class ViajeOpcionesFragment : Fragment() {

    private var _binding: FragmentViajeOpcionesBinding? = null
    private val binding get() = _binding!!
    private val args: ViajeOpcionesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViajeOpcionesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar Toolbar
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // Configuración del RecyclerView
        binding.recyclerViewOpciones.layoutManager = LinearLayoutManager(requireContext())
        val adapter = OpcionesAdapter { opcion ->
            val action = ViajeOpcionesFragmentDirections
                .actionViajeOpcionesFragmentToHospedajeFragment(opcion.id)
            findNavController().navigate(action)
        }
        binding.recyclerViewOpciones.adapter = adapter

        // Cargar opciones
        loadOpciones(args.origen, args.destino, adapter)
    }


    private fun loadOpciones(origen: String, destino: String, adapter: OpcionesAdapter) {
        val opciones = listOf(
            OpcionViaje("1", "Viaje Directo", "$origen → $destino", "2h 15m", "S/. 50"),
            OpcionViaje("2", "Viaje con Escala", "$origen → $destino", "3h 30m", "S/. 40"),
            OpcionViaje("3", "Viaje Premium", "$origen → $destino", "1h 45m", "S/. 100")
        )

        // Enviar la lista al adaptador
        adapter.submitList(opciones)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
