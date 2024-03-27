package com.example.delivery_society.model.entities
data class OrderItem(
    val order_id : Int,
    val size_id : Int,
    var quantity : Int
)