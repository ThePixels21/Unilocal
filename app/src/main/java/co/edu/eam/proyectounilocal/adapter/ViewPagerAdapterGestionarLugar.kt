package co.edu.eam.proyectounilocal.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.edu.eam.proyectounilocal.fragmentos.ComentariosGestionarLugarFragment
import co.edu.eam.proyectounilocal.fragmentos.ComentariosLugarFragment
import co.edu.eam.proyectounilocal.fragmentos.ImagenesLugarFragment
import co.edu.eam.proyectounilocal.fragmentos.InfoLugarFragment

class ViewPagerAdapterGestionarLugar(var fragment: FragmentActivity, var codigoLugar: String): FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> InfoLugarFragment.newInstance(codigoLugar)
            1 -> ComentariosGestionarLugarFragment.newInstance(codigoLugar)
            else -> ImagenesLugarFragment.newInstance(codigoLugar)
        }
    }
}