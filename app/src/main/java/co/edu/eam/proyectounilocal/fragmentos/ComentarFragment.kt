package co.edu.eam.proyectounilocal.fragmentos

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.actividades.DetalleLugarActivity
import co.edu.eam.proyectounilocal.adapter.ViewPagerAdapterLugar
import co.edu.eam.proyectounilocal.bd.Comentarios
import co.edu.eam.proyectounilocal.bd.Lugares
import co.edu.eam.proyectounilocal.databinding.FragmentComentarBinding
import co.edu.eam.proyectounilocal.modelo.Comentario
import co.edu.eam.proyectounilocal.modelo.Lugar

class ComentarFragment : Fragment() {

    lateinit var binding: FragmentComentarBinding
    var codigoLugar: Int = -1
    private var lugar: Lugar? = null
    var estrellas: Int = 0
    var colorPorDefecto: Int = 0

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
        binding = FragmentComentarBinding.inflate(inflater, container, false)

        lugar = Lugares.obtener(codigoLugar)

        if(lugar != null){
            binding.btnComentar.setOnClickListener {
                enviarComentario()
            }

            //Cargar datos
            colorPorDefecto = binding.e1.textColors.defaultColor
            for(i in 0 until binding.listaEstrellas.childCount){
                (binding.listaEstrellas[i] as TextView).setOnClickListener { presionarEstrella(i) }
            }
        }

        return binding.root
    }

    fun enviarComentario(){
        val texto = binding.comentarioLugar.text.toString()

        if(texto.isEmpty()){
            binding.comentarioLugar.error = "Campo obligatorio"
        } else {
            binding.comentarioLugar.error = null
        }

        if(texto.isNotEmpty()){
            val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
            val codigoUsuario = sp.getInt("codigo_usuario", -1)
            if(codigoUsuario != -1 && codigoLugar != -1){
                val comentario = Comentarios.crear(Comentario(texto, codigoUsuario, codigoLugar, estrellas))
                Toast.makeText(requireContext(), "Comentario enviado", Toast.LENGTH_LONG).show()
                DetalleLugarActivity.binding.viewPager.adapter =  ViewPagerAdapterLugar(requireActivity(), codigoLugar, 1)
                DetalleLugarActivity.binding.viewPager.setCurrentItem(1)
            } else {
                Toast.makeText(requireContext(), "No se pudo enviar el comentario", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun presionarEstrella(i: Int){
        estrellas = i+1
        borrarSeleccion()
        for (k in 0 .. i){
            (binding.listaEstrellas[k] as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
        }
    }

    fun borrarSeleccion(){
        for(i in 0 until binding.listaEstrellas.childCount){
            (binding.listaEstrellas[i] as TextView).setTextColor(colorPorDefecto)
        }
    }

    companion object{
        fun newInstance(codigoLugar:Int):ComentarFragment{
            val args = Bundle()
            args.putInt("id_lugar", codigoLugar)
            val fragmento = ComentarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }

}