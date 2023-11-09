package co.edu.eam.proyectounilocal.actividades

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.databinding.ActivityLoginBinding
import co.edu.eam.proyectounilocal.modelo.Rol
import co.edu.eam.proyectounilocal.modelo.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        if(user != null) {
            hacerRedireccion(user)
        } else {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnIniciarSesion.setOnClickListener{ login() }
            binding.txtOlvidoContrasenia.setOnClickListener{ irAOlvidoContrasenia() }
            binding.txtNoCuenta.setOnClickListener { irARegsitro() }
        }

        /*val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val correo = sp.getString("correo_usuario", "")
        val tipo = sp.getString("tipo_usuario", "")
        if(correo!!.isNotEmpty() && tipo!!.isNotEmpty()){
            when(tipo){
                "usuario" -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
                "moderador" -> {
                    val intent = Intent(this, ModMainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }
            this.finish()
        } else {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnIniciarSesion.setOnClickListener{ login() }
            binding.txtOlvidoContrasenia.setOnClickListener{ irAOlvidoContrasenia() }
            binding.txtNoCuenta.setOnClickListener { irARegsitro() }
        }*/
    }

    fun login(){
        val correo = binding.emailUsuario.text
        val password = binding.passwordUsuario.text

        if(correo.isEmpty()) {
            binding.emailLayout.error = "Es obligatorio"
        } else {
            binding.emailLayout.error = null
        }

        if(password.isEmpty()) {
            binding.passwordLayout.error = "Es obligatorio"
        } else {
            binding.passwordLayout.error = null
        }

        if(correo.isNotEmpty() && password.isNotEmpty()){

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(correo.toString(), password.toString())
                .addOnCompleteListener { it ->
                    if(it.isSuccessful){

                        val user = FirebaseAuth.getInstance().currentUser

                        if(user != null){
                            hacerRedireccion(user)
                        }
                    }else{
                        Toast.makeText(this, getString(R.string.datos_incorrectos), Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, getString(R.string.datos_incorrectos), Toast.LENGTH_LONG).show()
                }

            /*val persona = Personas.login(correo.toString(), password.toString())
            if(persona != null){

                val tipo = if(persona is Usuario) "usuario" else "moderador"
                val sp = this.getSharedPreferences("sesion", Context.MODE_PRIVATE).edit()
                sp.putInt("codigo_usuario", persona.id)
                sp.putString("correo_usuario", persona.correo)
                sp.putString("tipo_usuario", tipo)
                sp.commit()

                when(persona){
                    //Usuario
                    is Usuario -> startActivity(Intent(this, MainActivity::class.java))
                    //Moderador
                    is Moderador -> startActivity(Intent(this, ModMainActivity::class.java))
                }
            } else {
                Toast.makeText(this, getString(R.string.datos_incorrectos), Toast.LENGTH_LONG).show()
            }*/
        }
    }

    fun hacerRedireccion(user: FirebaseUser){
        Firebase.firestore
            .collection("usuarios")
            .document(user.uid)
            .get()
            .addOnSuccessListener {u ->
                val usuario = u.toObject(Usuario::class.java)
                if(usuario != null){
                    val rol = usuario.rol
                    var intent: Intent = Intent()
                    if(rol == Rol.USUARIO){
                        intent = Intent(this, MainActivity::class.java)
                    } else if(rol == Rol.MODERADOR){
                        intent = Intent(this, ModMainActivity::class.java)
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }
            }
    }

    fun irARegsitro(){
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
    fun irAOlvidoContrasenia(){
        val intent = Intent(this, OlvidoContrasenaActivity::class.java)
        startActivity(intent)

    }


}