package co.edu.eam.proyectounilocal.modelo

class Usuario(id: Int, nombre: String, var nickname:String, var ciudad:String, correo: String, password: String): Persona(id, nombre, correo, password){
    var busquedasRecientes: ArrayList<String> = ArrayList()

    fun agregarBusqueda(busqueda:String){
        this.busquedasRecientes.add(busqueda)
    }

    fun eliminarBusquedas(){
        this.busquedasRecientes = ArrayList()
    }

    override fun toString(): String {
        return "Usuario(nickname='$nickname') ${super.toString()}"
    }
}