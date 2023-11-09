package co.edu.eam.proyectounilocal.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.proyectounilocal.adapter.BusquedasRecientesAdapter
import co.edu.eam.proyectounilocal.bd.UsuariosService
import co.edu.eam.proyectounilocal.databinding.FragmentBusquedasRecientesBinding
import com.google.firebase.auth.FirebaseAuth

class BusquedasRecientesFragment : Fragment() {

    lateinit var binding: FragmentBusquedasRecientesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusquedasRecientesBinding.inflate(inflater, container, false)

        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            UsuariosService.buscar(user.uid){usuario ->
                if(usuario != null){
                    val lista: ArrayList<String> = usuario.busquedasRecientes
                    val adapter = BusquedasRecientesAdapter(lista)
                    binding.listaBusquedasRecientes.adapter = adapter
                    binding.listaBusquedasRecientes.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)
                }
            }
        }

        /*val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigoUsuario = sp.getInt("codigo_usuario", -1)
        if(codigoUsuario != -1){
            val usuario = Usuarios.buscar(codigoUsuario)
            if(usuario != null){
                lista = usuario.busquedasRecientes
                val adapter = BusquedasRecientesAdapter(lista)
                binding.listaBusquedasRecientes.adapter = adapter
                binding.listaBusquedasRecientes.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)
            }
        }*/

        return binding.root
    }

}