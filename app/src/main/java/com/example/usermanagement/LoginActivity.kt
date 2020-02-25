package com.example.usermanagement

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.usermanagement.model.UserLogin
import com.example.usermanagement.model.UserLoginResponse
import com.example.usermanagement.repository.UserRepository
import com.example.usermanagement.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity: AppCompatActivity() {

    lateinit var preferenceId: String
    lateinit var email: EditText
    lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        preferenceId = getString(R.string.preferences_id)

        email = findViewById(R.id.editTextEmailLogin)
        password = findViewById(R.id.editTextPassLogin)
    }

    fun switchToRegisterScreen(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    private fun createServiceUser(postServiceUser: UserService) {
        postServiceUser.login(
            UserLogin(
                email.text.toString(),
                password.text.toString()
            )
        ).enqueue(object : Callback<UserLoginResponse> {
            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                Log.e("error", "error failure -> ${t.message}")
            }

            override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                if (response.code() == 200) {
                    val resp: UserLoginResponse = response.body()!!
                    saveToSharedPreference(resp.response.id, resp.response.token)

                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }

                println("error body -> " + response.code())
            }
        })
    }

    fun clickLogin(view: View) {
        val postServiceUser = UserRepository.CreateUserService()
        createServiceUser(postServiceUser)
    }

    @UseExperimental(ExperimentalUnsignedTypes::class)
    fun saveToSharedPreference(id: UInt, token: String) {
        val sharedPref = getSharedPreferences(preferenceId, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("ID", id.toString())
            putString("TOKEN", token)
            commit()
        }
    }
}