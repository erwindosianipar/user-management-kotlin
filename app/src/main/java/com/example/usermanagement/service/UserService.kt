package com.example.usermanagement.service

import com.example.usermanagement.model.*
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("register")
    fun register(@Body userRegister: UserRegister): Call<UserRegisterResponse>

    @POST("login")
    fun login(@Body userLogin: UserLogin): Call<UserLoginResponse>

    @GET("user")
    fun getListUser(): Call<UserList>

    @GET("user/{id}")
    fun getUserById(@Header("Authorization") token: String, @Path("id") id: String): Call<UserResponse>
}