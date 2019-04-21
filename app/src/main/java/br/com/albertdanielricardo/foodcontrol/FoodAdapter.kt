package br.com.albertdanielricardo.foodcontrol
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.albertdanielricardo.foodcontrol.model.Food

import br.com.albertdanielricardo.foodcontrol.R
import kotlinx.android.synthetic.main.card_food.view.*

class FoodAdapter(var foods: List<Food>):
        RecyclerView.Adapter<FoodAdapter.FoodViewHolder>(){
    override fun getItemCount(): Int {
        return foods.size
    }

    fun setList(foods: List<Food>){
        this.foods = foods
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): FoodViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_food,parent,false)
        return FoodViewHolder(v)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, i: Int) {
        val food = foods[i]
        holder.tvProduto.text = food.restaurante
        holder.tvDescricao.text = food.descricao
        holder?.foodRestaurante = food
    }

    class FoodViewHolder(v: View, var foodRestaurante: Food? = null) : RecyclerView.ViewHolder(v){
        var tvProduto: TextView = v.findViewById(R.id.tvProduto)
        var tvDescricao: TextView = v.findViewById(R.id.tvDescricao)

        init {
            v.setOnClickListener {
                val intent = Intent(v.context, PreviewFoodActivity::class.java)
                intent.putExtra("id",foodRestaurante?.id)
                intent.putExtra("restaurante", foodRestaurante?.restaurante)
                intent.putExtra("descricao", foodRestaurante?.descricao)
                intent.putExtra("numendereco",foodRestaurante?.numEndereco)
                intent.putExtra("rbNota", foodRestaurante?.nota)
                intent.putExtra("endereco",foodRestaurante?.endereco)
                intent.putExtra("telefone", foodRestaurante?.telefone)

                v.context.startActivity(intent)
            }
        }
    }
}