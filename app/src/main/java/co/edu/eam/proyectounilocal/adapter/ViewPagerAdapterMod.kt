package co.edu.eam.proyectounilocal.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.edu.eam.proyectounilocal.fragmentos.ModLugaresFragment
import co.edu.eam.proyectounilocal.fragmentos.ModRegistroLugaresFragment

class ViewPagerAdapterMod(var fragment: FragmentActivity): FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ModLugaresFragment()
            else -> ModRegistroLugaresFragment()
        }
    }
}