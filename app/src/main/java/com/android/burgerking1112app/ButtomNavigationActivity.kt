package com.android.burgerking1112app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.burgerking1112app.databinding.ActivityButtomNavigationBinding
import com.android.burgerking1112app.fragments.FragmentMenu
import com.android.burgerking1112app.fragments.FragmentMore
import com.android.burgerking1112app.fragments.FragmentOrders

class ButtomNavigationActivity : AppCompatActivity() {
    private val view: ActivityButtomNavigationBinding by lazy {ActivityButtomNavigationBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        view.bnvMain.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.item_menu -> changeFragment(FragmentMenu())
                R.id.item_orders-> changeFragment(FragmentOrders())
                R.id.item_more-> changeFragment(FragmentMore())
                else -> false
            }
        }
    }
    private fun changeFragment(fragment: Fragment): Boolean {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcMenu, fragment)
            .addToBackStack(fragment::class.java.name)
            .commit()
        return true
    }
}