package co.edu.eam.proyectounilocal.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.bd.Categorias
import co.edu.eam.proyectounilocal.bd.Lugares
import co.edu.eam.proyectounilocal.databinding.FragmentInfoLugarBinding
import co.edu.eam.proyectounilocal.modelo.Categoria
import co.edu.eam.proyectounilocal.modelo.DiaSemana
import co.edu.eam.proyectounilocal.modelo.Lugar

class InfoLugarFragment : Fragment() {

    lateinit var binding: FragmentInfoLugarBinding
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
        binding = FragmentInfoLugarBinding.inflate(inflater, container, false)

        lugar = Lugares.obtener(codigoLugar)

        if(lugar != null){
            //Cargar info layout
            cargarInformacion(lugar!!)
        }

        return binding.root
    }

    fun cargarInformacion(lugar: Lugar){

        binding.detalleLugar.text = lugar.descripcion

        for (horario in lugar.horarios){
            for(dia in horario.diaSemana){
                if(dia == DiaSemana.LUNES){
                    binding.horarioLunes.text = "${horario.horaInicio}:00 - ${horario.horaFinal}:00"
                } else if(dia == DiaSemana.MARTES){
                    binding.horarioMartes.text = "${horario.horaInicio}:00 - ${horario.horaFinal}:00"
                } else if(dia == DiaSemana.MIERCOLES){
                    binding.horarioMiercoles.text = "${horario.horaInicio}:00 - ${horario.horaFinal}:00"
                } else if (dia == DiaSemana.JUEVES){
                    binding.horarioJueves.text = "${horario.horaInicio}:00 - ${horario.horaFinal}:00"
                } else if (dia == DiaSemana.VIERNES){
                    binding.horarioViernes.text = "${horario.horaInicio}:00 - ${horario.horaFinal}:00"
                } else if (dia == DiaSemana.SABADO){
                    binding.horarioSabado.text = "${horario.horaInicio}:00 - ${horario.horaFinal}:00"
                } else  if (dia == DiaSemana.DOMINGO){
                    binding.horarioDomingo.text = "${horario.horaInicio}:00 - ${horario.horaFinal}:00"
                }
            }
        }

        var telefonos = ""
        if(lugar.telefonos.isNotEmpty()){
            for (i in 0 until lugar.telefonos.size){
                if(i < lugar.telefonos.size-1){
                    telefonos += "${lugar.telefonos.get(i)}\n"
                } else {
                    telefonos += "${lugar.telefonos.get(i)}"
                }
            }
        } else {
            telefonos = "No tiene telefonos de contacto"
        }
        binding.contactoLugar.text = telefonos

        val categoria = Categorias.obtener(lugar.idCategoria)
        binding.iconoCategoria.text = categoria!!.icono
        binding.categoriaLugar.text = categoria!!.nombre

        //colores estado
        val abierto = lugar.estaAbierto()
        if(abierto){
            binding.estado.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            binding.estado.text = "Abierto"
        }else{
            binding.estado.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            binding.estado.text = "Cerrado"
        }

    }

    companion object{
        fun newInstance(codigoLugar:Int):InfoLugarFragment{
            val args = Bundle()
            args.putInt("id_lugar", codigoLugar)
            val fragmento = InfoLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }
}