package co.edu.eam.proyectounilocal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import co.edu.eam.proyectounilocal.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun irALogin(v: View){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}