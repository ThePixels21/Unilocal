package co.edu.eam.proyectounilocal.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.eam.proyectounilocal.R

class ImagenesLugarFragment : Fragment() {

    companion object{
        fun newInstance(codigoLugar:Int):ImagenesLugarFragment{
            val args = Bundle()
            args.putInt("id_lugar", codigoLugar)
            val fragmento = ImagenesLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }
}