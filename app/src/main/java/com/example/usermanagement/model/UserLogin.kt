package com.example.usermanagement.model

class UserLogin (
    var email: String,
    var password: String
){}

class Token(
    var id: UInt,
    var token: String
){}

class UserLoginResponse (
    var message: String,
    var response: Token,
    var status: Boolean
){}