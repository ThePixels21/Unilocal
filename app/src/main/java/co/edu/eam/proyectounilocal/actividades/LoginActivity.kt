package co.edu.eam.proyectounilocal.actividades

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.bd.Personas
import co.edu.eam.proyectounilocal.databinding.ActivityLoginBinding
import co.edu.eam.proyectounilocal.modelo.Moderador
import co.edu.eam.proyectounilocal.modelo.Usuario
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val correo = sp.getString("correo_usuario", "")
        val tipo = sp.getString("tipo_usuario", "")
        if(correo!!.isNotEmpty() && tipo!!.isNotEmpty()){
            when(tipo){
                "usuario" -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                //moderador
            }
            this.finish()
        } else {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnIniciarSesion.setOnClickListener{ login() }
            binding.txtOlvidoContrasenia.setOnClickListener{ irAOlvidoContrasenia() }
            binding.txtNoCuenta.setOnClickListener { irARegsitro() }
        }
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
            val persona = Personas.login(correo.toString(), password.toString())
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
                    is Moderador -> startActivity(Intent(this, MainActivity::class.java))
                }
            } else {
                Snackbar.make(window.decorView.rootView, "Datos incorrectos", BaseTransientBottomBar.LENGTH_SHORT).show()
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