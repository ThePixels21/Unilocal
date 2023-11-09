package co.edu.eam.proyectounilocal.modelo

import java.util.Date

class Comentario() {

    constructor(texto:String, keyUsuario:String, calificacion:Int):this(){
        this.texto = texto
        this.keyUsuario = keyUsuario
        this.calificacion = calificacion
    }

    var key:String = ""
    var texto:String = ""
    var keyUsuario:String = ""
    var calificacion:Int = 0
    var fecha:Date = Date()
}