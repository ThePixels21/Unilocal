package co.edu.eam.proyectounilocal.actividades

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.adapter.ViewPagerAdapterLugar
import co.edu.eam.proyectounilocal.bd.Lugares
import co.edu.eam.proyectounilocal.bd.Usuarios
import co.edu.eam.proyectounilocal.databinding.ActivityDetalleLugarBinding
import co.edu.eam.proyectounilocal.fragmentos.InicioFragment
import co.edu.eam.proyectounilocal.modelo.Usuario
import com.google.android.material.tabs.TabLayoutMediator

class DetalleLugarActivity : AppCompatActivity() {

    var codigoLugar: Int = -1
    var fav: Boolean = false

    companion object{
        lateinit var binding: ActivityDetalleLugarBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.btnSearch.setOnClickListener { startActivity(Intent(this, BusquedaActivity::class.java)) }

        codigoLugar = intent.extras!!.getInt("codigo")

        if(codigoLugar != -1){
            val nombreLugar = Lugares.obtener(codigoLugar)!!.nombre
            binding.nombreLugar.text = nombreLugar
            //Adapter
            binding.viewPager.adapter = ViewPagerAdapterLugar(this, codigoLugar, 1)
            TabLayoutMediator(binding.tabs, binding.viewPager){ tab, pos ->
                when(pos){
                    0 -> tab.text = "Descripción"
                    1 -> tab.text = "Reseñas"
                    2 -> tab.text = "Fotos"
                }
            }.attach()

            //Boton favorito
            val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
            val codigoUsuario = sp.getInt("codigo_usuario", -1)
            if(codigoUsuario != -1){
                val usuario = Usuarios.buscar(codigoUsuario)
                if(usuario != null){
                    fav = usuario.buscarFavorito(codigoLugar)
                    if(fav){
                        binding.imgFav.setImageResource(R.drawable.ic_favorite_40)
                    } else {
                        binding.imgFav.setImageResource(R.drawable.ic_favorite_border_40)
                    }

                    binding.imgFav.setOnClickListener { clickFav(usuario) }
                }
            }
        }
    }

    fun clickFav(usuario : Usuario){
        if(fav){
            usuario.lugaresFavoritos.remove(codigoLugar)
            binding.imgFav.setImageResource(R.drawable.ic_favorite_border_40)
            fav = false
        } else {
            usuario.lugaresFavoritos.add(codigoLugar)
            binding.imgFav.setImageResource(R.drawable.ic_favorite_40)
            fav = true
        }
    }
}