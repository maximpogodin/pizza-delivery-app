package com.example.delivery_society.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.delivery_society.R
import com.example.delivery_society.api.ApplicationDbContext
import com.example.delivery_society.model.entities.CurrentUser
class SettingsFragment constructor(employeeMode : Boolean) : Fragment() {

    private var db = ApplicationDbContext()
    private lateinit var clientActivity: ClientActivity
    private lateinit var employeeActivity: EmployeeActivity
    private var _employeeMode = employeeMode

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!_employeeMode) {
            clientActivity = (activity as ClientActivity)
            clientActivity.setActionBarTitle("Настройки")
        }
        else {
            employeeActivity = (activity as EmployeeActivity)
            employeeActivity.setActionBarTitle("Настройки")
        }

        getUser()
        getLoyaltyLevel()

        val logOutButton = view.findViewById<Button>(R.id.log_out_button)
        logOutButton.setOnClickListener {
            CurrentUser.user = null
            if (!_employeeMode) {
                clientActivity.finish()
            }
            else {
                employeeActivity.finish()
            }
            activity?.let {
                val intent = Intent(it, AuthorizationActivity::class.java)
                it.startActivity(intent)
            }
        }
    }

    private fun getUser() {
        val user = CurrentUser.user
        val usernameText = view?.findViewById<TextView>(R.id.username_text)
        usernameText?.text = "${user?.last_name} ${user?.first_name}"
    }

    private fun getLoyaltyLevel() {
        val loyaltyLevel = db.getUserLoyaltyLevel(CurrentUser.user!!.user_id)
        val loyaltyLevelText = view?.findViewById<TextView>(R.id.loyalty_level_text)
        val discountText = view?.findViewById<TextView>(R.id.discount_text)

        loyaltyLevelText?.text = loyaltyLevel?.name
        discountText?.text = "${loyaltyLevel?.discount}%"
    }
}