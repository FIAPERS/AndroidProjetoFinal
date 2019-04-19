package br.com.albertdanielricardo.foodcontrol

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import br.com.albertdanielricardo.foodcontrol.dao.BancoDeDados
import br.com.albertdanielricardo.foodcontrol.model.Food

class ListFoodViewModel(application: Application):AndroidViewModel(application){
    lateinit var foods:LiveData<List<Food>>
    private val bd:BancoDeDados = BancoDeDados.getDatabase(application.applicationContext)!!

    init {
        carregarDados()
    }

    private fun carregarDados(){
        //Carregar os dados da nossa Base de dados e armazenar no LiveData
        foods = bd.foodDAO().lerFood()
    }
}