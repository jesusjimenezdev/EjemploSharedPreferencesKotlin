package jesus.com.ejemplosharedpreferenceskotlin

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var preferencias: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Leer preferencias
        preferencias = PreferenceManager.getDefaultSharedPreferences(this)

        ponerPreferenciasSiExisten()

        btnLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if(logeo(email, password)) {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                guardarPreferencias(email, password)
                startActivity(intent)
            }
        }
    }

    fun validarEmail(email: String) : Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validarPassword(password: String) : Boolean {
        return password.length >= 5
    }

    fun logeo(email: String, password: String) : Boolean {
        if(!validarEmail(email)) {
            Toast.makeText(this, "Email no valido", Toast.LENGTH_SHORT).show()
            return false
        } else if(!validarPassword(password)) {
            Toast.makeText(this, "Password no valido", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }

    fun guardarPreferencias(email: String, password: String) {
        if(switchRecordar.isChecked) {
            preferencias!!.edit()
                    .putString("email", email)
                    .putString("password", password)
                    .apply()
        }
    }

    fun ponerPreferenciasSiExisten() {
        val email = preferencias!!.getString("email", "")
        val password = preferencias!!.getString("password", "")
        if(email != null && password != null) {
            editTextEmail.setText(email)
            editTextPassword.setText(password)
            switchRecordar.isChecked = true
        }
    }
}
