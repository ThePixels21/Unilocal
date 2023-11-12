package co.edu.eam.proyectounilocal.modelo

class Ciudad() {
    constructor(nombre:String):this(){
        this.nombre = nombre
    }

    var key:String = ""
    var nombre:String = ""

    override fun toString(): String {
        return "Ciudad(id=$key, nombre='$nombre')"
    }
}