package com.example.usermanagement.model

class UserRegister (
    var name: String,
    var email: String,
    var password: String
){}

class UserRegisterResponse(
    var message: String,
    var response : User,
    var status: Boolean
){}