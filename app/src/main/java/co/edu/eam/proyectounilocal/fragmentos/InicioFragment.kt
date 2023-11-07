package co.edu.eam.proyectounilocal.fragmentos

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.bd.Lugares
import co.edu.eam.proyectounilocal.databinding.FragmentInicioBinding
import co.edu.eam.proyectounilocal.modelo.EstadoLugar
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class InicioFragment : Fragment(), OnMapReadyCallback {

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
        Lugares.listarPorEstado(EstadoLugar.ACEPTADO).forEach{
            gMap.addMarker( MarkerOptions().position(LatLng(it.posicion.lat,it.posicion.lng)).title(it.nombre).visible(true))
        }
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
}