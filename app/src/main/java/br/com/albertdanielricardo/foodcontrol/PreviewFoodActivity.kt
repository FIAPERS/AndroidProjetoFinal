package br.com.albertdanielricardo.foodcontrol

import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import br.com.albertdanielricardo.foodcontrol.dao.BancoDeDados
import br.com.albertdanielricardo.foodcontrol.model.Food
import android.os.AsyncTask
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.RatingBar
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import kotlinx.android.synthetic.main.activity_preview_food.*
import kotlinx.android.synthetic.main.activity_food.*

class PreviewFoodActivity : AppCompatActivity(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks{
    private lateinit var txtRestaurante: TextView
    private lateinit var rbNotaPreview: RatingBar
    private lateinit var txtDescricao: TextView
    private lateinit var txtEndereco: TextView
    private lateinit var txtNumEdereco: TextView
    private lateinit var txtTelefone: TextView
    private lateinit var imgFotoPreview: ImageView
    private lateinit var btnEditar: Button
    private lateinit var btnDeletar: Button
    private lateinit var btnLigar: Button
    private lateinit var mMap: GoogleMap

    private var db: BancoDeDados? = null

    companion object {
        const val PERMISSION_STORAGE = 1
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val geocoder = Geocoder(applicationContext, Locale.getDefault())
        val endereco = intent.getStringExtra("endereco") + intent.getStringExtra("numero")
        val addressGeocoding = geocoder.getFromLocationName(endereco, 1)

        val localRestaurante = LatLng(addressGeocoding[0].latitude, addressGeocoding[0].longitude)
        val tituloMarker = intent.getStringExtra("restaurante")
        mMap.addMarker(MarkerOptions().position(localRestaurante).title(tituloMarker))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localRestaurante, 16f))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_food)

        supportActionBar!!.title = getString(R.string.backbutton)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        txtRestaurante = findViewById(R.id.txtRestaurante)
        rbNotaPreview = findViewById(R.id.rbNotaPreview)
        txtDescricao = findViewById(R.id.txtDescricao)
        txtEndereco = findViewById(R.id.txtEndereco)
        txtNumEdereco = findViewById(R.id.txtNumEndereco)
        txtTelefone = findViewById(R.id.txtTelefone)
        btnDeletar = findViewById(R.id.btnDeletar)
        btnEditar = findViewById(R.id.btnEditar)
        btnLigar = findViewById(R.id.btnLigar)

        val intentRestaurante = intent.getStringExtra("restaurante")
        val intentRbNota = intent.getFloatExtra("rbNota",0.0F)

        //Desabilitando o touch da RattingBar
        rbNotaPreview.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                return true
            }
        })

        val intentDescricao = intent.getStringExtra("descricao")
        val intentEndereco = intent.getStringExtra("endereco")
        val intentNumEndereco = intent.getStringExtra("numendereco")
        val intentTelefone = intent.getStringExtra("telefone")
        val intentId = intent.getIntExtra("id",0)

        txtRestaurante?.setText(intentRestaurante.toString())
        rbNotaPreview?.setRating(intentRbNota)
        txtEndereco?.setText(intentEndereco.toString())
        txtNumEdereco?.setText(intentNumEndereco.toString())
        txtDescricao?.setText(intentDescricao.toString())
        txtTelefone?.setText(intentTelefone.toString())

        //verificando se há um mapa instalado
        val latitudeLongitude = "-23.5565804,-46.662113"
        val geo = "geo:$latitudeLongitude?"
        val geoUri = Uri.parse( geo )
        val intent = Intent( Intent.ACTION_VIEW, geoUri )
        intent.setPackage( "com.google.android.apps.maps" )
        if( intent.resolveActivity( packageManager ) != null ){
            //startActivity( intent )
        }
        else{
            Toast.makeText(this, "Nenhum mapa instalado", Toast.LENGTH_LONG).show()
        }

        //Validando endereço para não buscar latitude e longitude com endereço vazio
        if ((intentEndereco != null && intentEndereco != "") && (intentNumEndereco != null && intentNumEndereco != "")){
            val mapFragment = supportFragmentManager.findFragmentById(R.id.mvMapa) as SupportMapFragment?
            mapFragment?.getMapAsync(this)
        }

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
            if (food.restaurante !="")
                DeleteAsyncTask(db!!).execute(food)
            finish()
        }

        btnLigar.setOnClickListener {
            val intentCall = Intent(Intent.ACTION_DIAL)
            intentCall.data = Uri.parse("tel: "+intentTelefone)
            startActivity(intentCall)

            /*intentCall.action = Intent(Intent.ACTION_CALL, Uri.parse("tel:2222222222")
                    startActivity(intentCall)*/
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        sharedFood()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this,"Teste compartilhamento", Toast.LENGTH_SHORT).show()
        sharedFood()
    }

    fun sharedFood(){
        val body : String = String.format(
            "%s %s\n\n%s \n\n%s \n\n%s \n\n%s \n\n%s\" \n\n%s",
            getString(R.string.initial_shared_body),
            getString(R.string.restaurant)+": "+txtRestaurante.text,
            getString(R.string.description)+": "+txtDescricao.text,
            getString(R.string.ratting)+": "+rbNotaPreview.getRating().toString(),
            getString(R.string.address)+": "+txtEndereco.text,
            getString(R.string.num_address)+": "+txtNumEdereco.text,
            getString(R.string.phone)+": "+txtTelefone.text,
            getString(R.string.about_shared)
        )
        val intent = Intent()
        intent.action = Intent.ACTION_SEND

        intent.type = "text/plain"

        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.extra_subject)+txtRestaurante.text)
        intent.putExtra(Intent.EXTRA_TEXT,body)

        if (intent.resolveActivity(packageManager) != null){
            val intentChoser = Intent.createChooser(intent,getString(R.string.chosser_title))
            startActivity(intentChoser)
        }

    }

    fun sharedFoodPermission(view: View){
        EasyPermissions.requestPermissions(
            PermissionRequest.Builder(this,
                PERMISSION_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).setRationale(R.string.permission_inform)
                .build()
        )
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
