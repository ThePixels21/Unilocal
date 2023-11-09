package co.edu.eam.proyectounilocal.bd

import android.util.Log
import android.widget.Toast
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.modelo.Comentario
import co.edu.eam.proyectounilocal.modelo.EstadoLugar
import co.edu.eam.proyectounilocal.modelo.Lugar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

object LugaresService {

    fun crearLugar(lugar: Lugar, callback: (Boolean) -> Unit){
        Firebase.firestore
            .collection("lugares")
            .add(lugar)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                Log.e("LugaresService_crearLugar", "${it.message}")
                callback(false)
            }
    }

    fun obtener(key: String, callback: (Lugar?) -> Unit) {
        Firebase.firestore
            .collection("lugares")
            .document(key)
            .get()
            .addOnSuccessListener {
                var lugarF = it.toObject(Lugar::class.java)
                if (lugarF != null) {
                    lugarF.key = it.id
                    callback(lugarF) // Llama al callback con el lugar
                } else {
                    callback(null) // Llama al callback con null si no se encontró el lugar
                }
            }
            .addOnFailureListener {
                Log.e("LugaresService_obtener", "${it.message}")
                callback(null) // Llama al callback con null si ocurrió un error
            }
    }

    fun eliminarLugar(key: String, callback: (Boolean) -> Unit){
        Firebase.firestore
            .collection("lugares")
            .document(key)
            .delete()
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener {
                Log.e("LugaresService_eliminarLugar", it.message.toString())
                callback(false) }
    }

    fun listarLugaresPorEstado(estado: EstadoLugar, callback: (ArrayList<Lugar>) -> Unit) {
        val lugares: ArrayList<Lugar> = ArrayList()
        Firebase.firestore
            .collection("lugares")
            .whereEqualTo("estado", estado)
            .get()
            .addOnSuccessListener {
                for(doc in it){
                    var lugar = doc.toObject(Lugar::class.java)
                    lugar.key = doc.id
                    lugares.add(lugar)
                }
                callback(lugares) // Llama al callback con los lugares
            }
            .addOnFailureListener {
                Log.e("LugaresService_listarLugaresAceptados", it.message.toString())
                callback(lugares)
            }
    }

    fun listarPorCategoria(keyCategoria: String, callback: (ArrayList<Lugar>) -> Unit){
        val lugares: ArrayList<Lugar> = ArrayList()
        Firebase.firestore
            .collection("lugares")
            .whereEqualTo("keyCategoria", keyCategoria)
            .get()
            .addOnSuccessListener {
                for(doc in it){
                    var lugar = doc.toObject(Lugar::class.java)
                    lugar.key = doc.id
                    lugares.add(lugar)
                }
                callback(lugares)
            }
            .addOnFailureListener {
                Log.e("LugaresService_listarPorCategoria", it.message.toString())
                callback(lugares)
            }
    }

    //Corregir idUsuario
    fun listarLugaresPorPropietario(idUsuario: Int, callback: (ArrayList<Lugar>) -> Unit){
        val lugares: ArrayList<Lugar> = ArrayList()
        Firebase.firestore
            .collection("lugares")
            .whereEqualTo("idCreador", idUsuario)
            .get()
            .addOnSuccessListener {
                for(doc in it){
                    var lugar = doc.toObject(Lugar::class.java)
                    lugar.key = doc.id
                    lugares.add(lugar)
                }
                callback(lugares) // Llama al callback con los lugares
            }
            .addOnFailureListener {
                Log.e("LugaresService_listarLugaresPorPropietario", it.message.toString())
                callback(lugares)
            }
    }

    fun agregarComentario(comentario: Comentario, keyLugar: String, callback: (Boolean) -> Unit) {
        Firebase.firestore
            .collection("lugares")
            .document(keyLugar)
            .collection("comentarios")
            .add(comentario)
            .addOnSuccessListener {
                callback(true) // Llama al callback con true
            }
            .addOnFailureListener {
                Log.e("LugaresService_agregarComentario", "${it.message}")
                callback(false) // Llama al callback con false si ocurrió un error
            }
    }

    fun listarComentarios(keyLugar: String, callback: (ArrayList<Comentario>) -> Unit){
        val comentarios: ArrayList<Comentario> = ArrayList()
        Firebase.firestore
            .collection("lugares")
            .document(keyLugar)
            .collection("comentarios")
            .get()
            .addOnSuccessListener {
                for(doc in it){
                    var comentario = doc.toObject(Comentario::class.java)
                    comentario.key = doc.id
                    comentarios.add(comentario)
                }
                callback(comentarios)
            }
            .addOnFailureListener {
                Log.e("LugaresService_listarComentarios", it.message.toString())
                callback(comentarios)
            }
    }

    fun eliminarComentario(keyComentario: String, keyLugar: String, callback: (Boolean) -> Unit){
        Firebase.firestore
            .collection("lugares")
            .document(keyLugar)
            .collection("comentarios")
            .document(keyComentario)
            .delete()
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener {
                Log.e("LugaresService_eliminarComentario", it.message.toString())
                callback(false) }
    }

    //Corregir idUsuario
    fun tieneComentarios(keyLugar: String, idUsuario: Int, callback: (Boolean) -> Unit) {
        Firebase.firestore
            .collection("lugares")
            .document(keyLugar)
            .collection("comentarios")
            .whereEqualTo("idUsuario", idUsuario)
            .get()
            .addOnSuccessListener {
                var res = false
                for(doc in it){
                    res = true
                    break
                }
                callback(res)
            }
            .addOnFailureListener {
                Log.e("LugaresService_tieneComentarios", it.message.toString())
                callback(false)
            }
    }

}