package co.edu.eam.proyectounilocal.actividades

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.bd.Usuarios
import co.edu.eam.proyectounilocal.databinding.ActivityBusquedaBinding
import co.edu.eam.proyectounilocal.fragmentos.BusquedasRecientesFragment
import co.edu.eam.proyectounilocal.fragmentos.ResultadoBusquedaFragment

class BusquedaActivity : AppCompatActivity() {

    companion object{
        lateinit var binding: ActivityBusquedaBinding
        lateinit var fragmentMngr: FragmentManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentMngr = supportFragmentManager

        binding.buscador.setOnEditorActionListener{ textView, i, keyEvent ->
            if(i == EditorInfo.IME_ACTION_SEARCH){
                val busqueda = binding.buscador.text.toString()
                if(busqueda.isNotEmpty()){
                    //Guardar busqueda usuario
                    val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
                    val codigoUsuario = sp.getInt("codigo_usuario", -1)
                    if(codigoUsuario != -1){
                        Usuarios.buscar(codigoUsuario)!!.agregarBusqueda(busqueda)
                    }
                    //Ejecutar fragmento busqueda
                    supportFragmentManager.beginTransaction()
                        .replace(binding.contenido.id, ResultadoBusquedaFragment.newInstance(busqueda))
                        .addToBackStack("busqueda")
                        .commit()
                }
            }
            true
        }

        binding.back.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }

        supportFragmentManager.beginTransaction()
            .replace(binding.contenido.id, BusquedasRecientesFragment())
            .addToBackStack("recientes")
            .commit()

    }

    override fun onResume() {
        super.onResume()
        binding.buscador.requestFocus()
        binding.buscador.postDelayed({
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.buscador, InputMethodManager.SHOW_IMPLICIT)
        },100)
    }
}