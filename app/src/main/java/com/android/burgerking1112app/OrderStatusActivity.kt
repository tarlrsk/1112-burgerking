package com.android.burgerking1112app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.burgerking1112app.adapters.OrderSummaryAdapter
import com.android.burgerking1112app.constant.OrderStatus
import com.android.burgerking1112app.databinding.ActivityOrderStatusBinding
import com.android.burgerking1112app.models.Order
import com.android.burgerking1112app.models.OrderSummary
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.kofigyan.stateprogressbar.StateProgressBar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToLong


class OrderStatusActivity : AppCompatActivity() {

    private val view: ActivityOrderStatusBinding by lazy { ActivityOrderStatusBinding.inflate(layoutInflater) }
    private val database = Firebase.database("https://burger-king-1112-app-9e090-default-rtdb.asia-southeast1.firebasedatabase.app")
    private lateinit var auth: FirebaseAuth
    var descriptionData = arrayOf("Order Received", "Preparing", "Picking up", "Completed")

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

        view.statusBar.setStateDescriptionData(descriptionData)
        view.statusBar.stateDescriptionSize = 12F
        val orderId = intent.getStringExtra("orderId")
        if (orderId == null){
            val intent = Intent(this,ButtomNavigationActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val orderRef = database.reference.child("orders").child(currentUser!!.uid).child(orderId)

            orderRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val orderData = snapshot.getValue(Order::class.java)
                        if(orderData != null){
                            val orderAtDateTime = LocalDateTime.parse(orderData.orderAt)
                            val arriveDateTime = orderAtDateTime.plusHours(1)
                            val formatter = DateTimeFormatter.ofPattern("HH:mm, E dd MMM yyyy")

                            view.tvOrderDateTimeData.text = orderAtDateTime.format(formatter)
                            view.tvDeliverytimeData.text = arriveDateTime.format(formatter)
                            view.tvOrderNumberData.text = orderData.orderNo
                            view.tvOrderStatusData.text = orderData.status?.status ?: "Something wrong with status"
                            view.tvDeliverToData.text  = orderData.deliveryTo
                            view.tvSubtotalData.text = "฿ ${orderData.subTotal!!.roundToLong()}"
                            view.tvDeliveryFeeData.text = "฿ ${orderData.deliveryFee}"
                            view.tvDiscount.text = "฿ ${orderData.totalDiscount!!.roundToLong()}"
                            view.tvTotalDiscountData.text = "- ฿ ${orderData.totalDiscount!!.roundToLong()}"
                            view.tvTotalPriceData.text = "฿ ${orderData.totalPrice!!.roundToLong()}"
                            view.tvGreenTotalPriceData.text = "฿ ${orderData.subTotal!!.roundToLong()}"
                        }

                        when (orderData!!.status) {
                            OrderStatus.ORDER_RECEIVED -> view.statusBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE)
                            OrderStatus.PREPARING -> view.statusBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO)
                            OrderStatus.PICKING_UP -> view.statusBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE)
                            OrderStatus.COMPLETED -> view.statusBar.setAllStatesCompleted(true)
                            null -> view.statusBar.setAllStatesCompleted(false)
                        }

                        val orderItems = arrayListOf<OrderSummary>()
                        val orderItemRef = orderRef.child("items")
                        orderItemRef.addValueEventListener(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                orderItems.clear()
                                if(snapshot.exists()){
                                    for(itemSnap in snapshot.children){
                                        val itemData = itemSnap.getValue(OrderSummary::class.java)
                                        orderItems.add(itemData!!)
                                    }
                                }

                                view.rvOrderSummary.adapter = OrderSummaryAdapter(this@OrderStatusActivity, orderItems)
                                view.rvOrderSummary.layoutManager = LinearLayoutManager(this@OrderStatusActivity, LinearLayoutManager.VERTICAL, false)

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
        }

        view.icBackArrow.setOnClickListener {
            finish();
        }
    }


}


