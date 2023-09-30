package co.edu.eam.proyectounilocal.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.bd.Lugares
import co.edu.eam.proyectounilocal.databinding.FragmentComentariosLugarBinding
import co.edu.eam.proyectounilocal.modelo.Lugar

class ComentariosLugarFragment : Fragment() {

    lateinit var binding: FragmentComentariosLugarBinding
    var codigoLugar: Int = -1
    private var lugar: Lugar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments != null){
            codigoLugar = requireArguments().getInt("id_lugar")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComentariosLugarBinding.inflate(inflater, container, false)

        lugar = Lugares.obtener(codigoLugar)

        //Cargar info

        return binding.root
    }

    companion object{
        fun newInstance(codigoLugar:Int):ComentariosLugarFragment{
            val args = Bundle()
            args.putInt("id_lugar", codigoLugar)
            val fragmento = ComentariosLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }
}