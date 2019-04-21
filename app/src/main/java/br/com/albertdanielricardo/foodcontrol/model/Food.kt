package br.com.albertdanielricardo.foodcontrol.model
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Food {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var restaurante: String? = null
    var nota: Float? = null
    var descricao: String? = null
    var endereco: String? = null
    var numEndereco: String? = null
    var telefone: String? = null
    var imgFoto: String? = null

    constructor(){}

    constructor(restaurante: String,nota: Float, descricao: String, endereco: String, numEndereco: String, telefone: String){
        this.restaurante = restaurante
        this.nota = nota
        this.descricao = descricao
        this.endereco = endereco
        this.numEndereco = numEndereco
        this.telefone = telefone
    }

    constructor(id: Int, restaurante: String,nota: Float, descricao: String, endereco: String, numEndereco: String, telefone: String){
        this.id = id
        this.restaurante = restaurante
        this.nota = nota
        this.descricao = descricao
        this.endereco = endereco
        this.numEndereco = numEndereco
        this.telefone = telefone
    }
}