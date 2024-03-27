package com.example.delivery_society.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.jdbcTimestamp
import org.ktorm.schema.varchar

object Orders : Table<Nothing>("Orders") {
    val order_id = int("order_id").primaryKey()
    val creation_datetime = jdbcTimestamp("creation_datetime")
    val delivery_datetime = jdbcTimestamp("delivery_datetime")
    var delivery_address = varchar("delivery_address")
    var user_id = int("user_id")
    var status_id = int("status_id")
}