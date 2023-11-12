package co.edu.eam.proyectounilocal.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.adapter.ImagenesAdapter
import co.edu.eam.proyectounilocal.bd.LugaresService
import co.edu.eam.proyectounilocal.databinding.FragmentImagenesLugarBinding

class ImagenesLugarFragment : Fragment() {

    lateinit var binding: FragmentImagenesLugarBinding
    var codigoLugar: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            codigoLugar = requireArguments().getString("id_lugar", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagenesLugarBinding.inflate(inflater, container, false)

        LugaresService.obtener(codigoLugar){lugar ->
            if(lugar != null){
                if(lugar.imagenes.isNotEmpty()){
                    val adapter = ImagenesAdapter(lugar.imagenes)
                    binding.listaImagenes.adapter = adapter
                    binding.listaImagenes.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                }
            }
        }

        return binding.root
    }

    companion object{
        fun newInstance(codigoLugar:String):ImagenesLugarFragment{
            val args = Bundle()
            args.putString("id_lugar", codigoLugar)
            val fragmento = ImagenesLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }
}