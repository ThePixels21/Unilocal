package co.edu.eam.proyectounilocal.fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.actividades.BusquedaActivity
import co.edu.eam.proyectounilocal.actividades.MainActivity
import co.edu.eam.proyectounilocal.adapter.LugarAdapter
import co.edu.eam.proyectounilocal.bd.Lugares
import co.edu.eam.proyectounilocal.databinding.FragmentMisLugaresBinding
import co.edu.eam.proyectounilocal.modelo.Lugar

class MisLugaresFragment : Fragment() {

    lateinit var binding: FragmentMisLugaresBinding
    private var lista : ArrayList<Lugar> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMisLugaresBinding.inflate(inflater, container, false)

        binding.btnVolver.setOnClickListener{ requireActivity().supportFragmentManager.beginTransaction().replace( R.id.contenido_principal, InicioFragment() )
            .addToBackStack(MainActivity.MENU_INICIO).commit()
        MainActivity.binding.menuInferior.menu.getItem(0).isChecked = true}

        binding.btnSearch.setOnClickListener { startActivity(Intent(requireActivity(), BusquedaActivity::class.java)) }

        binding.btnNuevoLugar.setOnClickListener { requireActivity().supportFragmentManager.beginTransaction().replace( R.id.contenido_principal, CrearLugarFragment() )
            .addToBackStack(MainActivity.MENU_CREAR_LUGAR).commit() }

        val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigoUsuario = sp.getInt("codigo_usuario", -1)
        if(codigoUsuario != -1){
            lista = Lugares.listarPorPropietario(codigoUsuario)
            val adapter = LugarAdapter(lista, codigoUsuario)
            binding.listaMisLugares.adapter = adapter
            binding.listaMisLugares.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }

        return binding.root
    }

}