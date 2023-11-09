package co.edu.eam.proyectounilocal.modelo

class Usuario(){

    var id: Int = 0

    var key: String = ""
    var nombre: String = ""
    var nickname: String = ""
    var ciudad: String = ""
    var correo: String = ""
    var busquedasRecientes: ArrayList<String> = ArrayList()
    var lugaresFavoritos:ArrayList<Int> = ArrayList()
    var rol: Rol = Rol.USUARIO

    constructor(nombre: String, nickname:String, ciudad:String, correo: String, rol: Rol) : this() {
        this.nombre = nombre
        this.nickname = nickname
        this.ciudad = ciudad
        this.correo = correo
        this.rol = rol
    }

    fun agregarBusqueda(busqueda:String){
        this.busquedasRecientes.add(busqueda)
    }

    fun eliminarBusquedas(){
        this.busquedasRecientes = ArrayList()
    }

    override fun toString(): String {
        return "Usuario(nickname='$nickname') ${super.toString()}"
    }

    fun buscarFavorito(keyLugar:Int): Boolean{
        for(codigo in lugaresFavoritos){
            if(codigo == keyLugar){
                return true
            }
        }
        return false
    }
}