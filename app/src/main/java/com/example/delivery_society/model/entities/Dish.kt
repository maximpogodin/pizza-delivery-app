package com.example.delivery_society.model.entities

data class Dish(
    val dish_id : Int,
    val name : String,
    val description : String,
    val available : Boolean,
    val photo : String
)