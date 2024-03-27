package com.example.delivery_society.view

import CartAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery_society.R
import com.example.delivery_society.model.entities.Cart

class CartFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var clientActivity: ClientActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cart_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.base_list_recycler_view)
        clientActivity = (activity as ClientActivity)
        clientActivity.setActionBarTitle("Корзина")

        getCart()

        var clearCartButton: Button = view.findViewById(R.id.clear_cart_button) as Button
        var createOrderButton: Button = view.findViewById(R.id.create_order_button) as Button

        clearCartButton.setOnClickListener {
            Cart.orderItems.clear()
            cartAdapter.notifyDataSetChanged()
        }
        createOrderButton.setOnClickListener {
            if (Cart.orderItems.size == 0) {
                Toast.makeText(clientActivity, "Корзина пуста", Toast.LENGTH_SHORT).show()
            }
            else {
                clientActivity.openFragment(PayFragment())
            }
        }
    }

    private fun getCart() {
        recyclerView.adapter = null
        cartAdapter = CartAdapter()
        recyclerView.adapter = cartAdapter
    }
}