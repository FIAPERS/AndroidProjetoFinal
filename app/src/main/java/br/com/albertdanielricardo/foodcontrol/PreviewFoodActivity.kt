package br.com.albertdanielricardo.foodcontrol

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import br.com.albertdanielricardo.foodcontrol.dao.BancoDeDados
import br.com.albertdanielricardo.foodcontrol.model.Food
import android.os.AsyncTask
import kotlinx.android.synthetic.main.activity_preview_food.*

class PreviewFoodActivity : AppCompatActivity() {

    private lateinit var txtRestaurante: TextView
    private lateinit var rbNotaPreview: RatingBar
    private lateinit var txtDescricao: TextView
    private lateinit var txtEndereco: TextView
    private lateinit var txtNumEdereco: TextView
    private lateinit var txtTelefone: TextView
    private lateinit var imgFotoPreview: ImageView
    private lateinit var btnEditar: Button
    private lateinit var btnDeletar: Button

    private var db: BancoDeDados? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_food)

        txtRestaurante = findViewById(R.id.txtRestaurante)
        rbNotaPreview = findViewById(R.id.rbNotaPreview)
        txtDescricao = findViewById(R.id.txtDescricao)
        txtEndereco = findViewById(R.id.txtEndereco)
        txtNumEdereco = findViewById(R.id.txtNumEndereco)
        txtTelefone = findViewById(R.id.txtTelefone)
        btnDeletar = findViewById(R.id.btnDeletar)
        btnEditar = findViewById(R.id.btnEditar)

        val intentRestaurante = intent.getStringExtra("restaurante")
        val intentRbNota = intent.getFloatExtra("rbNota",0.0F)
        val intentDescricao = intent.getStringExtra("descricao")
        val intentEndereco = intent.getStringExtra("endereco")
        val intentNumEndereco = intent.getStringExtra("numendereco")
        val intentTelefone = intent.getStringExtra("telefone")
        val intentId = intent.getIntExtra("id",0)
        //val intentDescricao = intent.getStringExtra("descricao")
        //id = intent.getIntExtra("id",0)

        txtRestaurante?.setText(intentRestaurante.toString())
        rbNotaPreview?.setRating(intentRbNota)
        txtEndereco?.setText(intentEndereco.toString())
        txtNumEdereco?.setText(intentNumEndereco.toString())
        txtDescricao?.setText(intentDescricao.toString())
        txtTelefone?.setText(intentTelefone.toString())


        btnEditar.setOnClickListener {

            val intentEdit = Intent(this, FoodActivity::class.java)
            intentEdit.putExtra("id",intentId)
            intentEdit.putExtra("restaurante", intentRestaurante)
            intentEdit.putExtra("descricao", intentDescricao)
            intentEdit.putExtra("numendereco",intentNumEndereco)
            intentEdit.putExtra("rbNota",intentRbNota)
            intentEdit.putExtra("endereco",intentEndereco)
            intentEdit.putExtra("telefone", intentTelefone)

            startActivity(intentEdit)
            finish()
        }

        btnDeletar.setOnClickListener {
            db = BancoDeDados.getDatabase(this)
            val food = Food(intent.getIntExtra("id",0),intentRestaurante,intentRbNota, intentDescricao
                ,intentEndereco,intentNumEndereco,intentTelefone)
            if (food.restaurante !="")DeleteAsyncTask(db!!).execute(food)
            finish()
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
}
