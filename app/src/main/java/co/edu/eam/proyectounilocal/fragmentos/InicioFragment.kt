package co.edu.eam.proyectounilocal.fragmentos

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.actividades.DetalleLugarActivity
import co.edu.eam.proyectounilocal.actividades.GestionarLugarActivity
import co.edu.eam.proyectounilocal.bd.LugaresService
import co.edu.eam.proyectounilocal.databinding.FragmentInicioBinding
import co.edu.eam.proyectounilocal.modelo.EstadoLugar
import co.edu.eam.proyectounilocal.modelo.Lugar
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InicioFragment : Fragment(), OnMapReadyCallback, OnInfoWindowClickListener {

    lateinit var binding: FragmentInicioBinding

    lateinit var gMap:GoogleMap
    private var tienePermiso = false
    private val defaultLocation = LatLng(4.550923, -75.6557201)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLocationPermission()
        permisoCamara()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInicioBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapa_principal) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        gMap.uiSettings.isMyLocationButtonEnabled = false
        gMap.uiSettings.isCompassEnabled = false
        gMap.uiSettings.isRotateGesturesEnabled = false

        try {
            gMap.isMyLocationEnabled = tienePermiso
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
        LugaresService.listarLugaresPorEstado(EstadoLugar.ACEPTADO) { lugares ->
            for (lugar in lugares) {
                gMap.addMarker(MarkerOptions().position(LatLng(lugar.posicion.lat,lugar.posicion.lng)).title(lugar.nombre).visible(true))!!.tag = lugar.key
            }
        }
        gMap.setOnInfoWindowClickListener(this)
        obtenerUbicacion()

        binding.btnReestebalcerUbicacion.setOnClickListener{ reestablecerUbicacion() }
    }

    private fun reestablecerUbicacion(){
        try{
            if(tienePermiso){
                val ubicacionActual =
                    LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation
                ubicacionActual.addOnCompleteListener(requireActivity()) {
                    if (it.isSuccessful) {
                        val ubicacion = it.result
                        if (ubicacion != null) {
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(ubicacion.latitude, ubicacion.longitude), 18F))

                        }
                    }
                }
            }
        }catch (e: SecurityException) {
            Log.e("Exception: %%s", e.message, e)
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            tienePermiso = true
        } else {
            //permissionsResultCallback.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
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

    private fun obtenerUbicacion() {
        try {
            if (tienePermiso) {
                val ubicacionActual =
                    LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation
                ubicacionActual.addOnCompleteListener(requireActivity()) {
                    if (it.isSuccessful) {
                        val ubicacion = it.result
                        if (ubicacion != null) {
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(ubicacion.latitude, ubicacion.longitude), 15F))

                        }
                    } else {
                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation,
                            15F))
                        gMap.uiSettings.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %%s", e.message, e)
        }
    }

    override fun onInfoWindowClick(p0: Marker) {
        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            Firebase.firestore.collection("lugares")
                .document(p0.tag.toString()).get()
                .addOnSuccessListener {
                    val lugar = it.toObject(Lugar::class.java)
                    if(lugar != null){
                        if(lugar.idCreador == user.uid){
                            val intent = Intent(requireContext(), GestionarLugarActivity::class.java)
                            intent.putExtra("codigo", p0.tag.toString())
                            requireContext().startActivity(intent)
                        } else {
                            val intent = Intent(requireContext(), DetalleLugarActivity::class.java)
                            intent.putExtra("codigo", p0.tag.toString())
                            requireContext().startActivity(intent)
                        }
                    }
                }
        }
    }
}