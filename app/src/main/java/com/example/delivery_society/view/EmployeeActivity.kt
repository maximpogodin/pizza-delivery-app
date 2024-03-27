package com.example.delivery_society.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.example.delivery_society.R
import com.google.android.material.navigation.NavigationBarView

class EmployeeActivity : AppCompatActivity() {

    public lateinit var navigationBarView : NavigationBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_employee)

        openFragment(OrderHistoryFragment(true))

        navigationBarView = findViewById(R.id.bottom_navigation)
        navigationBarView.setOnItemSelectedListener (onNavigationItemSelectedListener)
    }

    private val onNavigationItemSelectedListener = NavigationBarView.OnItemSelectedListener { menuItem ->
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()

        when (menuItem.itemId) {
            R.id.action_order_history -> {
                openFragment(OrderHistoryFragment(true))
                return@OnItemSelectedListener true
            }
            R.id.action_settings -> {
                openFragment(SettingsFragment(true))
                return@OnItemSelectedListener true
            }
        }
        false
    }

    fun openFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout, fragment).addToBackStack(null)
            .setReorderingAllowed(true)
            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .show(fragment).commit()
    }

    fun returnBack() {
        supportFragmentManager.popBackStack();
    }

    fun setActionBarTitle(title : String) {
        supportActionBar?.title = title
    }
}