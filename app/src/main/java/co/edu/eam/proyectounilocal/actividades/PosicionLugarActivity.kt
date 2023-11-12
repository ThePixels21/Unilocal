package co.edu.eam.proyectounilocal.actividades

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.databinding.ActivityPosicionLugarBinding
import co.edu.eam.proyectounilocal.modelo.Posicion
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class PosicionLugarActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var binding: ActivityPosicionLugarBinding

    private var tienePermiso = false

    lateinit var gMap: GoogleMap
    private val defaultLocation = LatLng(4.550923, -75.6557201)
    private var posicion: Posicion? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPosicionLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getLocationPermission()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapa_crear_lugar) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnSeleccionarUbicacion.setOnClickListener{
            if(posicion != null){
                val resultIntent = Intent()
                resultIntent.putExtra("lat", posicion!!.lat)
                resultIntent.putExtra("lng", posicion!!.lng)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Seleccione la ubicación del lugar", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        gMap.uiSettings.isZoomControlsEnabled = true
        try{
            if(tienePermiso){
                gMap.isMyLocationEnabled = true
                gMap.uiSettings.isMyLocationButtonEnabled = true
            }else{
                gMap.isMyLocationEnabled = false
                gMap.uiSettings.isMyLocationButtonEnabled = false
            }
        }catch (e: SecurityException){
            e.printStackTrace()
        }

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f))

        gMap.setOnMapClickListener {
            if(posicion == null){
                posicion = Posicion()
            }
            posicion!!.lat = it.latitude
            posicion!!.lng = it.longitude
            gMap.clear()
            gMap.addMarker( MarkerOptions().position(it).title("Aquí"))
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(baseContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            tienePermiso = true
        } else {
            //permissionsResultCallback.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
        }
    }
}