package co.edu.eam.proyectounilocal.bd

import android.util.Log
import co.edu.eam.proyectounilocal.modelo.Ciudad
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object CiudadesService {

    fun listar(callback: (ArrayList<Ciudad>) -> Unit){
        val ciudades: ArrayList<Ciudad> = ArrayList()
        Firebase.firestore
            .collection("ciudades")
            .get()
            .addOnSuccessListener {
                for(doc in it){
                    var ciudad = doc.toObject(Ciudad::class.java)
                    ciudad.key = doc.id
                    ciudades.add(ciudad)
                }
                callback(ciudades)
            }
            .addOnFailureListener {
                Log.e("CiudadesService_listar", "${it.message}")
                callback(ciudades)
            }
    }

}