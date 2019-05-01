package br.com.albertdanielricardo.foodcontrol



import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*



class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private val newUserRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null) {
            goToHome()
        }

        btLogin.setOnClickListener {


            if (verifyFields())
            {
                mAuth.signInWithEmailAndPassword(
                    inputLoginEmail.text.toString(),
                    inputLoginPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        goToHome()
                    } else {
                        Toast.makeText(this@LoginActivity,getString(R.string.toast_error_login), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        btSignup.setOnClickListener {
            startActivityForResult(Intent(this, SignUpActivity::class.java), newUserRequestCode)
        }
    }

    private fun verifyFields() : Boolean{
        if (inputLoginEmail.text.toString() == "" ){
            Toast.makeText(this@LoginActivity,getString(R.string.toast_email_login), Toast.LENGTH_SHORT).show()
            return false
        }

        if (inputLoginPassword.text.toString() == "" ){
            Toast.makeText(this@LoginActivity,getString(R.string.toast_password_login), Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newUserRequestCode && resultCode == Activity.RESULT_OK) {
            inputLoginEmail.setText(data?.getStringExtra("email"))
        }
    }
}
