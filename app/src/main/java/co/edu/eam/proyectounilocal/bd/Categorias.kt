package co.edu.eam.proyectounilocal.bd

import co.edu.eam.proyectounilocal.modelo.Categoria
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object Categorias {

    private val categorias: ArrayList<Categoria> = ArrayList()

    init {
        /*categorias.add(Categoria("Hotel", "\uf594"))
        categorias.add(Categoria("Café", "\uf7b6"))
        categorias.add(Categoria("Restaurante", "\uf2e7"))
        categorias.add(Categoria("Parque", "\uf1bb"))
        categorias.add(Categoria("Bar", "\uf0fc"))
        categorias.add(Categoria("Centro Comercial", "\uf54e"))
        categorias.add(Categoria("Tienda", "\uf07a"))
        categorias.add(Categoria("Museo", "\uf66f"))*/
    }

    /*fun listar(): ArrayList<Categoria> {
        return categorias
    }*/

    /*fun obtener(id: Int): Categoria? {
        return categorias.firstOrNull { c -> c.id == id }
    }*/
}