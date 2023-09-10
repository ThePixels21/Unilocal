package co.edu.eam.proyectounilocal.modelo

import java.time.LocalDate

class Comentario(var id:Int,var texto:String, var idUsuario:Int, var idLugar:Int, var calificacion:Int, var fecha:LocalDate) {
}