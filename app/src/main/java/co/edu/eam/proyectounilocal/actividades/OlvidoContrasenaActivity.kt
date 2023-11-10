package co.edu.eam.proyectounilocal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import co.edu.eam.proyectounilocal.databinding.ActivityOlvidoContrasenaBinding
import com.google.firebase.auth.FirebaseAuth

class OlvidoContrasenaActivity : AppCompatActivity() {

    lateinit var binding: ActivityOlvidoContrasenaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOlvidoContrasenaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEnviar.setOnClickListener{
            if(binding.correoUsuario.text.isNotEmpty()){
                val correo = binding.correoUsuario.text.toString()
                FirebaseAuth.getInstance().sendPasswordResetEmail(correo)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this, "Correo de recuperación enviado", Toast.LENGTH_LONG).show()
                            startActivity(Intent(baseContext, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Ocurrió un error", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Ocurrió un error", Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(this, "Ingrese su correo para recuperar la contraseña", Toast.LENGTH_LONG).show()
            }
        }

    }
}