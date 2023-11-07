package co.edu.eam.proyectounilocal.modelo

import java.time.LocalDate
import java.util.Calendar
import java.util.Date

class Lugar() {

    constructor( nombre:String, descripcion:String, idCreador:Int, estado:EstadoLugar, idCategoria:Int, direccion:String, latitud:Float, longitud:Float, idCiudad:Int):this(){
        this.nombre = nombre
        this.descripcion = descripcion
        this.idCreador = idCreador
        this.estado = estado
        this.idCategoria = idCategoria
        this.direccion = direccion
        this.latitud = latitud
        this.longitud = longitud
        this.idCiudad = idCiudad
    }
    var key:String = ""
    var id:Int = 0
    var nombre:String = ""
    var descripcion:String = ""
    var idCreador:Int = 0
    var estado:EstadoLugar = EstadoLugar.SIN_REVISAR
    var idCategoria:Int = 0
    var direccion:String = ""
    var idCiudad:Int = 0
    var latitud:Float = 0f
    var longitud:Float = 0f
    var imagenes:ArrayList<String> = ArrayList()
    var telefonos:ArrayList<String> = ArrayList()
    var fecha: Date = Date()
    var horarios:ArrayList<Horario> = ArrayList()

    fun estaAbierto(): Boolean{
        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)
        val hora = fecha.get(Calendar.HOUR_OF_DAY)
        //val minuto = fecha.get(Calendar.MINUTE)

        var abierto = false

        for(horario in horarios){
            if( horario.diaSemana.contains( DiaSemana.values()[dia-1] ) && hora < horario.horaFinal && hora > horario.horaInicio  ){
                abierto = true
                break
            }
        }

        return abierto
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

    fun obtenerCalificacionPromedio(comentarios:ArrayList<Comentario>):Int{
        var promedio = 0
        if(comentarios.size > 0){
            val suma = comentarios.stream().map { c -> c.calificacion }
                .reduce { suma, valor -> suma + valor }.get()

            promedio = suma/comentarios.size
        }
        return promedio
    }

    override fun toString(): String {
        return "Lugar(id=$id, nombre='$nombre', descripcion='$descripcion', idCreador=$idCreador, estado=$estado, idCategoria=$idCategoria, latitud=$latitud, longitud=$longitud, idCiudad=$idCiudad, imagenes=$imagenes, telefonos=$telefonos, fecha=$fecha, horarios=$horarios)"
    }
}