package co.edu.eam.proyectounilocal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.proyectounilocal.R
import com.bumptech.glide.Glide

class ImagenesAdapter(var lista: ArrayList<String>): RecyclerView.Adapter<ImagenesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_imagen, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    override fun getItemCount() = lista.size

    inner class ViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val imagen: ImageView = itemView.findViewById(R.id.img)

        fun bind(url: String){
            Glide.with(itemView.context)
                .load(url)
                .into(imagen)
        }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }

    }

}