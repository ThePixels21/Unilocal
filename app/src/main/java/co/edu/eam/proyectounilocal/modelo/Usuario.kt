package co.edu.eam.proyectounilocal.modelo

class Usuario(){

    var key: String = ""
    var id: Int = 0
    var nombre: String = ""
    var nickname: String = ""
    var ciudad: String = ""
    var correo: String = ""
    var password: String = ""
    var busquedasRecientes: ArrayList<String> = ArrayList()
    var lugaresFavoritos:ArrayList<Int> = ArrayList()
    var rol: Rol = Rol.USUARIO

    constructor(id: Int, nombre: String, nickname:String, ciudad:String, correo: String, password: String, rol: Rol) : this() {
        this.id = id
        this.nombre = nombre
        this.nickname = nickname
        this.ciudad = ciudad
        this.correo = correo
        this.password = password
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

    fun buscarFavorito(codigoLugar:Int): Boolean{
        for(codigo in lugaresFavoritos){
            if(codigo == codigoLugar){
                return true
            }
        }
        return false
    }
}