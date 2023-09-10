package co.edu.eam.proyectounilocal.modelo

import java.time.LocalDate

class Lugar(var id:Int,
            var nombre:String,
            var desccripcion:String,
            var imagenes:List<String>,
            var idCrador:Int,
            var estado:Boolean,
            var latitud:Float,
            var longitud:Float,
            var idCiudad:Int,
            var fecha: LocalDate
) {
    var telefonos:List<String> = ArrayList()
}