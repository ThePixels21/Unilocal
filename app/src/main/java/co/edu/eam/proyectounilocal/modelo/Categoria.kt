package co.edu.eam.proyectounilocal.modelo

class Categoria() {
    constructor(nombre:String, icono:String):this(){
        this.nombre = nombre
        this.icono = icono
    }
    var key:String = ""
    var nombre:String = ""
    var icono:String = ""

    override fun toString(): String {
        return "Categoria(key=$key, nombre='$nombre', icono='$icono')"
    }
}