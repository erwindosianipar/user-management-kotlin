package com.example.usermanagement

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.usermanagement.model.UserResponse
import com.example.usermanagement.repository.UserRepository
import com.example.usermanagement.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var preferenceId: String
    lateinit var id: String
    lateinit var token: String
    lateinit var nameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferenceId = getString(R.string.preferences_id)
        nameTextView = findViewById(R.id.textViewNameUser)
        setIdFromSharedPreferences()
        loginValidation()
        fetchUser(UserRepository.CreateUserService())
    }

    private fun loginValidation() {
        if (id == "") {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun setIdFromSharedPreferences() {
        val sharedPreferences = getSharedPreferences(preferenceId, Context.MODE_PRIVATE)
        id = sharedPreferences.getString("ID", "")!!
        token = sharedPreferences.getString("TOKEN", "")!!
    }

    private fun resetSharedPreference() {
        val sharedPref = getSharedPreferences(preferenceId, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("ID", "")
            putString("TOKEN", "")
            commit()
        }
    }

    fun clickLogout(view: View) {
        resetSharedPreference()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun updateUserScreen(view: View) {
        startActivity(Intent(this, UpdateUserActivity::class.java))
    }

    private fun fetchUser(postServiceUser: UserService) {
        postServiceUser.getUserById(
            "Bearer ${token}",
            id
        ).enqueue(
            object : Callback<UserResponse>{
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("error", "error failure -> ${t.message}")
                }

                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.code() == 200) {
                        val resp: UserResponse = response.body()!!
                        println(resp.response.name)
                        nameTextView.setText(resp.response.name)
                    }

                    println("http response code -> " + response.code())
                }
            }
        )
    }
}
