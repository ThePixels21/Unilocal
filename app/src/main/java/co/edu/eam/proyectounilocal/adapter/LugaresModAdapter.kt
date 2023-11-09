package co.edu.eam.proyectounilocal.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.actividades.ModDetalleLugarActivity
import co.edu.eam.proyectounilocal.actividades.ModMainActivity
import co.edu.eam.proyectounilocal.bd.LugaresService
import co.edu.eam.proyectounilocal.modelo.EstadoLugar
import co.edu.eam.proyectounilocal.modelo.Lugar

class LugaresModAdapter(var lista: ArrayList<Lugar>): RecyclerView.Adapter<LugaresModAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_lugar_mod, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind( lista[position] )
    }

    override fun getItemCount() = lista.size

    inner class ViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val nombre: TextView = itemView.findViewById(R.id.nombre_lugar)
        val btnAprobar: TextView = itemView.findViewById(R.id.btn_aprobar)
        val btnRechazar: TextView = itemView.findViewById(R.id.btn_rechazar)
        var codigoLugar: String = ""

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(lugar: Lugar){
            codigoLugar = lugar.key
            nombre.text = lugar.nombre
            btnAprobar.setOnClickListener {
                LugaresService.editarEstadoLugar(lugar, EstadoLugar.ACEPTADO){res ->
                    if(res){
                        lista.remove(lugar)
                        ModMainActivity.binding.viewPager.adapter = ViewPagerAdapterMod(ModMainActivity.act)
                    }
                }
            }
            btnRechazar.setOnClickListener {
                LugaresService.editarEstadoLugar(lugar, EstadoLugar.RECHAZADO){res ->
                    if(res){
                        lista.remove(lugar)
                        ModMainActivity.binding.viewPager.adapter = ViewPagerAdapterMod(ModMainActivity.act)
                    }
                }
            }
        }

        override fun onClick(p0: View?) {
            //Enviar al lugar
            if(codigoLugar != ""){
                var intent = Intent(nombre.context, ModDetalleLugarActivity::class.java)
                intent.putExtra("codigo", codigoLugar)
                nombre.context.startActivity(intent)
            }
        }

    }
}