package com.android.burgerking1112app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.burgerking1112app.adapters.ShoppingCartAdapter
import com.android.burgerking1112app.databinding.ActivityShoppingCartBinding
import com.android.burgerking1112app.models.CartItem
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlin.math.roundToLong

class ShoppingCartActivity : AppCompatActivity() {
    private val view: ActivityShoppingCartBinding by lazy { ActivityShoppingCartBinding.inflate(layoutInflater) }
    private val database = Firebase.database("https://burger-king-1112-app-9e090-default-rtdb.asia-southeast1.firebasedatabase.app")
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)
        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        getCartItem(currentUser!!.uid)

        view.include.icCloseCart.setOnClickListener {
            finish()
        }

        view.btnRemoveAll.setOnClickListener {
            database.reference.child("carts").child(currentUser!!.uid).removeValue()
        }
    }

    private fun getCartItem(userId: String){
        var cartItems = arrayListOf<CartItem>()
        var itemsRef = database.reference.child("carts").child(userId)
        var totalPrice: Double = 0.0
        var shippingPrice = 19
        var codeUsed = false

        itemsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartItems.clear()
                if(snapshot.exists()){
                    for(cartSnap in snapshot.children){
                        val cartItemData = cartSnap.getValue(CartItem::class.java)
                        cartItems.add(cartItemData!!)
                        totalPrice += cartItemData.price!! * cartItemData.quantity!!
                    }
                }

                totalPrice += shippingPrice
                view.rvListSelectedmenu.adapter = ShoppingCartAdapter(this@ShoppingCartActivity, cartItems)
                view.rvListSelectedmenu.layoutManager = LinearLayoutManager(this@ShoppingCartActivity, LinearLayoutManager.VERTICAL, false)
                view.tvTotalPrice.text = "฿ ${totalPrice.roundToLong()}"
                codeUsed = false
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        view.btnApplyPromoCode.setOnClickListener {
            val code = view.tvPromoCode.text.toString()
            if (code.uppercase() == "DISCOUNT5" && !codeUsed) {
                totalPrice -= shippingPrice
                totalPrice *= (1.00 - 0.05)
                totalPrice += shippingPrice
                view.tvTotalPrice.text = "฿ ${totalPrice.roundToLong()}"
                codeUsed = true
            } else if (code.uppercase() == "DISCOUNT5" && codeUsed) {
                Toast.makeText(
                    this@ShoppingCartActivity,
                    "DISCOUNT5 is already applied",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this@ShoppingCartActivity, "Invalid Promo Code", Toast.LENGTH_SHORT)
                    .show()
            }
        }


        view.btnCheckOut.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }

        view.tvAddMore.setOnClickListener {
            val intent = Intent(this, BurgerKingMenuActivity::class.java)
            startActivity(intent)
            
            finish();
        }
    }
}