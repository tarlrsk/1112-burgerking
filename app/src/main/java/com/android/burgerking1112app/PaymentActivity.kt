package com.android.burgerking1112app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.burgerking1112app.adapters.ShoppingCartAdapter
import com.android.burgerking1112app.constant.OrderStatus
import com.android.burgerking1112app.databinding.ActivityPaymentBinding
import com.android.burgerking1112app.models.Address
import com.android.burgerking1112app.models.CartItem
import com.android.burgerking1112app.models.Order
import com.android.burgerking1112app.models.OrderSummary
import com.google.common.math.DoubleMath.roundToLong
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.function.DoubleToLongFunction
import kotlin.math.roundToLong

class PaymentActivity : AppCompatActivity() {
    private val view: ActivityPaymentBinding by lazy { ActivityPaymentBinding.inflate(layoutInflater) }
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

        val totalPrice = intent.getLongExtra("totalPrice", 0)
        var addressId = intent.getStringExtra("addressId")
        var discount = intent.getDoubleExtra("discount", 0.00)

        var userAddresses = arrayListOf<Address>()

        val addressRef = database.reference.child("addresses").child(currentUser!!.uid).child(addressId.toString())
        addressRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val addressData = snapshot.getValue(Address::class.java)
                    view.tvWorkTitle.text = addressData!!.title.toString()
                    view.tvAddress.text = addressData!!.address.toString()
                    getTotalPrice(currentUser!!.uid, discount)
                }else{
                    val userAddressesRef = database.reference.child("addresses").child(currentUser!!.uid)
                    userAddressesRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            userAddresses.clear()
                            if(snapshot.exists()){
                                for (addressSnapshot in snapshot.children){
                                    val addressData = addressSnapshot.getValue(Address::class.java)
                                    Log.d("userAddress1", addressData.toString())
                                    view.tvWorkTitle.text = addressData!!.title.toString()
                                    view.tvAddress.text = addressData!!.address.toString()
                                    break;
                                }
                            }else{
                                val intent = Intent(this@PaymentActivity, SelectAddressActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            Log.d("userAddress1", userAddresses.toString())
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        view.btnPlaceOrder.setOnClickListener {
            checkoutOrder(currentUser!!.uid,discount)
        }

        view.tvTotalPrice.text = "฿ $totalPrice"
        view.imgDeliveryBox.setOnClickListener {
            val intent = Intent(this, SelectAddressActivity::class.java)
            intent.putExtra("discount",discount)
            startActivity(intent)
            finish()
        }

        view.icBackArrow.setOnClickListener {
            finish()
        }

    }

    private fun getTotalPrice(userId: String, discount: Double){
        val cartItems = arrayListOf<CartItem>()
        val itemsRef = database.reference.child("carts").child(userId)
        var totalPrice: Double = 0.0
        val shippingPrice = 19

        itemsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                totalPrice = 0.0
                cartItems.clear()
                if(snapshot.exists()){
                    for(cartSnap in snapshot.children){
                        val cartItemData = cartSnap.getValue(CartItem::class.java)
                        cartItems.add(cartItemData!!)
                        totalPrice += cartItemData.price!! * cartItemData.quantity!!
                    }
                }
                totalPrice -= totalPrice * discount
                totalPrice += shippingPrice
                view.tvTotalPrice.text = "฿ ${totalPrice.roundToLong()} "
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun checkoutOrder(userId: String, discount: Double){
        var cartItems = arrayListOf<CartItem>()
        var cartRef = database.reference.child("carts").child(userId)
        var totalPrice: Double = 0.0
        var shippingPrice: Long = 19
        var totalDiscount: Double = 0.0
        var click = -1
        var orderItem = arrayListOf<OrderSummary>()

        cartRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                totalPrice = 0.0
                cartItems.clear()
                if(snapshot.exists()){
                    for(cartSnap in snapshot.children){
                        val cartItemData = cartSnap.getValue(CartItem::class.java)
                        cartItems.add(cartItemData!!)
                        totalPrice += cartItemData.price!! * cartItemData.quantity!!
                        orderItem.add(OrderSummary(cartItemData.quantity,cartItemData.name,cartItemData.price))
                    }
                }
                click += 1
                val subTotal = totalPrice
                Log.d("cartPrice", totalPrice.toString())
                totalDiscount = totalPrice * discount
                totalPrice += shippingPrice
                Log.d("cartPrice",totalDiscount.toString())
                Log.d("cartPrice",discount.toString())
                totalPrice -= totalDiscount

                val orderRef = database.reference.child("orders").child(userId).push()
                if(click == 0){
                    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

                    val order = Order(orderRef.key,LocalDateTime.now().format(formatter),OrderStatus.ORDER_RECEIVED,view.tvWorkTitle.text.toString(),totalDiscount,shippingPrice,subTotal,totalPrice)
                    orderRef.setValue(order)

                    val orderItemRef = orderRef.child("items")
                    orderItemRef.setValue(orderItem)



                    val navIntent = Intent(applicationContext, ButtomNavigationActivity::class.java)
                    val orderStatusIntent = Intent(applicationContext, OrderStatusActivity::class.java)
                    orderStatusIntent.putExtra("orderId", orderRef.key)
                    navIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(navIntent)
                    startActivity(orderStatusIntent)
                    finish()
                    cartRef.removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}