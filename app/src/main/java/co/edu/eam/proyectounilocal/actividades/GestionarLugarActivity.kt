package co.edu.eam.proyectounilocal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.adapter.ViewPagerAdapterGestionarLugar
import co.edu.eam.proyectounilocal.bd.LugaresService
import co.edu.eam.proyectounilocal.bd.UsuariosService
import co.edu.eam.proyectounilocal.databinding.ActivityGestionarLugarBinding
import co.edu.eam.proyectounilocal.modelo.EstadoLugar
import co.edu.eam.proyectounilocal.modelo.Usuario
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class GestionarLugarActivity : AppCompatActivity() {

    var codigoLugar: String = ""
    var fav: Boolean = false

    companion object{
        lateinit var binding: ActivityGestionarLugarBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGestionarLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { this.finish() }

        codigoLugar = intent.extras!!.getString("codigo", "")
        if(codigoLugar != ""){
            LugaresService.obtener(codigoLugar){ lug ->
                val lugar = lug
                var nombreLugar = ""
                if(lugar != null){
                    nombreLugar = lugar.nombre

                    //Botón eliminar
                    binding.btnEliminarLugar.setOnClickListener{
                        LugaresService.eliminarLugar(codigoLugar){res ->
                            if(res){
                                Toast.makeText(this, getString(R.string.lugar_eliminado), Toast.LENGTH_LONG).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                this.finish()
                            }else {
                                Toast.makeText(this, getString(R.string.lugar_no_eliminado), Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    //Icono estado
                    if(lugar.estado == EstadoLugar.ACEPTADO){
                        binding.estado.text = "\uf058"
                        binding.estado.setTextColor(ContextCompat.getColor(baseContext, R.color.green))
                    } else if(lugar.estado == EstadoLugar.RECHAZADO){
                        binding.estado.text = "\uf057"
                        binding.estado.setTextColor(ContextCompat.getColor(baseContext, R.color.red))
                    }

                }else{
                    nombreLugar = "ERROR"
                }
                binding.nombreLugar.text = nombreLugar
            }

            //Botón favorito
            val user = FirebaseAuth.getInstance().currentUser
            if(user != null){
                UsuariosService.buscar(user.uid){ u ->
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

            //Adapter
            binding.viewPager.adapter = ViewPagerAdapterGestionarLugar(this, codigoLugar)
            TabLayoutMediator(binding.tabs, binding.viewPager){ tab, pos ->
                when(pos){
                    0 -> tab.text = getString(R.string.descripcion)
                    1 -> tab.text = getString(R.string.resenas)
                    2 -> tab.text = getString(R.string.fotos)
                }
            }.attach()

        }

    }

    fun clickFav(usuario : Usuario){
        if(fav){
            usuario.eliminarFavorito(codigoLugar)
            UsuariosService.actualizarUsuario(usuario){res ->
                if(res){
                    binding.imgFav.setImageResource(R.drawable.ic_favorite_border_40)
                    fav = false
                }
            }
        } else {
            usuario.agregarFavorito(codigoLugar)
            UsuariosService.actualizarUsuario(usuario){res ->
                if(res){
                    binding.imgFav.setImageResource(R.drawable.ic_favorite_40)
                    fav = true
                }
            }
        }
    }
}