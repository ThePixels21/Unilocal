package co.edu.eam.proyectounilocal.modelo

import java.util.Date

class Comentario(var texto:String, var idUsuario:Int, var idLugar:Int, var calificacion:Int) {

    var id: Int = 0
    var fecha:Date = Date()
}