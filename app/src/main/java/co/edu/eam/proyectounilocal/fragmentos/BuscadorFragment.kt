package co.edu.eam.proyectounilocal.fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.actividades.LoginActivity
import co.edu.eam.proyectounilocal.databinding.FragmentBuscadorBinding
import co.edu.eam.proyectounilocal.databinding.FragmentInicioBinding


class BuscadorFragment : Fragment() {

    lateinit var binding: FragmentBuscadorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuscadorBinding.inflate(inflater, container, false)

        binding.imgUsuario.setOnClickListener { cerrarSesion() }

        return binding.root
    }

    fun cerrarSesion(){
        val sh = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE).edit()
        sh.clear()
        sh.commit()
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
    }

}