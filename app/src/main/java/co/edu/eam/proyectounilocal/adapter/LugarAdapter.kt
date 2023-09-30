package co.edu.eam.proyectounilocal.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.actividades.DetalleLugarActivity
import co.edu.eam.proyectounilocal.actividades.GestionarLugarActivity
import co.edu.eam.proyectounilocal.bd.Comentarios
import co.edu.eam.proyectounilocal.modelo.Lugar
import org.w3c.dom.Text

class LugarAdapter(var lista:ArrayList<Lugar>, codigoUsuario: Int = -1): RecyclerView.Adapter<LugarAdapter.ViewHolder>() {
    val codigoUsuario = codigoUsuario

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
        val comentarios: TextView = itemView.findViewById(R.id.comentarios_lugar)
        val calificacion: TextView = itemView.findViewById(R.id.calificacion_lugar)
        val listaEstrellas: LinearLayout = itemView.findViewById(R.id.lista_estrellas)
        var codigoLugar: Int = 0
        var lugarActual: Lugar? = null

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(lugar: Lugar){
            lugarActual = lugar
            nombre.text = lugar.nombre
            codigoLugar = lugar.id
            val abierto = lugar.estaAbierto()
            if(abierto){
                estado.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                estado.text = "Cierra a las ${lugar.obtenerHoraCierre()}:00"
            } else {
                estado.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                estado.text = "Cerrado"
            }

            val  cantComentarios = Comentarios.listar(lugar.id).size

            if(cantComentarios==1){
                comentarios.text = "${cantComentarios.toString()} comentario"
            } else {
                comentarios.text = "${cantComentarios.toString()} comentarios"
            }

            val cal: Int = lugar.obtenerCalificacionPromedio(Comentarios.listar(lugar.id))

            calificacion.text = cal.toString()

            if(cal != 0){
                for (i in 0 until cal){
                    (listaEstrellas[i] as TextView).setTextColor(ContextCompat.getColor(listaEstrellas.context, R.color.yellow))
                }
            }

        }

        override fun onClick(p0: View?) {
            if(codigoUsuario != -1 && lugarActual != null && lugarActual!!.idCreador == codigoUsuario){
                var intent = Intent(nombre.context, GestionarLugarActivity::class.java)
                intent.putExtra("codigo", codigoLugar)
                nombre.context.startActivity(intent)
            } else{
                var intent = Intent(nombre.context, DetalleLugarActivity::class.java)
                intent.putExtra("codigo", codigoLugar)
                nombre.context.startActivity(intent)
            }
        }
    }
}