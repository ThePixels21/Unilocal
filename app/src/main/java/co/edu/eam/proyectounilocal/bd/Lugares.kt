package co.edu.eam.proyectounilocal.bd

import co.edu.eam.proyectounilocal.modelo.Comentario
import co.edu.eam.proyectounilocal.modelo.EstadoLugar
import co.edu.eam.proyectounilocal.modelo.Horario
import co.edu.eam.proyectounilocal.modelo.Lugar
import co.edu.eam.proyectounilocal.modelo.Posicion
import co.edu.eam.proyectounilocal.modelo.RegistroEstadoLugar

object Lugares {

    private val lista:ArrayList<Lugar> = ArrayList()
    private val registros:ArrayList<RegistroEstadoLugar> = ArrayList()

    init {
        /*
        val horario1 = Horario(1, Horarios.obtenerTodos(), 12, 20)
        val horario2 = Horario(2, Horarios.obtenerEntreSemana(), 9, 12)
        val horario3 = Horario(3, Horarios.obtenerFinSemana(), 14, 23)

        val tels:ArrayList<String> = ArrayList()
        tels.add("7828789")
        tels.add("7464657")

        val lugar1 = Lugar("Café ABC", "Excelente café para compartir", 1, EstadoLugar.ACEPTADO, 2, "Calle 123", Posicion(4.5106545,-75.7050586), 1)
        lugar1.id = 1
        lugar1.horarios.add(horario2)

        val lugar2 = Lugar("Bar City Pub", "Bar en la ciudad de Armenia", 2, EstadoLugar.ACEPTADO, 5, "Calle 12 # 23-1", Posicion(4.5276916,-75.6973675), 1)
        lugar2.id = 2
        lugar2.telefonos = tels
        lugar2.horarios.add(horario1)

        val lugar3 = Lugar("Restaurante Mi Cuate", "Comida Mexicana para todos", 3, EstadoLugar.ACEPTADO, 3, "Calle 452", Posicion(4.5057171,-75.7022026), 5)
        lugar3.id = 3
        lugar3.horarios.add(horario1)

        val lugar4 = Lugar("BBC Norte Pub", "Cervezas BBC muy buenas", 1, EstadoLugar.ACEPTADO, 5, "Calle 53", Posicion(4.5433488,-75.6739659), 3)
        lugar4.id = 4
        lugar4.horarios.add(horario3)

        val lugar5 = Lugar("Hotel barato", "Hotel para ahorrar mucho dinero", 1, EstadoLugar.ACEPTADO, 1, "Calle 23 # 34-1", Posicion(4.5452105,-75.671197), 1)
        lugar5.id = 5
        lugar5.horarios.add( horario1 )

        val lugar6 = Lugar("Hostal Hippie", "Alojamiento para todos y todas", 2, EstadoLugar.SIN_REVISAR, 1, "Carrera 123", Posicion(4.5452105,-75.671197), 2)
        lugar6.id = 6
        lugar6.horarios.add( horario2 )

        lista.add( lugar1 )
        lista.add( lugar2 )
        lista.add( lugar3 )
        lista.add( lugar4 )
        lista.add( lugar5 )
        lista.add( lugar6 )

         */

    }

    fun listarPorEstado(estado:EstadoLugar):ArrayList<Lugar>{
        return lista.filter { l -> l.estado == estado }.toCollection(ArrayList())
    }

    fun obtener(id:Int): Lugar?{
        return lista.firstOrNull { l -> l.id == id }
    }

    fun eliminar(lugar: Lugar){
        lista.remove(lugar)
    }

    fun obtenerFavoritos(codigoUsuario: Int):ArrayList<Lugar>{
        val usuario = Usuarios.buscar(codigoUsuario)
        val lista: ArrayList<Lugar> = ArrayList()
        if(usuario != null){
            for (codigoLugar in usuario.lugaresFavoritos){
                val lugar = obtener(codigoLugar)
                if(lugar != null){
                    lista.add(lugar)
                }
            }
        }
        return lista
    }

    fun buscarNombre(nombre:String): ArrayList<Lugar> {
        return lista.filter { l -> l.nombre.lowercase().contains(nombre.lowercase()) && l.estado == EstadoLugar.ACEPTADO }.toCollection(ArrayList())
    }

    fun crear(lugar:Lugar){
        lugar.id = lista.size + 1
        lista.add( lugar )
    }

    fun buscarCiudad(codigoCiudad:Int): ArrayList<Lugar> {
        return lista.filter { l -> l.idCiudad == codigoCiudad && l.estado == EstadoLugar.ACEPTADO }.toCollection(ArrayList())
    }

    fun buscarCategoria(codigoCategoria:Int): ArrayList<Lugar> {
        return lista.filter { l -> l.idCategoria == codigoCategoria && l.estado == EstadoLugar.ACEPTADO }.toCollection(ArrayList())
    }

    fun listarPorPropietario(codigo:Int):ArrayList<Lugar>{
        return lista.filter { l -> l.idCreador == codigo }.toCollection(ArrayList())
    }

    fun agregarRegistro(lugar: Lugar, nuevoEstado: EstadoLugar){
        registros.add(RegistroEstadoLugar(lugar, nuevoEstado))
    }

    fun obtenerRegistros(): ArrayList<RegistroEstadoLugar>{
        return registros
    }

    fun cambiarEstado(codigo:Int, nuevoEstado:EstadoLugar){

        val lugar = lista.firstOrNull{ l -> l.id == codigo}

        if(lugar!=null){
            lugar.estado = nuevoEstado
        }

    }

}