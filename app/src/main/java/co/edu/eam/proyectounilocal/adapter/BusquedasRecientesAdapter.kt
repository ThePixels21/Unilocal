package co.edu.eam.proyectounilocal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.actividades.BusquedaActivity
import co.edu.eam.proyectounilocal.modelo.Lugar

class BusquedasRecientesAdapter(var lista:ArrayList<String>): RecyclerView.Adapter<BusquedasRecientesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_reciente, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    override fun getItemCount() = lista.size

    inner class ViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val reciente: TextView = itemView.findViewById(R.id.reciente)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(busquedaReciente: String){
            reciente.text = busquedaReciente
        }

        override fun onClick(p0: View?) {
            //click
        }

    }
}