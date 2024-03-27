package com.example.delivery_society.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.delivery_society.R
import com.example.delivery_society.api.ApplicationDbContext
import com.example.delivery_society.model.entities.Cart
import com.example.delivery_society.model.entities.Dish
import com.example.delivery_society.model.entities.DishSize
import com.example.delivery_society.model.entities.OrderItem

class DishFragment constructor(dishId : Int) : Fragment() {

    private var db = ApplicationDbContext()
    private val _dishId: Int = dishId
    private lateinit var clientActivity: ClientActivity
    private var selectedSize : DishSize? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dish_fragment, container, false)
    }

    fun stringToImageResource(context: Context, imageName: String): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clientActivity = (activity as ClientActivity)
        clientActivity.setActionBarTitle("Блюдо")
        val imageView = view.findViewById<ImageView>(R.id.image)
        val dish = db.getDish(_dishId)
        val imageResource = stringToImageResource(clientActivity, dish!!.photo)
        imageView.setImageResource(imageResource)
        val layoutParams = imageView.layoutParams
        layoutParams.height = 700
        layoutParams.width = 700

        getDish()

        val sizes = db.getDishSizes(_dishId)

        val sizesSpinner = view.findViewById<Spinner>(R.id.sizes_spinner)
        val spinnerAdapter = ArrayAdapter(clientActivity, android.R.layout.simple_spinner_item, sizes)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sizesSpinner.adapter = spinnerAdapter
        sizesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedSize = sizesSpinner.getItemAtPosition(position) as DishSize

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        val addToCartButton = view.findViewById<Button>(R.id.add_to_cart_button)
        addToCartButton.setOnClickListener {
            if (selectedSize == null) {
                Toast.makeText(clientActivity, "Выберите размер пиццы", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val existsItem = Cart.orderItems.find { it.size_id == selectedSize!!.size_id }
            if (existsItem == null) {
                Cart.orderItems.add(OrderItem(0, selectedSize!!.size_id, 1))
                Toast.makeText(clientActivity, "Пицца добавлена в корзину", Toast.LENGTH_SHORT).show()
            }
            else {
                existsItem.quantity = existsItem.quantity + 1
                Toast.makeText(clientActivity, "Количество пиццы в корзине обновлено", Toast.LENGTH_SHORT).show()
            }

            clientActivity.openFragment(CartFragment())
        }
    }

    private fun getDish() {
        val dish = db.getDish(_dishId)
        val dishNameText = view?.findViewById<TextView>(R.id.dish_name_text)
        val dishDescriptionText = view?.findViewById<TextView>(R.id.dish_description_text)

        dishNameText?.text = dish?.name
        dishDescriptionText?.text = dish?.description
    }
}