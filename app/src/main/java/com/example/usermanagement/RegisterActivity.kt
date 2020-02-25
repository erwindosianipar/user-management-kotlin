package com.example.usermanagement

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.usermanagement.model.UserRegister
import com.example.usermanagement.model.UserRegisterResponse
import com.example.usermanagement.repository.UserRepository
import com.example.usermanagement.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    // var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        name = findViewById(R.id.editTextNameReg)
        email = findViewById(R.id.editTextEmailReg)
        password = findViewById(R.id.editTextPassReg)
    }

    fun switchToLoginScreen(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun createServiceUser(postServiceUser: UserService) {
        postServiceUser.register(
            UserRegister(
                name.text.toString(),
                email.text.toString(),
                password.text.toString()
            )
        ).enqueue(object : Callback<UserRegisterResponse> {
            override fun onFailure(call: Call<UserRegisterResponse>, t: Throwable) {
                Log.e("error", "error failure -> ${t.message}")
            }

            override fun onResponse(call: Call<UserRegisterResponse>, response: Response<UserRegisterResponse>) {
                if (response.code() == 201) {
                    Toast.makeText(this@RegisterActivity, "Register success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Register failed", Toast.LENGTH_SHORT).show()
                }

                println("http response code -> " + response.code())
            }
        })
    }

    fun clickRegister(view: View) {
        val postServiceUser = UserRepository.CreateUserService()

        createServiceUser(postServiceUser)
    }
}