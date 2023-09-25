package co.edu.eam.proyectounilocal.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.actividades.DetalleLugarActivity
import co.edu.eam.proyectounilocal.modelo.Lugar

class LugarAdapter(var lista:ArrayList<Lugar>): RecyclerView.Adapter<LugarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_lugar, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind( lista[position] )
    }

    inner class ViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val nombre: TextView = itemView.findViewById(R.id.nombre_lugar)
        val estado: TextView = itemView.findViewById(R.id.estado_lugar)
        val imagen: ImageView = itemView.findViewById(R.id.img_lugar)
        val comentario: TextView = itemView.findViewById(R.id.comentarios_lugar)
        val calificacion: TextView = itemView.findViewById(R.id.calificacion_lugar)
        var codigoLugar: Int = 0

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(lugar: Lugar){
            nombre.text = lugar.nombre
            codigoLugar = lugar.id
            if(lugar.estaAbierto() == "Abierto"){
                estado.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                estado.text = "Cierra a las ${lugar.obtenerHoraCierre()}:00"
            } else {
                estado.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                estado.text = lugar.estaAbierto()
            }
        }

        override fun onClick(p0: View?) {
            var intent = Intent(nombre.context, DetalleLugarActivity::class.java)
            intent.putExtra("codigo", codigoLugar)
            nombre.context.startActivity(intent)
        }
    }
}