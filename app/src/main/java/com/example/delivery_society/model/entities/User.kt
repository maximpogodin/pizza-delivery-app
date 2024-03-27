package com.example.delivery_society.model.entities

data class User (
    val user_id : Int,
    val first_name : String,
    val last_name : String,
    val middle_name : String,
    val login : String,
    val password : String,
    val phone_number : String,
    val loyalty_level_id : Int,
    val access_right_id : Int
)