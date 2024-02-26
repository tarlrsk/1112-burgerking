package com.android.burgerking1112app

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.burgerking1112app.adapters.MenuAdapter
import com.android.burgerking1112app.adapters.ShoppingCartAdapter
import com.android.burgerking1112app.databinding.ActivityShoppingCartBinding
import com.android.burgerking1112app.models.MainMenu
import com.android.burgerking1112app.models.ShoppingCart
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ShoppingCartActivity : AppCompatActivity() {
    private val view: ActivityShoppingCartBinding by lazy { ActivityShoppingCartBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)


        val shoppingCart = listOf(
            ShoppingCart("", "Hat Yai Chicken King 2 pcs + 1 Sticky Rice + Drink 16 oz 1 cup", "1","169"),
            ShoppingCart("", "Hat Yai Chicken King 2 pcs + 1 Sticky Rice + Drink 16 oz 1 cup", "1","169"),
            ShoppingCart("", "Hat Yai Chicken King 2 pcs + 1 Sticky Rice + Drink 16 oz 1 cup", "1","169"),
            ShoppingCart("", "Hat Yai Chicken King 2 pcs + 1 Sticky Rice + Drink 16 oz 1 cup", "1","169")
        )


        view.rvListSelectedmenu.adapter = ShoppingCartAdapter(this, shoppingCart)
        view.rvListSelectedmenu.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)



        view.btnCheckOut.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }
    }
}