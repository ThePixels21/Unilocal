package co.edu.eam.proyectounilocal.bd

import android.util.Log
import android.widget.Toast
import co.edu.eam.proyectounilocal.R
import co.edu.eam.proyectounilocal.modelo.Comentario
import co.edu.eam.proyectounilocal.modelo.EstadoLugar
import co.edu.eam.proyectounilocal.modelo.Lugar
import co.edu.eam.proyectounilocal.modelo.RegistroEstadoLugar
import co.edu.eam.proyectounilocal.modelo.Usuario
import com.google.firebase.firestore.FieldPath
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

    fun listarLugaresPorPropietario(keyUsuario: String, callback: (ArrayList<Lugar>) -> Unit){
        val lugares: ArrayList<Lugar> = ArrayList()
        Firebase.firestore
            .collection("lugares")
            .whereEqualTo("idCreador", keyUsuario)
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

    fun obtenerLugaresFavoritos(keyUsuario: String, callback: (ArrayList<Lugar>) -> Unit){
        Firebase.firestore
            .collection("usuarios")
            .document(keyUsuario)
            .get()
            .addOnSuccessListener { document ->
                val usuario = document.toObject(Usuario::class.java)
                if(usuario != null){
                    val keysLugares = usuario.lugaresFavoritos
                    val lugares: ArrayList<Lugar> = ArrayList()
                    val chunks = keysLugares.chunked(10) // Divide el array en subarrays de tamaño 10
                    var completedChunks = 0

                    for (chunk in chunks) {
                        Firebase.firestore
                            .collection("lugares")
                            .whereIn(FieldPath.documentId(), chunk)
                            .get()
                            .addOnSuccessListener { result ->
                                for (doc in result) {
                                    var lugar = doc.toObject(Lugar::class.java)
                                    lugar.key = doc.id
                                    lugares.add(lugar)
                                }
                                completedChunks++
                                if (completedChunks == chunks.size) {
                                    callback(lugares) // Llama al callback solo cuando todas las consultas han terminado
                                }
                            }
                            .addOnFailureListener {
                                Log.e("LugaresService_obtenerFavoritos", it.message.toString())
                                completedChunks++
                                if (completedChunks == chunks.size) {
                                    callback(lugares)
                                }
                            }
                    }
                } else {
                    callback(ArrayList())
                }
            }
            .addOnFailureListener {
                Log.e("LugaresService_obtenerFavoritos", it.message.toString())
                callback(ArrayList())
            }
    }

    fun buscarPorNombre(nombre: String, callback: (ArrayList<Lugar>) -> Unit) {
        val lugares: ArrayList<Lugar> = ArrayList()
        Firebase.firestore
            .collection("lugares")
            .whereEqualTo("estado", EstadoLugar.ACEPTADO)
            .get()
            .addOnSuccessListener {
                for(doc in it){
                    var lugar = doc.toObject(Lugar::class.java)
                    if (lugar.nombre.lowercase().contains(nombre.lowercase())) {
                        lugar.key = doc.id
                        lugares.add(lugar)
                    }
                }
                callback(lugares) // Llama al callback con los lugares
            }
            .addOnFailureListener {
                Log.e("LugaresService_buscarPorNombre", it.message.toString())
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

    fun tieneComentarios(keyLugar: String, keyUsuario: String, callback: (Boolean) -> Unit) {
        Firebase.firestore
            .collection("lugares")
            .document(keyLugar)
            .collection("comentarios")
            .whereEqualTo("keyUsuario", keyUsuario)
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

    fun editarEstadoLugar(lugar: Lugar, estado: EstadoLugar, callback: (Boolean) -> Unit){
        val estadoAnterior = lugar.estado
        lugar.estado = estado
        Firebase.firestore
            .collection("lugares")
            .document(lugar.key)
            .set(lugar)
            .addOnSuccessListener {
                val registro = RegistroEstadoLugar(estadoAnterior, estado, lugar)
                agregarRegistro(registro){res ->
                    if(res){
                        callback(true)
                    } else {
                        callback(false)
                    }
                }
            }
            .addOnFailureListener {
                Log.e("LugaresService_editarEstadoLugar", it.message.toString())
                callback(false)
            }
    }

    fun agregarRegistro(registro: RegistroEstadoLugar, callback: (Boolean) -> Unit){
        Firebase.firestore
            .collection("registrosEstadoLugar")
            .add(registro)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener {
                Log.e("LugaresService_agregarRegistro", it.message.toString())
                callback(false)
            }
    }

    fun obtenerRegistros(callback: (ArrayList<RegistroEstadoLugar>) -> Unit){
        val registros: ArrayList<RegistroEstadoLugar> = ArrayList()
        Firebase.firestore
            .collection("registrosEstadoLugar")
            .get()
            .addOnSuccessListener {
                for (doc in it){
                    var registro = doc.toObject(RegistroEstadoLugar::class.java)
                    registro.key = doc.id
                    registros.add(registro)
                }
                callback(registros)
            }
            .addOnFailureListener {
                Log.e("LugaresService_obtenerRegistros", it.message.toString())
                callback(registros)
            }
    }

}