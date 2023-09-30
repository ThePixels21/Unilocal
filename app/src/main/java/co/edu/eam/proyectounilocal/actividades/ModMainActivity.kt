package co.edu.eam.proyectounilocal.actividades

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.adapter.ViewPagerAdapterMod
import co.edu.eam.proyectounilocal.databinding.ActivityModMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class ModMainActivity : AppCompatActivity() {

    companion object {
        lateinit var binding: ActivityModMainBinding
        lateinit var act: FragmentActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        act = this

        binding.btnCerrarSesion.setOnClickListener{ cerrarSesion() }

        binding.viewPager.adapter = ViewPagerAdapterMod(this)
        TabLayoutMediator(binding.tabs, binding.viewPager){tab, pos ->
            when (pos){
                0 -> tab.text = "Lugares"
                1 -> tab.text = "Registro"
            }
        }.attach()

    }

    fun cerrarSesion(){
        val sh = getSharedPreferences("sesion", Context.MODE_PRIVATE).edit()
        sh.clear()
        sh.commit()
        startActivity(Intent(this, LoginActivity::class.java))
    }
}