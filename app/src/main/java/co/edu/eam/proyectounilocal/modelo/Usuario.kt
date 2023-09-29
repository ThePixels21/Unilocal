package co.edu.eam.proyectounilocal.modelo

class Usuario(id: Int, nombre: String, var nickname:String, var ciudad:String, correo: String, password: String): Persona(id, nombre, correo, password){
    var busquedasRecientes: ArrayList<String> = ArrayList()
    var lugaresFavoritos:ArrayList<Int> = ArrayList()

    fun agregarBusqueda(busqueda:String){
        this.busquedasRecientes.add(busqueda)
    }

    fun eliminarBusquedas(){
        this.busquedasRecientes = ArrayList()
    }

    override fun toString(): String {
        return "Usuario(nickname='$nickname') ${super.toString()}"
    }

    fun buscarFavorito(codigoLugar:Int): Boolean{
        for(codigo in lugaresFavoritos){
            if(codigo == codigoLugar){
                return true
            }
        }
        return false
    }
}