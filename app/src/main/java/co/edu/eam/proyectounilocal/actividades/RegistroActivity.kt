package co.edu.eam.proyectounilocal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.bd.Usuarios
import co.edu.eam.proyectounilocal.databinding.ActivityRegistroBinding
import co.edu.eam.proyectounilocal.modelo.Usuario
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class RegistroActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrarse.setOnClickListener{ registrarUsuario() }
    }

    fun registrarUsuario(){
        val nombre = binding.nombreUsuario.text.toString()
        val nickname = binding.usuarioUsuario.text.toString()
        val email = binding.emailUsuario.text.toString()
        val ciudad = binding.ciudadUsuario.text.toString()
        val password = binding.passwordUsuario.text.toString()
        val cpassword = binding.passwordConfirmUsuario.text.toString()

        if(nombre.isEmpty()) {
            binding.nombreUsuario.error = getString(R.string.campo_obligatorio)
        } else {
            binding.nombreUsuario.error = null
        }

        if(nickname.isEmpty()) {
            binding.usuarioUsuario.error = getString(R.string.campo_obligatorio)
        } else {
            binding.usuarioUsuario.error = null
        }

        if(email.isEmpty()) {
            binding.emailUsuario.error = getString(R.string.campo_obligatorio)
        } else {
            binding.emailUsuario.error = null
        }

        if(ciudad.isEmpty()) {
            binding.ciudadUsuario.error = getString(R.string.campo_obligatorio)
        } else {
            binding.ciudadUsuario.error = null
        }

        if(password.isEmpty()) {
            binding.passwordUsuario.error = getString(R.string.campo_obligatorio)
        } else {
            binding.passwordUsuario.error = null
        }

        if(cpassword.isEmpty()) {
            binding.passwordConfirmUsuario.error = getString(R.string.campo_obligatorio)
        } else {
            binding.passwordConfirmUsuario.error = null
        }

        if(nombre.isNotEmpty() && nickname.isNotEmpty() && email.isNotEmpty() && ciudad.isNotEmpty() && password.isNotEmpty() && cpassword.isNotEmpty()){
            if(password==cpassword){
                binding.passwordConfirmUsuario.error = null
                if(Usuarios.agregar(Usuario(0, nombre, nickname, ciudad, email, password))){
                    Toast.makeText(this, getString(R.string.registrado_exitosamente), Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                }else{
                    Toast.makeText(this, getString(R.string.no_se_pudo_registrar), Toast.LENGTH_LONG).show()
                }
            }else{
                binding.passwordConfirmUsuario.error = getString(R.string.la_contrasena_ingresada_no_coincide)
            }
        }
    }
}