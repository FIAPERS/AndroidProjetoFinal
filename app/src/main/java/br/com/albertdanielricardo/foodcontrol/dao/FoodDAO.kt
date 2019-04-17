package br.com.albertdanielricardo.foodcontrol.dao
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

import br.com.albertdanielricardo.foodcontrol.model.Food

@Dao
interface FoodDAO {

    @Insert
    fun inserir(food: Food)

    @Query("SELECT * FROM Food")
    fun lerFood(): LiveData<List<Food>>

    @Query("SELECT * FROM Food WHERE id=:id")
    fun buscarPor(id: Int):Food

    @Update
    fun atualizar(food: Food)

    @Delete
    fun apagar (food: Food)

}