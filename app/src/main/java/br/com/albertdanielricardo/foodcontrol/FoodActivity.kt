package br.com.albertdanielricardo.foodcontrol

import android.arch.persistence.room.Database
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import br.com.albertdanielricardo.foodcontrol.dao.BancoDeDados
import br.com.albertdanielricardo.foodcontrol.model.Food
import android.os.AsyncTask

class FoodActivity : AppCompatActivity() {

    private lateinit var edtProduto: EditText
    private lateinit var edtDescricao: EditText
    private lateinit var btnAdicionar: Button
    private var db: BancoDeDados? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        edtProduto = findViewById(R.id.edtProduto)
        edtDescricao = findViewById(R.id.edtDescricao)
        btnAdicionar = findViewById(R.id.btnAdicionar)

        btnAdicionar.setOnClickListener {
            db = BancoDeDados.getDatabase(this)
            val food = Food(edtProduto.text.toString(), edtDescricao.text.toString())

            if (food.produto !="")InsertAsyncTask(db!!).execute(food)


            /*BaseDados db = new BaseDados.getDatabase(this);
            db.tarefaDao().criarTarefa(tarefa);*/

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
