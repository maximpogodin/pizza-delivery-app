package com.example.delivery_society.model.entities

data class OrderDTO(
    val order_id: Int,
    val creation_datetime: String,
    val delivery_datetime: String,
    val delivery_address: String,
    val status_name: String,
    var order_items: ArrayList<OrderItemDTO> = ArrayList()
)