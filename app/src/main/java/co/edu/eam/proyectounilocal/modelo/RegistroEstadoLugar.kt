package co.edu.eam.proyectounilocal.modelo

class RegistroEstadoLugar() {

    constructor(lugar: Lugar, nuevoEstado: EstadoLugar) : this() {
        this.lugar = Lugar()
        this.nuevoEstado = nuevoEstado
    }

    var key: String = ""
    var lugar: Lugar = Lugar()
    var nuevoEstado: EstadoLugar = EstadoLugar.SIN_REVISAR

}