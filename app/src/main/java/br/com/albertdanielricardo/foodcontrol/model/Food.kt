package br.com.albertdanielricardo.foodcontrol.model
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Food {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var produto: String? = null
    var descricao: String? = null

    constructor(){}

    constructor(produto: String, descricao: String){
        this.produto = produto
        this.descricao = descricao
    }

    constructor(id: Int, produto: String, descricao: String){
        this.id = id
        this.produto = produto
        this.descricao = descricao
    }
}