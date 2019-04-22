package br.com.albertdanielricardo.foodcontrol

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_list_food.*
import br.com.albertdanielricardo.foodcontrol.FoodAdapter

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import br.com.albertdanielricardo.foodcontrol.model.Food
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.content_list_food.*

class ListFoodActivity : AppCompatActivity() {

    private var adapter: FoodAdapter? = null
    private var foods: List<Food> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_food)

        flbAdicionar.setOnClickListener {
            val intent = Intent(this,FoodActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        mostrarDados()
        rvFoods.layoutManager = LinearLayoutManager (this)
        adapter = FoodAdapter(foods!!)
        rvFoods.adapter = adapter


    }


    private fun mostrarDados(){
        ViewModelProviders.of(this)
            .get(ListFoodViewModel::class.java)
            .foods
            .observe(this, Observer<List<Food>> { foods ->
                adapter?.setList(foods!!)
                rvFoods.adapter!!.notifyDataSetChanged()
            })
    }
}
