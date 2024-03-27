package com.example.delivery_society.view

import OrderHistoryAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery_society.R
import com.example.delivery_society.api.ApplicationDbContext
import com.example.delivery_society.model.entities.CurrentUser
import com.example.delivery_society.model.entities.OrderDTO

class OrderHistoryFragment (employeeMode : Boolean) : Fragment() {

    lateinit var recyclerView: RecyclerView
    private var _employeeMode : Boolean = employeeMode
    private var db = ApplicationDbContext()
    private lateinit var orderHistoryAdapter: OrderHistoryAdapter
    private lateinit var clientActivity: ClientActivity
    private lateinit var employeeActivity: EmployeeActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.base_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.base_list_recycler_view)

        if (_employeeMode) {
            employeeActivity = (activity as EmployeeActivity)
            employeeActivity.setActionBarTitle("Заказы")
        }
        else {
            clientActivity = (activity as ClientActivity)
            clientActivity.setActionBarTitle("История заказов")
        }

        getOrderHistory()
    }

    private fun getOrderHistory() {
        recyclerView.adapter = null
        if (_employeeMode) {
            orderHistoryAdapter = OrderHistoryAdapter(db.getAllOrders(), _employeeMode, object : OrderHistoryAdapter.Callback {
                override fun onItemClicked(item: OrderDTO) {
                    employeeActivity.openFragment(
                        OrderItemEditorFragment(item.order_id)
                    )
                }
            })
        }
        else {
            orderHistoryAdapter = OrderHistoryAdapter(db.getOrderHistory(CurrentUser.user!!.user_id), _employeeMode, object : OrderHistoryAdapter.Callback {
                override fun onItemClicked(item: OrderDTO) {
                    // No action
                }
            })
        }

        recyclerView.adapter = orderHistoryAdapter
    }
}