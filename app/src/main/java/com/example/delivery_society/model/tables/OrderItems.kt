package com.example.delivery_society.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int

object OrderItems : Table<Nothing>("OrderItems") {
    val order_id = int("order_id").primaryKey()
    val size_id = int("size_id")
    val quantity = int("quantity")
}