package co.edu.eam.proyectounilocal.bd

import co.edu.eam.proyectounilocal.modelo.Categoria

object Categorias {

    private val categorias: ArrayList<Categoria> = ArrayList()

    init {
        categorias.add(Categoria(1, "Hotel", "\uf594"))
        categorias.add(Categoria(2, "Café", "\uf7b6"))
        categorias.add(Categoria(3, "Restaurante", "\uf2e7"))
        categorias.add(Categoria(4, "Parque", "\uf1bb"))
        categorias.add(Categoria(5, "Bar", "\uf0fc"))
        categorias.add(Categoria(6, "Centro Comercial", "\uf54e"))
        categorias.add(Categoria(7, "Tienda", "\uf07a"))
        categorias.add(Categoria(8, "Museo", "\uf66f"))
    }

    fun listar(): ArrayList<Categoria> {
        return categorias
    }

    fun obtener(id: Int): Categoria? {
        return categorias.firstOrNull { c -> c.id == id }
    }
}