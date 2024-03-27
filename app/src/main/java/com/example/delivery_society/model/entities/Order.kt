package com.example.delivery_society.model.entities

import java.time.LocalDateTime

data class Order(
    val order_id : Int,
    val creation_datetime : String,
    val delivery_datetime : String?,
    var delivery_address : String,
    var user_id : Int,
    var status_id : Int
)