package co.edu.eam.proyectounilocal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.adapter.ViewPagerAdapterLugar
import co.edu.eam.proyectounilocal.bd.Lugares
import co.edu.eam.proyectounilocal.databinding.ActivityDetalleLugarBinding
import co.edu.eam.proyectounilocal.fragmentos.InicioFragment
import com.google.android.material.tabs.TabLayoutMediator

class DetalleLugarActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetalleLugarBinding
    var codigoLugar: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }

        codigoLugar = intent.extras!!.getInt("codigo")

        if(codigoLugar != -1){
            val nombreLugar = Lugares.obtener(codigoLugar)!!.nombre
            binding.nombreLugar.text = nombreLugar
            //Adapter
            binding.viewPager.adapter = ViewPagerAdapterLugar(this, codigoLugar)
            TabLayoutMediator(binding.tabs, binding.viewPager){ tab, pos ->
                when(pos){
                    0 -> tab.text = "Descripción"
                    1 -> tab.text = "Reseñas"
                    2 -> tab.text = "Fotos"
                }
            }.attach()

        }
    }
}