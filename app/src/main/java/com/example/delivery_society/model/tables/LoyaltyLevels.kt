package com.example.delivery_society.model.tables

import org.ktorm.schema.Table
import org.ktorm.schema.decimal
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object LoyaltyLevels : Table<Nothing>("LoyaltyLevels") {
    val level_id = int("level_id").primaryKey()
    val name = varchar("name")
    val discount = decimal("discount")
}