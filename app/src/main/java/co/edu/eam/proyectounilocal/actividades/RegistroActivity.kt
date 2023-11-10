package co.edu.eam.proyectounilocal.actividades

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.databinding.ActivityRegistroBinding
import co.edu.eam.proyectounilocal.modelo.Rol
import co.edu.eam.proyectounilocal.modelo.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistroBinding
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(R.layout.dialogo_progreso)
        dialog = builder.create()

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
            if(password.length >= 6){
                binding.passwordUsuario.error = null
            } else {
                binding.passwordUsuario.error = getString(R.string.seis_caracteres_minimo)
            }
        }

        if(cpassword.isEmpty()) {
            binding.passwordConfirmUsuario.error = getString(R.string.campo_obligatorio)
        } else {
            binding.passwordConfirmUsuario.error = null
        }

        if(nombre.isNotEmpty() && nickname.isNotEmpty() && email.isNotEmpty() && ciudad.isNotEmpty() && password.isNotEmpty() && cpassword.isNotEmpty() && password.length >= 6){
            if(password==cpassword){
                setDialog(true)
                Firebase.firestore.collection("usuarios")
                    .whereEqualTo("nickname", nickname)
                    .get()
                    .addOnSuccessListener {
                        if(it.isEmpty){
                            binding.usuarioUsuario.error = null
                            binding.passwordConfirmUsuario.error = null
                            FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener {
                                    if(it.isSuccessful){
                                        val usuario = FirebaseAuth.getInstance().currentUser
                                        if(usuario != null){
                                            val usuarioRegistro = Usuario(nombre, nickname, ciudad, email, Rol.USUARIO)
                                            usuarioRegistro.key = usuario.uid
                                            Firebase.firestore
                                                .collection("usuarios")
                                                .document(usuarioRegistro.key)
                                                .set(usuarioRegistro)
                                                .addOnSuccessListener {
                                                    setDialog(false)
                                                    Toast.makeText(this, getString(R.string.registrado_exitosamente), Toast.LENGTH_LONG).show()
                                                    startActivity(Intent(this, LoginActivity::class.java))
                                                    finish()
                                                }
                                                .addOnFailureListener {
                                                    setDialog(false)
                                                    Toast.makeText(this, getString(R.string.no_se_pudo_registrar), Toast.LENGTH_LONG).show()
                                                }
                                        } else {
                                            setDialog(false)
                                            Toast.makeText(this, getString(R.string.no_se_pudo_registrar), Toast.LENGTH_LONG).show()
                                        }
                                    } else {
                                        setDialog(false)
                                        Toast.makeText(this, getString(R.string.no_se_pudo_registrar), Toast.LENGTH_LONG).show()
                                    }
                                }
                                .addOnFailureListener {
                                    setDialog(false)
                                    Toast.makeText(this, getString(R.string.no_se_pudo_registrar), Toast.LENGTH_LONG).show()
                                }
                        } else {
                            setDialog(false)
                            binding.usuarioUsuario.error = "Nickname en uso"
                            Toast.makeText(this, "Nickname en uso", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener {
                        setDialog(false)
                        Toast.makeText(this, getString(R.string.no_se_pudo_registrar), Toast.LENGTH_LONG).show()
                    }
            }else{
                binding.passwordConfirmUsuario.error = getString(R.string.la_contrasena_ingresada_no_coincide)
            }
        }
    }

    private fun setDialog(show: Boolean){
        if (show) dialog.show() else dialog.dismiss()
    }

}