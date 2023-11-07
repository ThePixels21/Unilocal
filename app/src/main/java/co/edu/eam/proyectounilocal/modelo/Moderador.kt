package co.edu.eam.proyectounilocal.modelo

class Moderador(): Persona() {

    override var key: String = ""
    override var id: Int = 0
    override var nombre: String = ""
    override var correo: String = ""
    override var password: String = ""

    constructor(id: Int, nombre: String, correo: String, password: String) : this() {
        this.id = id
        this.nombre = nombre
        this.correo = correo
        this.password = password
    }

    override fun toString(): String {
        return "Moderador() ${super.toString()}"
    }

}