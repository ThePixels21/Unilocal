package co.edu.eam.proyectounilocal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.adapter.ViewPagerAdapterLugar
import co.edu.eam.proyectounilocal.bd.LugaresService
import co.edu.eam.proyectounilocal.bd.UsuariosService
import co.edu.eam.proyectounilocal.databinding.ActivityDetalleLugarBinding
import co.edu.eam.proyectounilocal.modelo.Usuario
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class DetalleLugarActivity : AppCompatActivity() {

    var codigoLugar: String? = ""
    var fav: Boolean = false

    companion object{
        lateinit var binding: ActivityDetalleLugarBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { this.finish() }
        binding.btnSearch.setOnClickListener { startActivity(Intent(this, BusquedaActivity::class.java)) }

        codigoLugar = intent.extras!!.getString("codigo")

        if(codigoLugar != null){
            var nombreLugar = ""
            LugaresService.obtener(codigoLugar!!){lugar ->
                if(lugar != null){
                    nombreLugar = lugar.nombre
                    binding.nombreLugar.text = nombreLugar
                } else {
                    nombreLugar = "ERROR"
                }
            }
            //Adapter
            binding.viewPager.adapter = ViewPagerAdapterLugar(this, codigoLugar!!, 1)
            TabLayoutMediator(binding.tabs, binding.viewPager){ tab, pos ->
                when(pos){
                    0 -> tab.text = getString(R.string.txt_detalles)
                    1 -> tab.text = getString(R.string.resenas)
                    2 -> tab.text = getString(R.string.fotos)
                }
            }.attach()

            //Boton favorito
            val user = FirebaseAuth.getInstance().currentUser
            if(user != null){
                UsuariosService.buscar(user.uid){u ->
                    if(u != null){
                        fav = u.buscarFavorito(codigoLugar!!)
                        if(fav){
                            binding.imgFav.setImageResource(R.drawable.ic_favorite_40)
                        } else {
                            binding.imgFav.setImageResource(R.drawable.ic_favorite_border_40)
                        }
                        binding.imgFav.setOnClickListener { clickFav(u) }
                    }
                }
            }

            /*val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
            val codigoUsuario = sp.getInt("codigo_usuario", -1)
            if(codigoUsuario != -1){
                val usuario = Usuarios.buscar(codigoUsuario)
                if(usuario != null){
                    //ARREGLAR
                    fav = usuario.buscarFavorito(0)
                    if(fav){
                        binding.imgFav.setImageResource(R.drawable.ic_favorite_40)
                    } else {
                        binding.imgFav.setImageResource(R.drawable.ic_favorite_border_40)
                    }

                    binding.imgFav.setOnClickListener { clickFav(usuario) }
                }
            }*/
        }
    }

    fun clickFav(usuario : Usuario){
        if(fav){
            usuario.eliminarFavorito(codigoLugar!!)
            UsuariosService.actualizarUsuario(usuario){res ->
                if(res){
                    binding.imgFav.setImageResource(R.drawable.ic_favorite_border_40)
                    fav = false
                }
            }
        } else {
            usuario.agregarFavorito(codigoLugar!!)
            UsuariosService.actualizarUsuario(usuario){res ->
                if(res){
                    binding.imgFav.setImageResource(R.drawable.ic_favorite_40)
                    fav = true
                }
            }
        }
    }
}