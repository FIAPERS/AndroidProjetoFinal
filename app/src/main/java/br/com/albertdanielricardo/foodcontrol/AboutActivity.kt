package br.com.albertdanielricardo.foodcontrol

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar!!.title = getString(R.string.backbutton)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
