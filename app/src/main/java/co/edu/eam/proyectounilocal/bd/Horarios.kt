package co.edu.eam.proyectounilocal.bd

import co.edu.eam.proyectounilocal.modelo.DiaSemana
import co.edu.eam.proyectounilocal.modelo.Horario

object Horarios {

    val lista:ArrayList<Horario> = ArrayList()

    fun obtenerTodos():ArrayList<DiaSemana>{
        val todosDias:ArrayList<DiaSemana> = ArrayList()
        todosDias.addAll( DiaSemana.values() )
        return todosDias
    }

    fun obtenerFinSemana():ArrayList<DiaSemana>{
        val todosDias:ArrayList<DiaSemana> = ArrayList()
        todosDias.add(DiaSemana.VIERNES)
        todosDias.add(DiaSemana.SABADO)

        return todosDias
    }

    fun obtenerEntreSemana():ArrayList<DiaSemana>{
        val todosDias:ArrayList<DiaSemana> = ArrayList()
        todosDias.add( DiaSemana.LUNES )
        todosDias.add( DiaSemana.MARTES )
        todosDias.add( DiaSemana.MIERCOLES )
        todosDias.add( DiaSemana.JUEVES )
        todosDias.add( DiaSemana.VIERNES )

        return todosDias
    }

}