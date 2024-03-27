package com.example.delivery_society.model.entities

data class AccessRight(
    val access_right_id : Int,
    val name : String
)
{
    override fun toString(): String {
        return name;
    }
}