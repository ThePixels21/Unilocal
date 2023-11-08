package co.edu.eam.proyectounilocal.bd

import co.edu.eam.proyectounilocal.modelo.Comentario

object Comentarios {

    private val lista:ArrayList<Comentario> = ArrayList()

    init {

        var comentario1 = Comentario("Excelente servicio y buen ambiente", 1, 2, 5 )
        comentario1.id = 1
        lista.add( comentario1 )
        var comentario2 = Comentario("Muy demorado, no volvería", 4, 1, 2 )
        comentario2.id = 2
        lista.add( comentario2 )
        var comentario3 = Comentario("Buena comida mexicana, precios razonables", 3, 3, 4 )
        comentario3.id = 3
        lista.add( comentario3 )
        var comentario4 = Comentario("El lugar es bonito pero muy lentos", 2, 2, 3 )
        comentario4.id = 4
        lista.add( comentario4 )
        var comentario5 = Comentario("No volvería, los precios son muy altos", 5, 2, 2 )
        comentario5.id = 5
        lista.add( comentario5 )
        var comentario6 = Comentario("Un hotel bien ubicado y con desayuno incluído. Recomendado.", 6, 5, 5 )
        comentario6.id = 6
        lista.add( comentario6 )

    }

    fun listar(idLugar:Int):ArrayList<Comentario>{
        return lista.filter { c -> c.idLugar == idLugar }.toCollection(ArrayList())
    }

    fun crear(comentario: Comentario){
        comentario.id = lista.size+1
        lista.add( comentario )
    }

    fun comentado(idLugar:String, idUsuario:Int): Boolean{
        //ARREGLAR
        var list = listar(0)
        for (coment in list){
            if(coment.idUsuario == idUsuario){
                return true
            }
        }
        return false
    }

    fun eliminarComentario(comentario: Comentario){
        lista.remove(comentario)
    }
}