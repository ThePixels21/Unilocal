package co.edu.eam.proyectounilocal.fragmentos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.adapter.BusquedasRecientesAdapter
import co.edu.eam.proyectounilocal.bd.Usuarios
import co.edu.eam.proyectounilocal.databinding.FragmentBusquedasRecientesBinding

class BusquedasRecientesFragment : Fragment() {

    lateinit var binding: FragmentBusquedasRecientesBinding
    private var lista : ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusquedasRecientesBinding.inflate(inflater, container, false)

        val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigoUsuario = sp.getInt("codigo_usuario", -1)
        if(codigoUsuario != -1){
            val usuario = Usuarios.buscar(codigoUsuario)
            if(usuario != null){
                lista = usuario.busquedasRecientes
                val adapter = BusquedasRecientesAdapter(lista)
                binding.listaBusquedasRecientes.adapter = adapter
                binding.listaBusquedasRecientes.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)
            }
        }

        return binding.root
    }

}