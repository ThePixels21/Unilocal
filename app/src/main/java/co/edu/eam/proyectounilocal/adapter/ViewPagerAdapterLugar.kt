package co.edu.eam.proyectounilocal.adapter

import android.media.Image
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.edu.eam.proyectounilocal.fragmentos.ComentariosLugarFragment
import co.edu.eam.proyectounilocal.fragmentos.ImagenesLugarFragment
import co.edu.eam.proyectounilocal.fragmentos.InfoLugarFragment

class ViewPagerAdapterLugar(var fragment: FragmentActivity, var codigoLugar: Int): FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> InfoLugarFragment.newInstance(codigoLugar)
            1 -> ComentariosLugarFragment.newInstance(codigoLugar)
            else -> ImagenesLugarFragment.newInstance(codigoLugar)
        }
    }
}