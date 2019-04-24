package br.com.albertdanielricardo.foodcontrol
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import br.com.albertdanielricardo.foodcontrol.dao.BancoDeDados
import br.com.albertdanielricardo.foodcontrol.model.Food
import android.os.AsyncTask
import android.widget.ImageView
import android.widget.RatingBar
import android.arch.persistence.room.Database
import android.content.Intent


class FoodActivity : AppCompatActivity() {

    private lateinit var edtRestaurante: EditText
    private lateinit var rbNota: RatingBar
    private lateinit var edtDescricao: EditText
    private lateinit var edtEndereco: EditText
    private lateinit var edtNumEdereco: EditText
    private lateinit var edtTelefone: EditText
    private lateinit var imgFoto: ImageView
    private lateinit var btnSalvar: Button

    private var db: BancoDeDados? = null
    private var id: Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        supportActionBar!!.title = getString(R.string.backbutton)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        edtRestaurante = findViewById(R.id.edtRestaurante)
        rbNota = findViewById(R.id.rbNota)
        edtDescricao = findViewById(R.id.edtDescricao)
        edtEndereco = findViewById(R.id.edtEndereco)
        edtNumEdereco = findViewById(R.id.edtNumEnd)
        edtTelefone = findViewById(R.id.edtTelefone)
        imgFoto = findViewById(R.id.imgFoto)
        btnSalvar = findViewById(R.id.btnSalvar)

        if (intent.getStringExtra("restaurante") != null){
            val intentRestaurante = intent.getStringExtra("restaurante")
            val intentEndereco = intent.getStringExtra("endereco")
            val intentDescricao = intent.getStringExtra("descricao")
            val intentNumEndereco = intent.getStringExtra("numendereco")
            val intetNota = intent.getFloatExtra("rbNota",0.0F)
            val intentTelefone = intent.getStringExtra("telefone")
            id = intent.getIntExtra("id",0)

            edtRestaurante?.setText(intentRestaurante)
            rbNota?.setRating(intetNota)
            edtDescricao?.setText(intentDescricao)
            edtNumEdereco?.setText(intentNumEndereco)
            edtEndereco?.setText(intentEndereco)
            edtTelefone?.setText(intentTelefone)

        }

        btnSalvar.setOnClickListener {

            //Editar registro
            if(id != null){
                db = BancoDeDados.getDatabase(this)
                val food = Food(intent.getIntExtra("id",0),edtRestaurante.text.toString(),rbNota.getRating(), edtDescricao.text.toString()
                    ,edtEndereco.text.toString(),edtNumEdereco.text.toString(),edtTelefone.text.toString())
                if (food.restaurante !="")UpdateAsyncTask(db!!).execute(food)
                finish()
            }else{//Salvar novo registro
                db = BancoDeDados.getDatabase(this)
               val food = Food(edtRestaurante.text.toString(),rbNota.getRating(), edtDescricao.text.toString()
                   ,edtEndereco.text.toString(),edtNumEdereco.text.toString(),edtTelefone.text.toString())
                if (food.restaurante !="")InsertAsyncTask(db!!).execute(food)
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
