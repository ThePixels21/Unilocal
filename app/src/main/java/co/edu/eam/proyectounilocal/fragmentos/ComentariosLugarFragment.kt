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
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.actividades.DetalleLugarActivity
import co.edu.eam.proyectounilocal.adapter.ComentariosAdapter
import co.edu.eam.proyectounilocal.adapter.ViewPagerAdapterLugar
import co.edu.eam.proyectounilocal.bd.Comentarios
import co.edu.eam.proyectounilocal.bd.Lugares
import co.edu.eam.proyectounilocal.databinding.FragmentComentariosLugarBinding
import co.edu.eam.proyectounilocal.modelo.Comentario
import co.edu.eam.proyectounilocal.modelo.Lugar
import com.google.android.material.tabs.TabLayoutMediator

class ComentariosLugarFragment : Fragment() {

    lateinit var binding: FragmentComentariosLugarBinding
    private var lista : ArrayList<Comentario> = ArrayList()
    var codigoLugar: Int = -1
    var codigoUsuario: Int = -1
    private var lugar: Lugar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments != null){
            codigoLugar = requireArguments().getInt("id_lugar")
        }
        act = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComentariosLugarBinding.inflate(inflater, container, false)

        lugar = Lugares.obtener(codigoLugar)
        val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        codigoUsuario = sp.getInt("codigo_usuario", -1)

        if(lugar != null){
            binding.btnCalificar.setOnClickListener {
                if(codigoUsuario != -1 && !Comentarios.comentado(codigoLugar, codigoUsuario)){
                    DetalleLugarActivity.binding.viewPager.adapter =  ViewPagerAdapterLugar(requireActivity(), codigoLugar, 2)
                    DetalleLugarActivity.binding.viewPager.setCurrentItem(1)
                } else{
                    Toast.makeText(requireContext(), "No puede agregar más de un comentario", Toast.LENGTH_LONG).show()
                }
            }

            //Cargar info
            val cal: Int = lugar!!.obtenerCalificacionPromedio(Comentarios.listar(lugar!!.id))

            binding.calificacionPromedio.text = cal.toString()

            if(cal != 0){
                for (i in 0 until cal){
                    (binding.listaEstrellas[i] as TextView).setTextColor(ContextCompat.getColor(binding.listaEstrellas.context, R.color.yellow))
                }
            }

            lista = Comentarios.listar(codigoLugar)
            val adapter = ComentariosAdapter(lista, codigoUsuario)
            binding.listaComentarios.adapter = adapter
            binding.listaComentarios.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)
        }

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

        lateinit var act: FragmentActivity
    }
}