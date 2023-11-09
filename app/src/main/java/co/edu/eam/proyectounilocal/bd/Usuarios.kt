package co.edu.eam.proyectounilocal.bd

import co.edu.eam.proyectounilocal.modelo.Usuario

object Usuarios {

    private val usuarios: ArrayList<Usuario> = ArrayList()

    init {
        /*usuarios.add(Usuario(1, "Santiago", "Santiago", "Armenia", "santiago@email.com", "a"))
        usuarios.add( Usuario(2, "Pepito", "Pepe", "Medellín", "pepe@email.com", "3451") )
        usuarios.add( Usuario(3, "Laura", "Laura", "Cali", "laura@email.com", "6543") )
        usuarios.add( Usuario(4, "Marcos", "Marcos", "Popayán", "marcos@email.com", "8635") )
        usuarios.add( Usuario(5, "Maria", "Maria", "Pereira", "maria@email.com", "5437") )
        usuarios.add( Usuario(6, "Carlos", "Carlos", "Barranquilla", "carlos@email.com", "1203") )*/
    }

    fun listar(): ArrayList<Usuario> {
        return usuarios
    }

    fun agregar(usuario: Usuario): Boolean {
        val correo = usuarios.firstOrNull{u -> u.correo == usuario.correo}
        val nickname = usuarios.firstOrNull{u -> u.nickname == usuario.nickname}
        if(correo == null && nickname == null && usuario.id == 0){
            val id = usuarios.get(usuarios.size-1).id + 1
            usuario.id = id
            usuarios.add(usuario)
            return true
        }
        return false
    }

    fun buscar(id:Int): Usuario?{
        return usuarios.firstOrNull { u -> u.id == id }
    }
}