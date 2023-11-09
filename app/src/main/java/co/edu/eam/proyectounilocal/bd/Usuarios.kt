package co.edu.eam.proyectounilocal.bd

import co.edu.eam.proyectounilocal.modelo.Rol
import co.edu.eam.proyectounilocal.modelo.Usuario

object Usuarios {

    private val usuarios: ArrayList<Usuario> = ArrayList()

    init {
        /*usuarios.add(Usuario("Juan", "Juan", "Armenia", "mode@email.com", "a", Rol.MODERADOR))
        usuarios.add(Usuario("Santiago", "Santiago", "Armenia", "santiago@email.com", "a", Rol.USUARIO))
        usuarios.add( Usuario("Pepito", "Pepe", "Medellín", "pepe@email.com", "3451", Rol.USUARIO) )
        usuarios.add( Usuario("Laura", "Laura", "Cali", "laura@email.com", "6543", Rol.USUARIO) )
        usuarios.add( Usuario("Marcos", "Marcos", "Popayán", "marcos@email.com", "8635", Rol.USUARIO) )
        usuarios.add( Usuario("Maria", "Maria", "Pereira", "maria@email.com", "5437", Rol.USUARIO) )
        usuarios.add( Usuario("Carlos", "Carlos", "Barranquilla", "carlos@email.com", "1203", Rol.USUARIO) )*/
    }

    fun listar(): ArrayList<Usuario> {
        return usuarios
    }

    /*fun agregar(usuario: Usuario): Boolean {
        val correo = usuarios.firstOrNull{u -> u.correo == usuario.correo}
        val nickname = usuarios.firstOrNull{u -> u.nickname == usuario.nickname}
        if(correo == null && nickname == null && usuario.id == 0){
            val id = usuarios.get(usuarios.size-1).id + 1
            usuario.id = id
            usuarios.add(usuario)
            return true
        }
        return false
    }*/

    fun buscar(id:Int): Usuario?{
        return usuarios.firstOrNull { u -> u.id == id }
    }
}