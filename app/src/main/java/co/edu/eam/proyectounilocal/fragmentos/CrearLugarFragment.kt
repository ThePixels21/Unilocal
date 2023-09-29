package co.edu.eam.proyectounilocal.fragmentos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.actividades.MainActivity
import co.edu.eam.proyectounilocal.bd.Categorias
import co.edu.eam.proyectounilocal.bd.Ciudades
import co.edu.eam.proyectounilocal.bd.Lugares
import co.edu.eam.proyectounilocal.databinding.FragmentCrearLugarBinding
import co.edu.eam.proyectounilocal.modelo.Categoria
import co.edu.eam.proyectounilocal.modelo.Ciudad
import co.edu.eam.proyectounilocal.modelo.EstadoLugar
import co.edu.eam.proyectounilocal.modelo.Lugar

class CrearLugarFragment : Fragment() {

    lateinit var binding: FragmentCrearLugarBinding
    lateinit var ciudades:ArrayList<Ciudad>
    lateinit var categorias:ArrayList<Categoria>

    var posCiudad:Int = -1
    var posCategoria:Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCrearLugarBinding.inflate(inflater, container, false)

        ciudades = Ciudades.listar()
        categorias = Categorias.listar()

        cargarCiudades()
        cargarCategorias()

        binding.btnCrearLugar.setOnClickListener { crearLugar() }

        binding.btnVolver.setOnClickListener { requireActivity().supportFragmentManager.beginTransaction().replace( R.id.contenido_principal, InicioFragment() )
            .addToBackStack(MainActivity.MENU_INICIO).commit()
            MainActivity.binding.menuInferior.menu.getItem(0).isChecked = true}

        return binding.root
    }

    fun cargarCiudades(){
        var lista = ciudades.map { c -> c.nombre }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.ciudadLugar.adapter = adapter

        binding.ciudadLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                posCiudad = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    fun cargarCategorias(){
        var lista = categorias.map { c -> c.nombre }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriaLugar.adapter = adapter

        binding.categoriaLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                posCategoria = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    fun crearLugar(){
        val nombre = binding.nombreLugar.text.toString()
        val descripcion = binding.descripcionLugar.text.toString()
        val telefono = binding.telefonoLugar.text.toString()
        val direccion = binding.direccionLugar.text.toString()
        val idCiudad = ciudades[posCiudad].id
        val idCategoria = categorias[posCategoria].id

        if( nombre.isEmpty() ){
            binding.nombreLayout.error = "Campo obligatorio"
        }else{
            binding.nombreLayout.error = null
        }

        if( descripcion.isEmpty() ){
            binding.descripcionLayout.error = "Campo obligatorio"
        }else{
            binding.descripcionLayout.error = null
        }

        if( direccion.isEmpty() ){
            binding.direccionLayout.error = "Campo obligatorio"
        }

        if( telefono.isEmpty() ){
            binding.telefonoLayout.error = "Campo obligatorio"
        }else{
            binding.telefonoLayout.error = null
        }

        if(nombre.isNotEmpty() && descripcion.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty() && idCiudad != -1 && idCategoria != -1) {

            val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
            val codigoUsuario = sp.getInt("codigo_usuario", -1)
            if(codigoUsuario != -1){
                val nuevoLugar = Lugar(nombre, descripcion, codigoUsuario, EstadoLugar.SIN_REVISAR, idCategoria, direccion, 0f, 0f, idCiudad)

                val telefonos: ArrayList<String> = ArrayList()
                telefonos.add(telefono)
                nuevoLugar.telefonos = telefonos

                Lugares.crear(nuevoLugar)

                Toast.makeText(requireActivity(), "Lugar creado correctamente, será revisado por un moderador", Toast.LENGTH_LONG).show()

                requireActivity().supportFragmentManager.beginTransaction().replace( R.id.contenido_principal, MisLugaresFragment() )
                    .addToBackStack(MainActivity.MENU_MIS_LUGARES).commit()
            }

        }

    }

}