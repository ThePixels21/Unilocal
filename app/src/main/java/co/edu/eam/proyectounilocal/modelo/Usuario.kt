package co.edu.eam.proyectounilocal.modelo

class Usuario(): Persona(){

    override var key: String = ""
    override var id: Int = 0
    override var nombre: String = ""
    var nickname: String = ""
    var ciudad: String = ""
    override var correo: String = ""
    override var password: String = ""
    var busquedasRecientes: ArrayList<String> = ArrayList()
    var lugaresFavoritos:ArrayList<Int> = ArrayList()

    constructor(id: Int, nombre: String, nickname:String, ciudad:String, correo: String, password: String) : this() {
        this.id = id
        this.nombre = nombre
        this.nickname = nickname
        this.ciudad = ciudad
        this.correo = correo
        this.password = password
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