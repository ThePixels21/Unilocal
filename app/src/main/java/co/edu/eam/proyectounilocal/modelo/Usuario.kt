package co.edu.eam.proyectounilocal.modelo

class Usuario(){

    var key: String = ""
    var nombre: String = ""
    var nickname: String = ""
    var ciudad: String = ""
    var correo: String = ""
    var rol: Rol = Rol.USUARIO
    var busquedasRecientes: ArrayList<String> = ArrayList()
    var lugaresFavoritos:ArrayList<String> = ArrayList()

    constructor(nombre: String, nickname:String, ciudad:String, correo: String, rol: Rol) : this() {
        this.nombre = nombre
        this.nickname = nickname
        this.ciudad = ciudad
        this.correo = correo
        this.rol = rol
    }

    fun agregarBusqueda(busqueda:String){
        if(this.busquedasRecientes.size>9){
            this.busquedasRecientes.removeFirst()
        }
        this.busquedasRecientes.add(busqueda)
    }

    fun agregarFavorito(keyLugar: String){
        if(this.lugaresFavoritos.size>19){
            this.lugaresFavoritos.removeFirst()
        }
        this.lugaresFavoritos.add(keyLugar)
    }

    fun eliminarFavorito(keyLugar: String){
        this.lugaresFavoritos.remove(keyLugar)
    }

    fun eliminarBusquedas(){
        this.busquedasRecientes = ArrayList()
    }

    override fun toString(): String {
        return "Usuario(nickname='$nickname') ${super.toString()}"
    }

    fun buscarFavorito(keyLugar:String): Boolean{
        for(codigo in lugaresFavoritos){
            if(codigo == keyLugar){
                return true
            }
        }
        return false
    }
}