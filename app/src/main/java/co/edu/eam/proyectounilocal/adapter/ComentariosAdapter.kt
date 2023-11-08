package co.edu.eam.proyectounilocal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.actividades.DetalleLugarActivity
import co.edu.eam.proyectounilocal.actividades.MainActivity
import co.edu.eam.proyectounilocal.bd.Comentarios
import co.edu.eam.proyectounilocal.bd.Usuarios
import co.edu.eam.proyectounilocal.fragmentos.ComentariosLugarFragment
import co.edu.eam.proyectounilocal.modelo.Comentario
import org.w3c.dom.Text

class ComentariosAdapter(var lista:ArrayList<Comentario>, var codigoUsuario: Int): RecyclerView.Adapter<ComentariosAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_comentario, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    override fun getItemCount() = lista.size

    inner class ViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val imgUsuario: ImageView = itemView.findViewById(R.id.img_usuario)
        val nombreUsuario: TextView = itemView.findViewById(R.id.nombre_usuario_comentario)
        val listaEstrellas: LinearLayout = itemView.findViewById(R.id.lista_estrellas_comentario)
        val fecha: TextView = itemView.findViewById(R.id.fecha_comentario)
        val coment: TextView = itemView.findViewById(R.id.comentario)
        val btnEliminar: TextView =  itemView.findViewById(R.id.btn_eliminar_comentario)

        fun bind(comentario: Comentario){
            val usuario = Usuarios.buscar(comentario.idUsuario)

            if(comentario.idUsuario == codigoUsuario){
                btnEliminar.setOnClickListener{
                    Comentarios.eliminarComentario(comentario)
                    //ARREGLAR TOSTRING
                    DetalleLugarActivity.binding.viewPager.adapter =  ViewPagerAdapterLugar(ComentariosLugarFragment.act, comentario.idLugar.toString(), 1)
                    DetalleLugarActivity.binding.viewPager.setCurrentItem(1)
                }
            }else{
                btnEliminar.isVisible = false
            }

            if(usuario != null){
                nombreUsuario.text = usuario.nickname
            }

            val cal: Int = comentario.calificacion

            if(cal != 0){
                for (i in 0 until cal){
                    (listaEstrellas[i] as TextView).setTextColor(ContextCompat.getColor(listaEstrellas.context, R.color.yellow))
                }
            }

            fecha.text = comentario.fecha.toString().substring(0, 10)

            coment.text = comentario.texto
        }

        override fun onClick(p0: View?) {}

    }
}