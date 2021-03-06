package br.com.albertdanielricardo.foodcontrol

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.albertdanielricardo.foodcontrol.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.content.Intent

class SignUpActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()

        btCreate.setOnClickListener {

            if (verifyFields()) {

                mAuth.createUserWithEmailAndPassword(
                    inputEmail.text.toString(),
                    inputPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        saveInRealTimeDatabase()
                    } else {
                        Toast.makeText(this@SignUpActivity, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun verifyFields() : Boolean{
        if (inputName.text.toString() == "" ){
            Toast.makeText(this@SignUpActivity,getString(R.string.toast_name_sign_up), Toast.LENGTH_SHORT).show()
            return false
        }

        if (inputEmail.text.toString() == "" ){
            Toast.makeText(this@SignUpActivity,getString(R.string.toast_email_login), Toast.LENGTH_SHORT).show()
            return false
        }

        if (inputPassword.text.toString() == "" ){
            Toast.makeText(this@SignUpActivity,getString(R.string.toast_password_login), Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun saveInRealTimeDatabase() {
        val user = User(inputName.text.toString(), inputEmail.text.toString())
        FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this,  R.string.toast_success_create_user, Toast.LENGTH_SHORT).show()
                    val returnIntent = Intent(this, LoginActivity::class.java)
                    returnIntent.putExtra("email", inputEmail.text.toString())
                    setResult(RESULT_OK, returnIntent)
                    finish()
                    finish()
                } else {
                    Toast.makeText(this, R.string.toast_error_create_user, Toast.LENGTH_SHORT).show()
                }
            }
    }
}
