package com.example.delivery_society.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.delivery_society.R
import com.example.delivery_society.api.ApplicationDbContext
import com.example.delivery_society.model.entities.Cart
import com.example.delivery_society.model.entities.Dish
import com.example.delivery_society.model.entities.OrderItem

class PayFragment constructor() : Fragment() {

    private var db = ApplicationDbContext()
    private lateinit var clientActivity: ClientActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pay_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clientActivity = (activity as ClientActivity)
        clientActivity.setActionBarTitle("Оплата")

        val payOrderButton = view.findViewById<Button>(R.id.pay_order_button)
        val deliveryAddressEdit = view.findViewById<EditText>(R.id.delivery_address_edit)
        payOrderButton.setOnClickListener {
            if (deliveryAddressEdit.text.isEmpty()) {
                Toast.makeText(clientActivity, "Укажите адрес доставки.", Toast.LENGTH_SHORT).show()
            }
            else {
                db.createOrder(deliveryAddressEdit.text.toString())
                Toast.makeText(clientActivity, "Спасибо! Ваш заказ создан и оплачен.", Toast.LENGTH_SHORT).show()
                Cart.orderItems.clear()
                clientActivity.openFragment(OrderHistoryFragment(false))
            }
        }
    }
}