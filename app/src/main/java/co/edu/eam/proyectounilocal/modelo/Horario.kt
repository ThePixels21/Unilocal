package co.edu.eam.proyectounilocal.modelo

class Horario() {

    constructor(id:Int, diaSemana:ArrayList<DiaSemana>, horaInicio:Int, horaFinal:Int  ):this(){
        this.diaSemana = diaSemana
        this.horaFinal = horaFinal
        this.horaInicio = horaInicio
    }

    var key: String = ""
    var id:Int = 0
    var diaSemana:ArrayList<DiaSemana> = ArrayList()
    var horaInicio:Int = 0
    var horaFinal:Int = 0
}