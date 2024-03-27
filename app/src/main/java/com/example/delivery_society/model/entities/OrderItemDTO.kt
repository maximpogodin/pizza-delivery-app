package com.example.delivery_society.model.entities

data class OrderItemDTO (
    val dish_name : String,
    val dish_price : Double,
    val dish_size : Int,
    val quantity : Int
)