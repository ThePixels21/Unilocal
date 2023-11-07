package co.edu.eam.proyectounilocal.modelo

import java.util.Date

class Comentario() {

    constructor(texto:String, idUsuario:Int, idLugar: Int, calificacion:Int):this(){
        this.texto = texto
        this.idUsuario = idUsuario
        this.calificacion = calificacion
        this.idLugar = idLugar
    }

    var key:String = ""
    var id: Int = 0
    var texto:String = ""
    var idUsuario:Int = 0
    var idLugar: Int = 0
    var calificacion:Int = 0
    var fecha:Date = Date()
}