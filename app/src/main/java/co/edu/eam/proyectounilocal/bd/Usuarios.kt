package co.edu.eam.proyectounilocal.bd

import co.edu.eam.proyectounilocal.modelo.Usuario

object Usuarios {

    private val usuarios: ArrayList<Usuario> = ArrayList()

    init {
        usuarios.add(Usuario(1, "Santiago", "santiago", "Armenia", "santiago@email.com", "a"))
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