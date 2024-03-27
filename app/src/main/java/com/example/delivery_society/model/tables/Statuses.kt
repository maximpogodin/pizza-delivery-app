package com.example.delivery_society.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Statuses : Table<Nothing>("Statuses") {
    val status_id = int("status_id").primaryKey()
    val name = varchar("name")
}