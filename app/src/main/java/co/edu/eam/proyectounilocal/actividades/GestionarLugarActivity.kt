package co.edu.eam.proyectounilocal.actividades

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.adapter.ViewPagerAdapterGestionarLugar
import co.edu.eam.proyectounilocal.adapter.ViewPagerAdapterLugar
import co.edu.eam.proyectounilocal.bd.Lugares
import co.edu.eam.proyectounilocal.bd.Usuarios
import co.edu.eam.proyectounilocal.databinding.ActivityGestionarLugarBinding
import co.edu.eam.proyectounilocal.modelo.EstadoLugar
import co.edu.eam.proyectounilocal.modelo.Usuario
import com.google.android.material.tabs.TabLayoutMediator

class GestionarLugarActivity : AppCompatActivity() {

    var codigoLugar: Int = -1
    var fav: Boolean = false

    companion object{
        lateinit var binding: ActivityGestionarLugarBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGestionarLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { this.finish() }

        codigoLugar = intent.extras!!.getInt("codigo")
        if(codigoLugar != -1){
            val lugar = Lugares.obtener(codigoLugar)
            val nombreLugar = lugar!!.nombre
            binding.nombreLugar.text = nombreLugar

            //Botón favorito
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

            //Botón eliminar
            binding.btnEliminarLugar.setOnClickListener{
                Lugares.eliminar(lugar)
                startActivity(Intent(this, MainActivity::class.java))
                this.finish()
            }

            //Icono estado
            if(lugar.estado == EstadoLugar.ACEPTADO){
                binding.estado.text = "\uf058"
                binding.estado.setTextColor(ContextCompat.getColor(baseContext, R.color.green))
            } else if(lugar.estado == EstadoLugar.RECHAZADO){
                binding.estado.text = "\uf057"
                binding.estado.setTextColor(ContextCompat.getColor(baseContext, R.color.red))
            }

            //Adapter
            binding.viewPager.adapter = ViewPagerAdapterGestionarLugar(this, codigoLugar)
            TabLayoutMediator(binding.tabs, binding.viewPager){ tab, pos ->
                when(pos){
                    0 -> tab.text = "Descripción"
                    1 -> tab.text = "Reseñas"
                    2 -> tab.text = "Fotos"
                }
            }.attach()

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