package co.edu.eam.proyectounilocal.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.adapter.ViewPagerAdapterLugarMod
import co.edu.eam.proyectounilocal.adapter.ViewPagerAdapterMod
import co.edu.eam.proyectounilocal.bd.Lugares
import co.edu.eam.proyectounilocal.databinding.ActivityModDetalleLugarBinding
import co.edu.eam.proyectounilocal.modelo.EstadoLugar
import com.google.android.material.tabs.TabLayoutMediator

class ModDetalleLugarActivity : AppCompatActivity() {

    lateinit var binding: ActivityModDetalleLugarBinding
    var codigoLugar: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        codigoLugar = intent.extras!!.getInt("codigo")

        if(codigoLugar != -1){
            val lugar = Lugares.obtener(codigoLugar)
            if(lugar != null){
                binding.nombreLugar.text = lugar.nombre

                binding.btnVolver.setOnClickListener { this.finish() }
                binding.btnAprobar.setOnClickListener {
                    lugar.estado = EstadoLugar.ACEPTADO
                    Lugares.agregarRegistro(lugar, EstadoLugar.ACEPTADO)
                    ModMainActivity.binding.viewPager.adapter = ViewPagerAdapterMod(ModMainActivity.act)
                    this.finish()
                }
                binding.btnRechazar.setOnClickListener {
                    lugar.estado = EstadoLugar.RECHAZADO
                    Lugares.agregarRegistro(lugar, EstadoLugar.RECHAZADO)
                    ModMainActivity.binding.viewPager.adapter = ViewPagerAdapterMod(ModMainActivity.act)
                    this.finish()
                }

                binding.viewPager.adapter = ViewPagerAdapterLugarMod(this, codigoLugar)
                TabLayoutMediator(binding.tabs, binding.viewPager){ tab, pos ->
                    when(pos){
                        0 -> tab.text = getString(R.string.descripcion)
                        1 -> tab.text = getString(R.string.fotos)
                    }
                }.attach()
            }
        }

    }
}