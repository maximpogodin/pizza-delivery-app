package com.example.delivery_society.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.blob
import org.ktorm.schema.boolean
import org.ktorm.schema.bytes
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Dishes : Table<Nothing>("Dishes") {
    val dish_id = int("dish_id").primaryKey()
    val name = varchar("name")
    val description = varchar("description")
    val available = boolean("available")
    val photo = varchar("photo")
}