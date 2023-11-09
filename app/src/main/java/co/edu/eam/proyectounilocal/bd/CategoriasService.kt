package co.edu.eam.proyectounilocal.bd

import android.util.Log
import co.edu.eam.proyectounilocal.modelo.Categoria
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object CategoriasService {

    fun obtener(key: String, callback: (Categoria?) -> Unit){
        Firebase.firestore
            .collection("categorias")
            .document(key)
            .get()
            .addOnSuccessListener {
                var categoria = it.toObject(Categoria::class.java)
                if(categoria != null){
                    categoria.key = it.id
                    callback(categoria)
                }else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                Log.e("CategoriasService_obtener", "${it.message}")
                callback(null)
            }
    }

    fun listar(callback: (ArrayList<Categoria>) -> Unit){
        val categorias: ArrayList<Categoria> = ArrayList()
        Firebase.firestore
            .collection("categorias")
            .get()
            .addOnSuccessListener {
                for(doc in it){
                    var categoria = doc.toObject(Categoria::class.java)
                    categoria.key = doc.id
                    categorias.add(categoria)
                }
                callback(categorias)
            }
            .addOnFailureListener {
                Log.e("CategoriasService_listar", it.message.toString())
                callback(categorias)
            }
    }
}