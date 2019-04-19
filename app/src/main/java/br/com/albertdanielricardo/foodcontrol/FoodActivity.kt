package br.com.albertdanielricardo.foodcontrol

import android.arch.persistence.room.Database
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import br.com.albertdanielricardo.foodcontrol.dao.BancoDeDados
import br.com.albertdanielricardo.foodcontrol.model.Food
import android.os.AsyncTask
import android.content.Intent

class FoodActivity : AppCompatActivity() {

    private lateinit var edtProduto: EditText
    private lateinit var edtDescricao: EditText
    private lateinit var btnAdicionar: Button
    private var db: BancoDeDados? = null

    private var id: Int?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        edtProduto = findViewById(R.id.edtProduto)
        edtDescricao = findViewById(R.id.edtDescricao)
        btnAdicionar = findViewById(R.id.btnAdicionar)

        if (intent.getStringExtra("produto") != null){
            val intentProduto = intent.getStringExtra("produto")
            val intentDescricao = intent.getStringExtra("descricao")
            id = intent.getIntExtra("id",0)

            edtProduto?.setText(intentProduto)
            edtDescricao?.setText(intentDescricao.toString())
        }

        btnAdicionar.setOnClickListener {

            if(id != null){
                db = BancoDeDados.getDatabase(this)
                val food = Food(intent.getIntExtra("id",0),edtProduto.text.toString(), edtDescricao.text.toString())
                if (food.produto !="")UpdateAsyncTask(db!!).execute(food)
                finish()
            }else{
                db = BancoDeDados.getDatabase(this)
                val food = Food(edtProduto.text.toString(), edtDescricao.text.toString())
                if (food.produto !="")InsertAsyncTask(db!!).execute(food)
                finish()
            }


        }
    }

    private inner class UpdateAsyncTask
    internal constructor(appDatabase: BancoDeDados) : AsyncTask<Food, Void,
            String>(){
        private val db: BancoDeDados = appDatabase
        override fun doInBackground(vararg params: Food): String {
            db.foodDAO().atualizar(params[0])
            return ""
        }
    }

    private inner class DeleteAsyncTask
    internal constructor(appDatabase: BancoDeDados) : AsyncTask<Food, Void,
            String>(){
        private val db: BancoDeDados = appDatabase
        override fun doInBackground(vararg params: Food): String {
            db.foodDAO().apagar(params[0])
            return ""
        }
    }


    private inner class InsertAsyncTask
    internal constructor(appDatabase: BancoDeDados) : AsyncTask<Food, Void,
            String>(){
        private val db: BancoDeDados = appDatabase
        override fun doInBackground(vararg params: Food): String {
            db.foodDAO().inserir(params[0])
            return ""
        }
    }
}
