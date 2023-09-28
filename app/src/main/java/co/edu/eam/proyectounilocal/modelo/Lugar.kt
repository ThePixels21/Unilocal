package co.edu.eam.proyectounilocal.modelo

import java.time.LocalDate
import java.util.Calendar
import java.util.Date

class Lugar(var nombre:String,
            var descripcion:String,
            var idCreador:Int,
            var estado:EstadoLugar,
            var idCategoria:Int,
            var direccion:String,
            var latitud:Float, var longitud:Float,
            var idCiudad:Int
) {
    var id:Int = 0
    var imagenes:ArrayList<String> = ArrayList()
    var telefonos:ArrayList<String> = ArrayList()
    var fecha: Date = Date()
    var horarios:ArrayList<Horario> = ArrayList()

    fun estaAbierto(): String{
        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)
        val hora = fecha.get(Calendar.HOUR_OF_DAY)
        //val minuto = fecha.get(Calendar.MINUTE)

        var mensaje = "Cerrado"

        for(horario in horarios){
            if( horario.diaSemana.contains( DiaSemana.values()[dia-1] ) && hora < horario.horaInicio && hora > horario.horaFinal  ){
                mensaje = "Abierto"
                break
            }
        }

        return mensaje
    }

    fun obtenerHoraCierre():String{

        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)

        var mensaje = ""

        for(horario in horarios){
            if( horario.diaSemana.contains( DiaSemana.values()[dia-1] ) ){
                mensaje = horario.horaFinal.toString()
                break
            }
        }

        return mensaje
    }

    override fun toString(): String {
        return "Lugar(id=$id, nombre='$nombre', descripcion='$descripcion', idCreador=$idCreador, estado=$estado, idCategoria=$idCategoria, latitud=$latitud, longitud=$longitud, idCiudad=$idCiudad, imagenes=$imagenes, telefonos=$telefonos, fecha=$fecha, horarios=$horarios)"
    }
}