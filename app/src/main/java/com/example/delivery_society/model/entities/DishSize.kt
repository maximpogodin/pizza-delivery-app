package com.example.delivery_society.model.entities

data class DishSize(
    val size_id : Int,
    val dish_id : Int,
    val size : Int,
    val price : Double
) {
    override fun toString(): String {
        return "${size} см - ${price} руб."
    }
}