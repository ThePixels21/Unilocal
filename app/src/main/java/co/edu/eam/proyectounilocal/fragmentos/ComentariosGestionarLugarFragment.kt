package co.edu.eam.proyectounilocal.fragmentos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.adapter.ComentariosAdapter
import co.edu.eam.proyectounilocal.bd.Comentarios
import co.edu.eam.proyectounilocal.bd.Lugares
import co.edu.eam.proyectounilocal.bd.LugaresService
import co.edu.eam.proyectounilocal.databinding.FragmentComentariosGestionarLugarBinding
import co.edu.eam.proyectounilocal.modelo.Comentario

class ComentariosGestionarLugarFragment : Fragment() {

    lateinit var binding: FragmentComentariosGestionarLugarBinding
    private var lista : ArrayList<Comentario> = ArrayList()
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
        binding = FragmentComentariosGestionarLugarBinding.inflate(inflater, container, false)

        val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigoUsuario = sp.getInt("codigo_usuario", -1)

        LugaresService.obtener(codigoLugar){lug ->
            val lugar = lug
            if(lugar != null){
                //Cargar info
                val cal: Int = lugar.obtenerCalificacionPromedio(Comentarios.listar(lugar.id))
                binding.calificacionPromedio.text = cal.toString()

                if(cal != 0){
                    for (i in 0 until cal){
                        (binding.listaEstrellas[i] as TextView).setTextColor(ContextCompat.getColor(binding.listaEstrellas.context, R.color.yellow))
                    }
                }

                //ARREGLAR
                lista = Comentarios.listar(0)
                val adapter = ComentariosAdapter(lista, codigoUsuario)
                binding.listaComentarios.adapter = adapter
                binding.listaComentarios.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)

                binding.cantComentarios.text = "(${lista.size})"
            }
        }

        return binding.root
    }

    companion object{
        fun newInstance(codigoLugar:String):ComentariosGestionarLugarFragment{
            val args = Bundle()
            args.putString("id_lugar", codigoLugar)
            val fragmento = ComentariosGestionarLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }

}