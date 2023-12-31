package co.edu.eam.proyectounilocal.fragmentos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.eam.proyectounilocal.actividades.BusquedaActivity
import co.edu.eam.proyectounilocal.actividades.CuentaActivity
import co.edu.eam.proyectounilocal.databinding.FragmentBuscadorBinding


class BuscadorFragment : Fragment() {

    lateinit var binding: FragmentBuscadorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuscadorBinding.inflate(inflater, container, false)

        binding.imgUsuario.setOnClickListener { redireccionarCuenta() }
        binding.buscador.setOnClickListener { startActivity(Intent(requireActivity(), BusquedaActivity::class.java)) }

        return binding.root
    }

    fun redireccionarCuenta(){
        startActivity(Intent(requireActivity(), CuentaActivity::class.java))
    }

}