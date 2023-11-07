package co.edu.eam.proyectounilocal.modelo

open class Persona() {

    open var key: String = ""
    open var id: Int = 0
    open var nombre: String = ""
    open var correo: String = ""
    open var password: String = ""

    constructor(id:Int, nombre:String, correo:String, password:String) : this() {
        this.id = id
        this.nombre = nombre
        this.correo = correo
        this.password = password
    }

    override fun toString(): String {
        return "Persona(id=$id, nombre='$nombre', correo='$correo', password='$password')"
    }

}