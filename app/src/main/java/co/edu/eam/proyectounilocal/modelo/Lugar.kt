package co.edu.eam.proyectounilocal.modelo

import java.util.Calendar
import java.util.Date

class Lugar() {

    constructor( nombre:String, descripcion:String, idCreador:String, estado:EstadoLugar, keyCategoria:String, direccion:String, posicion: Posicion, idCiudad:String):this(){
        this.nombre = nombre
        this.descripcion = descripcion
        this.idCreador = idCreador
        this.estado = estado
        this.keyCategoria = keyCategoria
        this.direccion = direccion
        this.posicion = posicion
        this.latitud = posicion.lat
        this.longitud = posicion.lng
        this.idCiudad = idCiudad
    }

    constructor( nombre:String, descripcion:String, idCreador:String, estado:EstadoLugar, keyCategoria:String, direccion:String, latitud:Double, longitud:Double, idCiudad:String):this(){
        this.nombre = nombre
        this.descripcion = descripcion
        this.idCreador = idCreador
        this.estado = estado
        this.keyCategoria = keyCategoria
        this.direccion = direccion
        this.latitud = latitud
        this.longitud = longitud
        val pos = Posicion(latitud, longitud)
        this.posicion = pos
        this.idCiudad = idCiudad
    }

    var key:String = ""
    var nombre:String = ""
    var descripcion:String = ""
    var idCreador:String = ""
    var estado:EstadoLugar = EstadoLugar.SIN_REVISAR
    var keyCategoria:String = ""
    var direccion:String = ""
    var posicion: Posicion = Posicion()
    var idCiudad:String = ""
    var latitud:Double = 0.0
    var longitud:Double = 0.0
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
        return "Lugar(id=$key, nombre='$nombre', descripcion='$descripcion', idCreador=$idCreador, estado=$estado, keyCategoria=$keyCategoria,posicion=$posicion, idCiudad=$idCiudad, imagenes=$imagenes, telefonos=$telefonos, fecha=$fecha, horarios=$horarios)"
    }
}