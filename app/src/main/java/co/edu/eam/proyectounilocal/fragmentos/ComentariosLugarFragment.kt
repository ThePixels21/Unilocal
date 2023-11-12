package co.edu.eam.proyectounilocal.fragmentos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.actividades.DetalleLugarActivity
import co.edu.eam.proyectounilocal.adapter.ComentariosAdapter
import co.edu.eam.proyectounilocal.adapter.ViewPagerAdapterLugar
import co.edu.eam.proyectounilocal.bd.LugaresService
import co.edu.eam.proyectounilocal.databinding.FragmentComentariosLugarBinding
import co.edu.eam.proyectounilocal.modelo.Lugar
import com.google.firebase.auth.FirebaseAuth

class ComentariosLugarFragment : Fragment() {

    lateinit var binding: FragmentComentariosLugarBinding
    var codigoLugar: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments != null){
            codigoLugar = requireArguments().getString("id_lugar", "")
        }
        act = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComentariosLugarBinding.inflate(inflater, container, false)

        LugaresService.obtener(codigoLugar){lugar ->

            if(lugar != null){
                val user = FirebaseAuth.getInstance().currentUser
                if(user != null){
                    val codigoUsuario = user.uid
                    binding.btnCalificar.setOnClickListener {
                        LugaresService.tieneComentarios(codigoLugar, codigoUsuario){res ->
                            if(!res){
                                DetalleLugarActivity.binding.viewPager.adapter =  ViewPagerAdapterLugar(requireActivity(), codigoLugar, 2)
                                DetalleLugarActivity.binding.viewPager.setCurrentItem(1)
                            } else{
                                Toast.makeText(requireContext(), getString(R.string.no_puede_agregar_mas_de_un_comentario), Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    //Listar comentarios y estrellas
                    LugaresService.listarComentarios(codigoLugar){comentarios ->
                        if(comentarios.size > 0){
                            val adapter = ComentariosAdapter(comentarios, codigoUsuario, codigoLugar)
                            binding.listaComentarios.adapter = adapter
                            binding.listaComentarios.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)

                            binding.cantComentarios.text = "(${comentarios.size})"

                            //Cargar estrellas
                            val cal: Int = lugar!!.obtenerCalificacionPromedio(comentarios)

                            binding.calificacionPromedio.text = cal.toString()

                            if(cal != 0){
                                for (i in 0 until cal){
                                    (binding.listaEstrellas[i] as TextView).setTextColor(ContextCompat.getColor(binding.listaEstrellas.context, R.color.yellow))
                                }
                            }
                        }
                    }
                }
            }
        }

        return binding.root
    }

    companion object{
        fun newInstance(codigoLugar:String):ComentariosLugarFragment{
            val args = Bundle()
            args.putString("id_lugar", codigoLugar)
            val fragmento = ComentariosLugarFragment()
            fragmento.arguments = args
            return fragmento
        }

        lateinit var act: FragmentActivity
    }
}