package co.edu.eam.proyectounilocal.fragmentos

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.adapter.ViewPagerAdapterImagenes
import co.edu.eam.proyectounilocal.bd.CategoriasService
import co.edu.eam.proyectounilocal.bd.LugaresService
import co.edu.eam.proyectounilocal.databinding.FragmentInfoLugarBinding
import co.edu.eam.proyectounilocal.modelo.DiaSemana
import co.edu.eam.proyectounilocal.modelo.Lugar
import co.edu.eam.proyectounilocal.modelo.Posicion
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Timer
import java.util.TimerTask

class InfoLugarFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding: FragmentInfoLugarBinding
    var codigoLugar: String = ""
    private var lugar: Lugar? = null

    private var currentPage = 0

    lateinit var gMap: GoogleMap

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
        binding = FragmentInfoLugarBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapa_direccion_lugar) as SupportMapFragment
        mapFragment.getMapAsync(this)

        LugaresService.obtener(codigoLugar){lug ->
            lugar = lug
            if(lugar != null){
                //Cargar info layout
                cargarInformacion(lugar!!)
            }
        }

        return binding.root
    }

    fun cargarInformacion(lugar: Lugar){

        //Cargar imágenes
        if(lugar.imagenes.isNotEmpty()){
            val adapter = ViewPagerAdapterImagenes(requireActivity(), lugar.imagenes)
            binding.listaImgs.adapter = adapter

            val handler = Handler(Looper.getMainLooper())
            val update = Runnable {
                val itemCount = adapter.itemCount
                if (currentPage == itemCount) {
                    currentPage = 0
                }
                binding.listaImgs.setCurrentItem(currentPage++, true)
            }
            val timer = Timer() // Esto creará un nuevo Timer
            timer.schedule(object : TimerTask() { // tarea a ejecutar
                override fun run() {
                    handler.post(update)
                }
            }, DELAY_MS, PERIOD_MS)
        }

        //Cargar dirección y mapa
        binding.direccionLugar.text = lugar.direccion
        val pos = LatLng(lugar.posicion.lat, lugar.posicion.lng)
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 18f))
        gMap.addMarker( MarkerOptions().position(pos).title(lugar.nombre))

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
            telefonos = getString(R.string.no_tiene_telefonos_contac)
        }
        binding.contactoLugar.text = telefonos

        CategoriasService.obtener(lugar.keyCategoria){categoria ->
            if(categoria != null){
                binding.iconoCategoria.text = categoria.icono
                binding.categoriaLugar.text = categoria.nombre
            }
        }

        //colores estado
        val abierto = lugar.estaAbierto()
        if(abierto){
            binding.estado.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            binding.estado.text = getString(R.string.txt_abierto)
        }else{
            binding.estado.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            binding.estado.text = getString(R.string.cerrado)
        }

    }

    companion object{
        private const val DELAY_MS: Long = 0 //delay en milisegundos antes de que la tarea se ejecute por primera vez
        private const val PERIOD_MS: Long = 3500 //tiempo en milisegundos entre invocaciones sucesivas de la tarea
        fun newInstance(codigoLugar:String):InfoLugarFragment{
            val args = Bundle()
            args.putString("id_lugar", codigoLugar)
            val fragmento = InfoLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        gMap.uiSettings.isRotateGesturesEnabled = false
        gMap.uiSettings.isScrollGesturesEnabled = false
        gMap.uiSettings.isZoomGesturesEnabled = true
        gMap.uiSettings.isZoomControlsEnabled = true
    }
}