package co.edu.eam.proyectounilocal.bd

import android.util.Log
import co.edu.eam.proyectounilocal.modelo.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object UsuariosService {

    fun buscar(keyUsuario: String, callback: (Usuario?) -> Unit){
        Firebase.firestore
            .collection("usuarios")
            .document(keyUsuario)
            .get()
            .addOnSuccessListener {
                val usuario = it.toObject(Usuario::class.java)
                if(usuario != null){
                    usuario.key = it.id
                    callback(usuario)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                Log.e("UsuariosService_buscar", it.message.toString())
                callback(null)
            }
    }

    fun actualizarUsuario(usuario: Usuario, callback: (Boolean) -> Unit){
        Firebase.firestore
            .collection("usuarios")
            .document(usuario.key)
            .set(usuario)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener {
                Log.e("UsuariosService_actualizarUsuario", it.message.toString())
                callback(false)
            }
    }

}