package com.example.delivery_society.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object AccessRights : Table<Nothing>("AccessRights") {
    val access_right_id = int("access_right_id").primaryKey()
    val name = varchar("name")
}