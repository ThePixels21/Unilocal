package co.edu.eam.proyectounilocal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.databinding.ActivityCuentaBinding
import co.edu.eam.proyectounilocal.modelo.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CuentaActivity : AppCompatActivity() {

    lateinit var binding: ActivityCuentaBinding
    var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            Firebase.firestore
                .collection("usuarios")
                .document(user.uid)
                .get()
                .addOnSuccessListener {
                    val u = it.toObject(Usuario::class.java)
                    if(u != null){
                        usuario = u
                        cargarDatos(usuario!!)
                    }
                }

            binding.btnEditar.setOnClickListener{
                if(usuario != null){
                    editarUsuario(usuario!!)
                }
            }

            binding.btnCerrarSesion.setOnClickListener{
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(baseContext, LoginActivity::class.java)
                startActivity( intent )
                finish()
            }

        }

    }

    fun cargarDatos(usuario: Usuario){
        binding.nombreUsuario.setText(usuario.nombre)
        binding.usuarioUsuario.setText(usuario.nickname)
        binding.ciudadUsuario.setText(usuario.ciudad)
    }

    fun editarUsuario(usuario: Usuario){
        if(binding.nombreUsuario.text.isNotEmpty() && binding.usuarioUsuario.text.isNotEmpty() && binding.ciudadUsuario.text.isNotEmpty()){
            usuario.nombre = binding.nombreUsuario.text.toString()
            usuario.nickname = binding.usuarioUsuario.text.toString()
            usuario.ciudad = binding.ciudadUsuario.text.toString()
            Firebase.firestore.collection("usuarios")
                .whereEqualTo("nickname", usuario.nickname)
                .get()
                .addOnSuccessListener {
                    if(it.isEmpty){
                        Firebase.firestore.collection("usuarios")
                            .document(usuario.key)
                            .set(usuario)
                            .addOnSuccessListener {
                                this.usuario = usuario
                                cargarDatos(usuario)
                                Toast.makeText(this, "Datos editados correctamente", Toast.LENGTH_LONG).show()
                            }
                    } else {
                        Toast.makeText(this, "Nombre de usuario en uso", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_LONG).show()
        }
    }

}