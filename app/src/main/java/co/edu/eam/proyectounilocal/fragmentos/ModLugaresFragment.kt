package co.edu.eam.proyectounilocal.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.adapter.LugaresModAdapter
import co.edu.eam.proyectounilocal.bd.Lugares
import co.edu.eam.proyectounilocal.databinding.FragmentModLugaresBinding
import co.edu.eam.proyectounilocal.modelo.EstadoLugar
import co.edu.eam.proyectounilocal.modelo.Lugar

class ModLugaresFragment : Fragment() {

    lateinit var binding: FragmentModLugaresBinding
    private var lista : ArrayList<Lugar> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModLugaresBinding.inflate(inflater, container, false)

        lista = Lugares.listarPorEstado(EstadoLugar.SIN_REVISAR)
        val adapter = LugaresModAdapter(lista)
        binding.listaLugaresSinRevision.adapter = adapter
        binding.listaLugaresSinRevision.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)

        return binding.root
    }

}