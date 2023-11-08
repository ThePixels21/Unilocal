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

        try {
            if (tienePermiso) {
                gMap.isMyLocationEnabled = true
            } else {
                gMap.isMyLocationEnabled = false
                gMap.uiSettings.isMyLocationButtonEnabled = false
            }
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
        val intent = Intent(requireContext(), DetalleLugarActivity::class.java)
        intent.putExtra("codigo", p0.tag.toString())
        requireContext().startActivity(intent)
    }
}