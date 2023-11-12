package co.edu.eam.proyectounilocal.fragmentos

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.actividades.BusquedaActivity
import co.edu.eam.proyectounilocal.actividades.MainActivity
import co.edu.eam.proyectounilocal.actividades.PosicionLugarActivity
import co.edu.eam.proyectounilocal.bd.CategoriasService
import co.edu.eam.proyectounilocal.bd.CiudadesService
import co.edu.eam.proyectounilocal.bd.LugaresService
import co.edu.eam.proyectounilocal.databinding.FragmentCrearLugarBinding
import co.edu.eam.proyectounilocal.modelo.Categoria
import co.edu.eam.proyectounilocal.modelo.Ciudad
import co.edu.eam.proyectounilocal.modelo.DiaSemana
import co.edu.eam.proyectounilocal.modelo.EstadoLugar
import co.edu.eam.proyectounilocal.modelo.Horario
import co.edu.eam.proyectounilocal.modelo.Lugar
import co.edu.eam.proyectounilocal.modelo.Posicion
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.Date

class CrearLugarFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding: FragmentCrearLugarBinding
    lateinit var ciudades:ArrayList<Ciudad>
    lateinit var categorias:ArrayList<Categoria>

    private var tienePermiso = false

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    var codigoArchivo = 0
    var imagenes: ArrayList<String> = ArrayList()

    lateinit var dialog: Dialog

    private lateinit var mapLauncher: ActivityResultLauncher<Intent>
    lateinit var gMap: GoogleMap
    private var posicion:Posicion? = null

    var posCiudad:Int = -1
    var posCategoria:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permisoCamara()

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult() ) {
            onActivityResult(it.resultCode, it)
        }

        mapLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){result ->
            if(result.resultCode == Activity.RESULT_OK){
                val data = result.data
                val lat = data?.extras?.get("lat")
                var lng = data?.extras?.get("lng")
                if(lat is Double && lng is Double){
                    posicion = Posicion(lat, lng)
                    binding.btnSeleccionarUbicacion.visibility = View.GONE
                    binding.layoutMapa.visibility = View.VISIBLE
                    val pos = LatLng(posicion!!.lat, posicion!!.lng)
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 18f))
                    gMap.addMarker( MarkerOptions().position(pos).title("Aquí"))

                }
            }
        }
    }

    private fun onActivityResult(resultCode:Int, result: ActivityResult){
        if( resultCode == Activity.RESULT_OK ){
            setDialog(true)
            val fecha = Date()
            val storageRef = FirebaseStorage.getInstance()
                .reference
                .child("/p-${fecha.time}.jpg")
            if( codigoArchivo == 1 ){
                //Imagen capturada desde la cámara del celular
                val data = result.data?.extras
                if( data?.get("data") is Bitmap){
                    val imageBitmap = data?.get("data") as Bitmap
                    val baos = ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val data = baos.toByteArray()
                    storageRef.putBytes(data).addOnSuccessListener {
                        storageRef.downloadUrl.addOnSuccessListener {
                            dibujarImagen(it)
                            setDialog(false)
                        }.addOnFailureListener { setDialog(false) }
                    }.addOnFailureListener {
                        setDialog(false)
                        Snackbar.make(binding.root, "${it.message}", Snackbar.LENGTH_LONG).show()
                    }
                }

            }else if( codigoArchivo == 2 ){
                //Archivo seleccionado desde el almacenamiento
                val data = result.data
                if(data!=null){
                    val selectedImageUri: Uri? = data.data
                    if(selectedImageUri!=null){
                        storageRef.putFile(selectedImageUri).addOnSuccessListener {
                            storageRef.downloadUrl.addOnSuccessListener {
                                dibujarImagen(it)
                                setDialog(false)
                            }.addOnFailureListener { setDialog(false) }
                        }.addOnFailureListener {
                            setDialog(false)
                            Snackbar.make(binding.root, "${it.message}", Snackbar.LENGTH_LONG).show()
                        }
                    }else{
                        setDialog(false)
                    }
                }else{
                    setDialog(false)
                }

            }
        }
    }

    fun dibujarImagen(url: Uri){

        imagenes.add(url.toString())

        var imagen = ImageView(requireContext())
        imagen.layoutParams = LinearLayout.LayoutParams(300, 310)
        binding.imagenesSel.addView(imagen)

        Glide.with( requireContext() )
            .load(url.toString())
            .into(imagen)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCrearLugarBinding.inflate(inflater, container, false)
        binding.layoutMapa.visibility = View.GONE

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapa_crear_lugar) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setView(R.layout.dialogo_progreso)
        dialog = builder.create()

        CiudadesService.listar { lista ->
            ciudades = lista
            cargarCiudades()
        }
        CategoriasService.listar { lista ->
            if(lista.size>0){
                categorias = lista
                cargarCategorias()
            }
        }

        cargarHorarios()

        binding.btnCrearLugar.setOnClickListener { crearLugar() }

        binding.btnVolver.setOnClickListener { requireActivity().supportFragmentManager.beginTransaction().replace( R.id.contenido_principal, InicioFragment() )
            .addToBackStack(MainActivity.MENU_INICIO).commit()
            MainActivity.binding.menuInferior.menu.getItem(0).isChecked = true}

        binding.btnBuscar.setOnClickListener { startActivity(Intent(requireActivity(), BusquedaActivity::class.java)) }

        binding.btnSeleccionarUbicacion.setOnClickListener {
            val intent = Intent(requireActivity(), PosicionLugarActivity::class.java)
            mapLauncher.launch(intent)
        }

        binding.btnTomarFoto.setOnClickListener {
            if(tienePermiso){
                tomarFoto()
            } else {
                Toast.makeText(requireActivity(), "Otorgue acceso a la cámara", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnSelArchivo.setOnClickListener {
            seleccionarFoto()
        }

        return binding.root
    }

    fun tomarFoto(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                resultLauncher.launch(takePictureIntent)
                codigoArchivo = 1
            }
        }
    }

    fun seleccionarFoto(){
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        codigoArchivo = 2
        resultLauncher.launch(i)
    }

    fun cargarHorarios(){
        val lista: ArrayList<String> = ArrayList()
        lista.add(getString(R.string.txt_seleccione))
        for (i in 1..24){
            lista.add(i.toString())
        }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.inicioLunes.adapter = adapter
        binding.finalLunes.adapter = adapter
        binding.inicioMartes.adapter = adapter
        binding.finalMartes.adapter = adapter
        binding.inicioMiercoles.adapter = adapter
        binding.finalMiercoles.adapter = adapter
        binding.inicioJueves.adapter = adapter
        binding.finalJueves.adapter = adapter
        binding.inicioViernes.adapter = adapter
        binding.finalViernes.adapter = adapter
        binding.inicioSabado.adapter = adapter
        binding.finalSabado.adapter = adapter
        binding.inicioDomingo.adapter = adapter
        binding.finalDomingo.adapter = adapter
    }

    fun cargarCiudades(){
        var lista = ciudades.map { c -> c.nombre }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.ciudadLugar.adapter = adapter

        binding.ciudadLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                posCiudad = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    fun cargarCategorias(){
        var lista = categorias.map { c -> c.nombre }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriaLugar.adapter = adapter

        binding.categoriaLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                posCategoria = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    fun crearLugar(){
        setDialog(true)
        Log.e("CrearLugarFragment", binding.ciudadLugar.selectedItem.toString())
        val nombre = binding.nombreLugar.text.toString()
        val descripcion = binding.descripcionLugar.text.toString()
        val telefono = binding.telefonoLugar.text.toString()
        val direccion = binding.direccionLugar.text.toString()
        val idCiudad = ciudades[posCiudad].key
        val idCategoria = categorias[posCategoria].key

        if( nombre.isEmpty() ){
            binding.nombreLayout.error = getString(R.string.campo_obligatorio)
        }else{
            binding.nombreLayout.error = null
        }

        if( descripcion.isEmpty() ){
            binding.descripcionLayout.error = getString(R.string.campo_obligatorio)
        }else{
            binding.descripcionLayout.error = null
        }

        if( direccion.isEmpty() ){
            binding.direccionLayout.error = getString(R.string.campo_obligatorio)
        }

        if( telefono.isEmpty() ){
            binding.telefonoLayout.error = getString(R.string.campo_obligatorio)
        }else{
            binding.telefonoLayout.error = null
        }

        if(nombre.isNotEmpty() && descripcion.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty() && idCiudad != "" && idCategoria != "" && posicion != null) {

            if(imagenes.isNotEmpty()){
                val user = FirebaseAuth.getInstance().currentUser
                if(user != null){
                    val codigoUsuario = user.uid
                    val nuevoLugar = Lugar(nombre, descripcion, codigoUsuario, EstadoLugar.SIN_REVISAR, idCategoria, direccion, posicion!!, idCiudad)

                    val telefonos: ArrayList<String> = ArrayList()
                    telefonos.add(telefono)
                    nuevoLugar.telefonos = telefonos
                    nuevoLugar.imagenes = imagenes

                    if(binding.inicioLunes.selectedItemPosition!=0 && binding.finalLunes.selectedItemPosition != 0){
                        val horaInicio: Int = binding.inicioLunes.selectedItem.toString().toInt()
                        val horaFinal: Int = binding.finalLunes.selectedItem.toString().toInt()
                        if(horaInicio < horaFinal){
                            val dia: ArrayList<DiaSemana> = ArrayList()
                            dia.add(DiaSemana.LUNES)
                            val horario = Horario(0, dia, horaInicio, horaFinal)
                            nuevoLugar.horarios.add(horario)
                        }else{
                            Toast.makeText(requireActivity(), getString(R.string.horario_de_lunes_no_valido), Toast.LENGTH_SHORT).show()
                        }
                    }

                    if(binding.inicioMartes.selectedItemPosition!=0 && binding.finalMartes.selectedItemPosition != 0){
                        val horaInicio: Int = binding.inicioMartes.selectedItem.toString().toInt()
                        val horaFinal: Int = binding.finalMartes.selectedItem.toString().toInt()
                        if(horaInicio < horaFinal){
                            val dia: ArrayList<DiaSemana> = ArrayList()
                            dia.add(DiaSemana.MARTES)
                            val horario = Horario(0, dia, horaInicio, horaFinal)
                            nuevoLugar.horarios.add(horario)
                        }else{
                            Toast.makeText(requireActivity(), getString(R.string.horario_de_martes_no_valido), Toast.LENGTH_SHORT).show()
                        }
                    }

                    if(binding.inicioMiercoles.selectedItemPosition!=0 && binding.finalMiercoles.selectedItemPosition != 0){
                        val horaInicio: Int = binding.inicioMiercoles.selectedItem.toString().toInt()
                        val horaFinal: Int = binding.finalMiercoles.selectedItem.toString().toInt()
                        if(horaInicio < horaFinal){
                            val dia: ArrayList<DiaSemana> = ArrayList()
                            dia.add(DiaSemana.MIERCOLES)
                            val horario = Horario(0, dia, horaInicio, horaFinal)
                            nuevoLugar.horarios.add(horario)
                        }else{
                            Toast.makeText(requireActivity(), getString(R.string.horario_de_miercoles_no_valido), Toast.LENGTH_SHORT).show()
                        }
                    }

                    if(binding.inicioJueves.selectedItemPosition!=0 && binding.finalJueves.selectedItemPosition != 0){
                        val horaInicio: Int = binding.inicioJueves.selectedItem.toString().toInt()
                        val horaFinal: Int = binding.finalJueves.selectedItem.toString().toInt()
                        if(horaInicio < horaFinal){
                            val dia: ArrayList<DiaSemana> = ArrayList()
                            dia.add(DiaSemana.JUEVES)
                            val horario = Horario(0, dia, horaInicio, horaFinal)
                            nuevoLugar.horarios.add(horario)
                        }else{
                            Toast.makeText(requireActivity(), getString(R.string.horario_de_jueves_no_valido), Toast.LENGTH_SHORT).show()
                        }
                    }

                    if(binding.inicioViernes.selectedItemPosition!=0 && binding.finalViernes.selectedItemPosition != 0){
                        val horaInicio: Int = binding.inicioViernes.selectedItem.toString().toInt()
                        val horaFinal: Int = binding.finalViernes.selectedItem.toString().toInt()
                        if(horaInicio < horaFinal){
                            val dia: ArrayList<DiaSemana> = ArrayList()
                            dia.add(DiaSemana.VIERNES)
                            val horario = Horario(0, dia, horaInicio, horaFinal)
                            nuevoLugar.horarios.add(horario)
                        }else{
                            Toast.makeText(requireActivity(), getString(R.string.horario_de_viernes_no_valido), Toast.LENGTH_SHORT).show()
                        }
                    }

                    if(binding.inicioSabado.selectedItemPosition!=0 && binding.finalSabado.selectedItemPosition != 0){
                        val horaInicio: Int = binding.inicioSabado.selectedItem.toString().toInt()
                        val horaFinal: Int = binding.finalSabado.selectedItem.toString().toInt()
                        if(horaInicio < horaFinal){
                            val dia: ArrayList<DiaSemana> = ArrayList()
                            dia.add(DiaSemana.SABADO)
                            val horario = Horario(0, dia, horaInicio, horaFinal)
                            nuevoLugar.horarios.add(horario)
                        }else{
                            Toast.makeText(requireActivity(), getString(R.string.horario_de_sabado_no_valido), Toast.LENGTH_SHORT).show()
                        }
                    }

                    if(binding.inicioDomingo.selectedItemPosition!=0 && binding.finalDomingo.selectedItemPosition != 0){
                        val horaInicio: Int = binding.inicioDomingo.selectedItem.toString().toInt()
                        val horaFinal: Int = binding.finalDomingo.selectedItem.toString().toInt()
                        if(horaInicio < horaFinal){
                            val dia: ArrayList<DiaSemana> = ArrayList()
                            dia.add(DiaSemana.DOMINGO)
                            val horario = Horario(0, dia, horaInicio, horaFinal)
                            nuevoLugar.horarios.add(horario)
                        }else{
                            Toast.makeText(requireActivity(), getString(R.string.horario_de_domingo_no_valido), Toast.LENGTH_SHORT).show()
                        }
                    }

                    LugaresService.crearLugar(nuevoLugar){res ->
                        if(res){
                            setDialog(false)
                            Toast.makeText(requireActivity(), getString(R.string.lugar_creado_rev_mod), Toast.LENGTH_LONG).show()
                            requireActivity().supportFragmentManager.beginTransaction().replace( R.id.contenido_principal, MisLugaresFragment() )
                                .addToBackStack(MainActivity.MENU_MIS_LUGARES).commit()
                        }else{
                            setDialog(false)
                            Toast.makeText(requireActivity(), getString(R.string.lugar_creado_error), Toast.LENGTH_LONG).show()
                            requireActivity().supportFragmentManager.beginTransaction().replace( R.id.contenido_principal, MisLugaresFragment() )
                                .addToBackStack(MainActivity.MENU_MIS_LUGARES).commit()
                        }
                    }
                } else {
                    setDialog(false)
                    Toast.makeText(requireActivity(), getString(R.string.lugar_creado_error), Toast.LENGTH_LONG).show()
                }
            } else {
                setDialog(false)
                Toast.makeText(requireActivity(), getString(R.string.lugar_creado_imagenes_error), Toast.LENGTH_LONG).show()
            }
        } else {
            setDialog(false)
            if(posicion == null){
                Toast.makeText(requireActivity(), "Por favor seleccione la ubicación del lugar", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun permisoCamara(){
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            tienePermiso = true
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
        }
    }

    private fun setDialog(show: Boolean){
        if (show) dialog.show() else dialog.dismiss()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        gMap.uiSettings.isZoomControlsEnabled = true
        gMap.uiSettings.isScrollGesturesEnabled = false
    }

}