package com.example.delivery_society.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Users : Table<Nothing>("Users") {
    val user_id = int("user_id").primaryKey()
    val first_name = varchar("first_name")
    val last_name = varchar("last_name")
    val middle_name = varchar("middle_name")
    val login = varchar("login")
    val password = varchar("password")
    val phone_number = varchar("phone_number")
    val loyalty_level_id = int("loyalty_level_id")
    val access_right_id = int("access_right_id")
}