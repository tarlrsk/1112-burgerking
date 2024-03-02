package com.android.burgerking1112app.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.burgerking1112app.LoginActivity
import com.android.burgerking1112app.OrderStatusActivity
import com.android.burgerking1112app.PaymentActivity
import com.android.burgerking1112app.adapters.OrderHistoryAdapter
import com.android.burgerking1112app.databinding.FragmentOrdersBinding
import com.android.burgerking1112app.models.CartItem
import com.android.burgerking1112app.models.Order
import com.android.burgerking1112app.models.OrderHistory
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlin.math.roundToLong

class FragmentOrders: Fragment() {
    private lateinit var view: FragmentOrdersBinding
    private val database = Firebase.database("https://burger-king-1112-app-9e090-default-rtdb.asia-southeast1.firebasedatabase.app")
    private lateinit var auth: FirebaseAuth

    // Map UI with fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        view = FragmentOrdersBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            getOrders(currentUser.uid)
        }

        return view.root
    }

    private fun getOrders(userId: String){
        val orders = arrayListOf<Order>()
        val orderRef = database.reference.child("orders").child(userId).orderByChild("orderAt")

        orderRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orders.clear()
                if(snapshot.exists()){
                    for(orderSnap in snapshot.children){
                        val orderData = orderSnap.getValue(Order::class.java)
                        orders.add(orderData!!)
                    }
                }
                val sortedOrders = orders.sortedByDescending { it.orderAt }
                view.rvHistoryOrders.adapter = OrderHistoryAdapter(requireContext(), sortedOrders)
                view.rvHistoryOrders.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}