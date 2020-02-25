package com.example.usermanagement.repository

import com.example.usermanagement.service.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRepository {
    val BASE_URL = "http://10.0.2.2:9002/"

    fun CreateUserService(): UserService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(UserService::class.java)
    }
}