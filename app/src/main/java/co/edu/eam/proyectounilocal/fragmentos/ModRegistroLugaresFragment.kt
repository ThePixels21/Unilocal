package co.edu.eam.proyectounilocal.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.proyectounilocal.adapter.RegistroLugaresModAdapter
import co.edu.eam.proyectounilocal.bd.LugaresService
import co.edu.eam.proyectounilocal.databinding.FragmentModRegistroLugaresBinding

class ModRegistroLugaresFragment : Fragment() {

    lateinit var binding: FragmentModRegistroLugaresBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModRegistroLugaresBinding.inflate(inflater, container, false)

        LugaresService.obtenerRegistros { lista ->
            val adapter = RegistroLugaresModAdapter(lista)
            binding.registroLugares.adapter = adapter
            binding.registroLugares.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)
        }

        return binding.root
    }

}