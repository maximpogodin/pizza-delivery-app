package com.example.delivery_society.view

import DishListAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery_society.R
import com.example.delivery_society.api.ApplicationDbContext
import com.example.delivery_society.model.entities.Dish

class DishListFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private var db = ApplicationDbContext()
    private lateinit var dishAdapter: DishListAdapter
    private lateinit var clientActivity: ClientActivity

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
        clientActivity = (activity as ClientActivity)
        clientActivity.setActionBarTitle("Меню")

        getDishes()
    }

    private fun getDishes() {
        recyclerView.adapter = null
        dishAdapter =
            DishListAdapter(db.getDishes(), object : DishListAdapter.Callback {
                override fun onItemClicked(item: Dish) {
                    clientActivity.openFragment(
                        DishFragment(item.dish_id)
                    )
                }
            }, clientActivity)
        recyclerView.adapter = dishAdapter
    }
}