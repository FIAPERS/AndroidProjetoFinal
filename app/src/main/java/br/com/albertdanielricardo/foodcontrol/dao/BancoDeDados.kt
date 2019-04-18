package br.com.albertdanielricardo.foodcontrol.dao
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import br.com.albertdanielricardo.foodcontrol.dao.FoodDAO
import br.com.albertdanielricardo.foodcontrol.model.Food

@Database(entities = arrayOf(Food::class), version = 1)
abstract class BancoDeDados : RoomDatabase(){

    abstract fun foodDAO(): FoodDAO

    companion object {
        var INSTANCE: BancoDeDados? = null

        fun getDatabase(context: Context): BancoDeDados?{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    BancoDeDados::class.java,
                    "foodsdbs")
                    .build()
            }
            return INSTANCE
        }
    }
}