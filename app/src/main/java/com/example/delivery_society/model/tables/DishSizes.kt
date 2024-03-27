package com.example.delivery_society.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.decimal
import org.ktorm.schema.int

object DishSizes : Table<Nothing>("DishSizes") {
    val size_id = int("size_id").primaryKey()
    val dish_id = int("dish_id")
    val size = int("size")
    val price = decimal("price")
}