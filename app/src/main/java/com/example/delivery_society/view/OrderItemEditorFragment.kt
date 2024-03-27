package com.example.delivery_society.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.delivery_society.R
import com.example.delivery_society.api.ApplicationDbContext
import com.example.delivery_society.model.entities.Status

class OrderItemEditorFragment constructor(orderId : Int) : Fragment() {

    private var db = ApplicationDbContext()
    private val _orderId: Int = orderId
    private lateinit var employeeActivity: EmployeeActivity
    private var selectedStatus : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.order_item_editor_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        employeeActivity = (activity as EmployeeActivity)
        employeeActivity.setActionBarTitle("Данные по заказу")

        getOrder()
        val saveOrderButton = view.findViewById<Button>(R.id.save_order_button)
        val statusesSpinner = view.findViewById<Spinner>(R.id.statuses_spinner)
        val statuses = db.getStatuses()
        val statusArray = ArrayList<String>()
        statuses.forEach {
            statusArray.add(it.name)
        }

        val spinnerAdapter = ArrayAdapter(employeeActivity, android.R.layout.simple_spinner_item, statusArray)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        statusesSpinner.adapter = spinnerAdapter
        statusesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedStatus = statusesSpinner.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        saveOrderButton.setOnClickListener {
            if (selectedStatus == null) {
                Toast.makeText(employeeActivity, "Выберите статус заказа.", Toast.LENGTH_SHORT).show()
            }
            val status = db.getStatus(selectedStatus!!)
            db.updateOrderStatus(_orderId, status!!.status_id)
            employeeActivity.openFragment(OrderHistoryFragment(true))
        }
    }

    private fun getOrder() {
        val order = db.getOrder(_orderId)
        val orderIdText = view?.findViewById<TextView>(R.id.order_id_text)
        val orderItemsText = view?.findViewById<TextView>(R.id.order_items_text)
        val creationDatetimeText = view?.findViewById<TextView>(R.id.creation_datetime_text)
        val orderStatusText = view?.findViewById<TextView>(R.id.order_status_text)
        val orderSumText = view?.findViewById<TextView>(R.id.order_sum_text)

        orderIdText?.text = "№ заказа ${order?.order_id}"
        creationDatetimeText?.text = "Создан: ${order?.creation_datetime}"
        var orderItems = order?.order_items?.iterator()
        orderItems?.forEach {
            orderItemsText?.text = "${orderItemsText?.text}${it.dish_name} (${it.quantity} шт.) по цене (${it.dish_price}) = ${it.dish_price * it.quantity}\n"
        }
        orderStatusText?.text = "${order?.status_name}"
        orderSumText?.text = "Сумма заказа: ${order?.order_items?.sumOf { (it.quantity * it.dish_price) }}"
    }
}