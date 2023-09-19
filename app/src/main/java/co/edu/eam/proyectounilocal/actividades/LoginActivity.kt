package co.edu.eam.proyectounilocal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import co.edu.eam.proyectounilocal.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
    fun irARegsitro(v: View){
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
    fun irAOlvidoContrasena(v: View){
        val intent = Intent(this, OlvidoContrasenaActivity::class.java)
        startActivity(intent)
    }
}