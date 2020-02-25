package com.example.usermanagement.model

import com.google.gson.annotations.SerializedName

class User (
    var id: UInt,
    var created_at: String,
    var updated_at: String,
    var deleted_at: String,
    var email: String,
    var password: String,
    var name: String,
    var age: String,
    var address: String
){}

class UserList(
    var message: String,
    var response: List<User>,
    var status: Boolean
){}

class UserResponse(
    var message: String,
    var response: User,
    var status: Boolean
){}