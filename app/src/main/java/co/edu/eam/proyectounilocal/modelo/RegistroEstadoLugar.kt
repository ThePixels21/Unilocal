package co.edu.eam.proyectounilocal.modelo

class RegistroEstadoLugar() {

    constructor(estadoAnterior: EstadoLugar, nuevoEstado: EstadoLugar, lugar: Lugar) : this() {
        this.estadoAnterior = estadoAnterior
        this.nuevoEstado = nuevoEstado
        this.lugar = lugar
    }

    var key: String = ""
    var estadoAnterior: EstadoLugar = EstadoLugar.SIN_REVISAR
    var nuevoEstado: EstadoLugar = EstadoLugar.SIN_REVISAR
    var lugar: Lugar = Lugar()

}