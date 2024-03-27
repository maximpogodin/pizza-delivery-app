package com.example.delivery_society.api

import com.example.delivery_society.model.entities.Cart
import com.example.delivery_society.model.entities.CurrentUser
import com.example.delivery_society.model.entities.Dish
import com.example.delivery_society.model.entities.DishSize
import com.example.delivery_society.model.entities.LoyaltyLevel
import com.example.delivery_society.model.entities.OrderDTO
import com.example.delivery_society.model.entities.OrderItemDTO
import com.example.delivery_society.model.entities.Status
import com.example.delivery_society.model.entities.User
import com.example.delivery_society.model.tables.DishSizes
import com.example.delivery_society.model.tables.Dishes
import com.example.delivery_society.model.tables.LoyaltyLevels
import com.example.delivery_society.model.tables.OrderItems
import com.example.delivery_society.model.tables.Orders
import com.example.delivery_society.model.tables.Statuses
import com.example.delivery_society.model.tables.Users
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.gte
import org.ktorm.dsl.innerJoin
import org.ktorm.dsl.insert
import org.ktorm.dsl.lt
import org.ktorm.dsl.max
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ApplicationDbContext {
    private var api = Api()

    fun getDishes() : ArrayList<Dish> {
        val db = api.getConnection()
        val resultList = ArrayList<Dish>()

        for (row in db.from(Dishes).select()) {
            val item = Dish(
                row[Dishes.dish_id]!!.toInt(),
                row[Dishes.name].toString(),
                row[Dishes.description].toString(),
                row[Dishes.available]!!,
                row[Dishes.photo].toString()
            )
            resultList.add(item)
        }

        return resultList
    }

    fun getDish(dish_id : Int) : Dish? {
        val db = api.getConnection()

        for (row in db.from(Dishes).select().where(Dishes.dish_id eq dish_id)) {
            return Dish(
                row[Dishes.dish_id]!!.toInt(),
                row[Dishes.name].toString(),
                row[Dishes.description].toString(),
                row[Dishes.available]!!,
                row[Dishes.photo].toString()
            )
        }

        return null
    }

    fun getSize(sizeId : Int) : DishSize? {
        val db = api.getConnection()

        for (row in db.from(DishSizes).select().where(DishSizes.size_id eq sizeId)) {
            return DishSize(
                row[DishSizes.size_id]!!.toInt(),
                row[DishSizes.dish_id]!!.toInt(),
                row[DishSizes.size]!!.toInt(),
                row[DishSizes.price]!!.toDouble()
            )
        }

        return null
    }

    fun getDishSizes(dishId : Int) : ArrayList<DishSize> {
        val db = api.getConnection()
        val resultList = ArrayList<DishSize>()

        for (row in db.from(DishSizes).select().where(DishSizes.dish_id eq dishId)) {
            val item = DishSize(
                row[DishSizes.size_id]!!.toInt(),
                row[DishSizes.dish_id]!!.toInt(),
                row[DishSizes.size]!!.toInt(),
                row[DishSizes.price]!!.toDouble()
            )
            resultList.add(item)
        }

        return resultList
    }

    fun getStatuses() : ArrayList<Status> {
        val db = api.getConnection()
        val resultList = ArrayList<Status>()

        for (row in db.from(Statuses).select()) {
            val item = Status(
                row[Statuses.status_id]!!.toInt(),
                row[Statuses.name].toString()
            )
            resultList.add(item)
        }

        return resultList
    }

    fun getStatus(name : String) : Status? {
        val db = api.getConnection()

        for (row in db.from(Statuses).select().where(Statuses.name eq name)) {
            return Status(
                row[Statuses.status_id]!!.toInt(),
                row[Statuses.name].toString()
            )
        }

        return null
    }

    fun getOrderHistory(user_id : Int) : ArrayList<OrderDTO> {
        val db = api.getConnection()
        val resultList = ArrayList<OrderDTO>()

        for (row in db.from(Orders)
            .innerJoin(Statuses, Statuses.status_id eq Orders.status_id)
            .select(Orders.order_id, Orders.creation_datetime, Orders.delivery_datetime,
                Orders.delivery_address, Statuses.name)
            .where(Orders.user_id eq user_id)
        ) {
            val item = OrderDTO(
                row[Orders.order_id]!!.toInt(),
                row[Orders.creation_datetime].toString(),
                row[Orders.delivery_datetime].toString(),
                row[Orders.delivery_address].toString(),
                row[Statuses.name].toString()
            )

            val orderItems = ArrayList<OrderItemDTO>()

            for (row2 in db.from(OrderItems)
                .innerJoin(DishSizes, OrderItems.size_id eq DishSizes.size_id)
                .innerJoin(Dishes, DishSizes.dish_id eq Dishes.dish_id)
                .select(Dishes.name, DishSizes.size, DishSizes.price, OrderItems.quantity)
                .where(OrderItems.order_id eq item.order_id)) {
                val item2 = OrderItemDTO (
                    row2[Dishes.name].toString(),
                    row2[DishSizes.price]!!.toDouble(),
                    row2[DishSizes.size]!!.toInt(),
                    row2[OrderItems.quantity]!!.toInt()
                )

                orderItems.add(item2)
            }

            item.order_items = orderItems
            resultList.add(item)
        }

        return resultList
    }

    fun getAllOrders() : ArrayList<OrderDTO> {
        val db = api.getConnection()
        val resultList = ArrayList<OrderDTO>()

        for (row in db.from(Orders)
            .innerJoin(Statuses, Statuses.status_id eq Orders.status_id)
            .select(Orders.order_id, Orders.creation_datetime, Orders.delivery_datetime,
                Orders.delivery_address, Statuses.name)
        ) {
            val item = OrderDTO(
                row[Orders.order_id]!!.toInt(),
                row[Orders.creation_datetime].toString(),
                row[Orders.delivery_datetime].toString(),
                row[Orders.delivery_address].toString(),
                row[Statuses.name].toString()
            )

            val orderItems = ArrayList<OrderItemDTO>()

            for (row2 in db.from(OrderItems)
                .innerJoin(DishSizes, OrderItems.size_id eq DishSizes.size_id)
                .innerJoin(Dishes, DishSizes.dish_id eq Dishes.dish_id)
                .select(Dishes.name, DishSizes.size, DishSizes.price, OrderItems.quantity)
                .where(OrderItems.order_id eq item.order_id)) {
                val item2 = OrderItemDTO (
                    row2[Dishes.name].toString(),
                    row2[DishSizes.price]!!.toDouble(),
                    row2[DishSizes.size]!!.toInt(),
                    row2[OrderItems.quantity]!!.toInt()
                )

                orderItems.add(item2)
            }

            item.order_items = orderItems
            resultList.add(item)
        }

        return resultList
    }

    fun getOrder(orderId : Int) : OrderDTO? {
        val db = api.getConnection()

        for (row in db.from(Orders)
            .innerJoin(Statuses, Statuses.status_id eq Orders.status_id)
            .select(Orders.order_id, Orders.creation_datetime, Orders.delivery_datetime,
                Orders.delivery_address, Statuses.name)
            .where(Orders.order_id eq orderId)
        ) {
            val item = OrderDTO(
                row[Orders.order_id]!!.toInt(),
                row[Orders.creation_datetime].toString(),
                row[Orders.delivery_datetime].toString(),
                row[Orders.delivery_address].toString(),
                row[Statuses.name].toString()
            )

            val orderItems = ArrayList<OrderItemDTO>()

            for (row2 in db.from(OrderItems)
                .innerJoin(DishSizes, OrderItems.size_id eq DishSizes.size_id)
                .innerJoin(Dishes, DishSizes.dish_id eq Dishes.dish_id)
                .select(Dishes.name, DishSizes.size, DishSizes.price, OrderItems.quantity)
                .where(OrderItems.order_id eq item.order_id)) {
                val item2 = OrderItemDTO (
                    row2[Dishes.name].toString(),
                    row2[DishSizes.price]!!.toDouble(),
                    row2[DishSizes.size]!!.toInt(),
                    row2[OrderItems.quantity]!!.toInt()
                )

                orderItems.add(item2)
            }

            item.order_items = orderItems
            return item
        }

        return null
    }

    fun updateOrderStatus(orderId : Int, statusId : Int) {
        val db = api.getConnection()
        db.update(Orders) {
            set(it.status_id, statusId)
            where {
                it.order_id eq orderId
            }
        }
    }

    fun getUser(username : String) : User? {
        val db = api.getConnection()
        val resultList = ArrayList<User>()

        for (row in db.from(Users).select()
            .where { Users.login eq username }) {
            val item = User(
                row[Users.user_id]!!.toInt(),
                row[Users.first_name].toString(),
                row[Users.last_name].toString(),
                row[Users.middle_name].toString(),
                row[Users.login].toString(),
                row[Users.password].toString(),
                row[Users.phone_number].toString(),
                row[Users.loyalty_level_id]!!.toInt(),
                row[Users.access_right_id]!!.toInt()
            )
            resultList.add(item)
        }

        if (resultList.isEmpty())
        {
            return null
        }

        return resultList[0]
    }

    fun getUserLoyaltyLevel(userId : Int) : LoyaltyLevel? {
        val db = api.getConnection()
        var loyaltyLevel = ""
        val currentDate = LocalDate.now()
        var date1 = LocalDateTime.of(currentDate.year, currentDate.month, 1, 0, 0, 0).format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        var date2 = LocalDateTime.of(currentDate.year, currentDate.month + 1, 1, 0, 0).format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        var timeStamp1 = Timestamp.valueOf(date1.toString())
        var timeStamp2 = Timestamp.valueOf(date2.toString())

        val count = db
                .from(Orders)
                .select(Orders.order_id)
                .where {
                    (Orders.user_id eq userId) and
                    (Orders.creation_datetime.gte(timeStamp1)) and
                    (Orders.creation_datetime.lt(timeStamp2))
                }.totalRecordsInAllPages

        if (count < 3) {
            loyaltyLevel = "Стандарт"
        }
        else if (count in 3..9) {
            loyaltyLevel = "Новичок"
        }
        else if (count in 10..19) {
            loyaltyLevel = "Продвинутый"
        }
        else {
            loyaltyLevel = "Эксперт"
        }

        for (row in db.from(LoyaltyLevels).select().where(LoyaltyLevels.name eq loyaltyLevel)) {
            return LoyaltyLevel(
                row[LoyaltyLevels.level_id]!!.toInt(),
                row[LoyaltyLevels.name].toString(),
                row[LoyaltyLevels.discount]!!.toDouble()
            )
        }

        return null
    }

    fun createOrder(deliveryAddress : String) {
        val db = api.getConnection()
        var date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        var timeStamp = Timestamp.valueOf(date.toString())

        db.insert(Orders) {
            set(it.creation_datetime, timeStamp)
            set(it.delivery_datetime, null)
            set(it.user_id, CurrentUser.user!!.user_id)
            set(it.status_id, 1)
            set(it.delivery_address, deliveryAddress)
        }

        val maxOrderId = max(Orders.order_id).aliased("max_order_id")
        for (row in db.from(Orders).select(maxOrderId)
            .where(Orders.user_id eq CurrentUser.user!!.user_id)) {
            val orderId = row[maxOrderId]!!.toInt()
            var orderItems = Cart.orderItems.iterator()
            orderItems.forEach { orderItem ->
                db.insert(OrderItems) {
                    set(it.order_id, orderId)
                    set(it.size_id, orderItem.size_id)
                    set(it.quantity, orderItem.quantity)
                }
            }
        }
    }

    fun createUser(user : User) {
        val db = api.getConnection()

        db.insert(Users) {
            set(it.login, user.login)
            set(it.password, user.password)
            set(it.phone_number, user.password)
            set(it.loyalty_level_id, 1)
            set(it.access_right_id, 2)
            set(it.first_name, user.first_name)
            set(it.last_name, user.last_name)
            set(it.middle_name, user.middle_name)
        }
    }
}