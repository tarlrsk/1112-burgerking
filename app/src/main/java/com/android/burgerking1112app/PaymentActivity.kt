package com.android.burgerking1112app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import com.android.burgerking1112app.databinding.ActivityPaymentBinding
import com.android.burgerking1112app.models.Address
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

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
        var userAddresses = arrayListOf<Address>()
        val userAddressesRef = database.reference.child("addresses").child(currentUser!!.uid)
        userAddressesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userAddresses.clear()
                if(snapshot.exists()){
                    for (addressSnapshot in snapshot.children){
                        val addressData = snapshot.getValue(Address::class.java)
                        userAddresses.add(addressData!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        if (addressId == null && userAddresses.size == 0){
            val intent = Intent(this, SelectAddressActivity::class.java)
            Log.d("userAddress", userAddresses.size.toString())
            startActivity(intent)
            finish()
        }else if(addressId == null){
            addressId = userAddresses[0].id
        }


        val addressRef = database.reference.child("addresses").child(currentUser!!.uid).child(addressId!!)
        addressRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val addressData = snapshot.getValue(Address::class.java)
                    view.tvWorkTitle.text = addressData!!.title.toString()
                    view.tvAddress.text = addressData!!.address.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        view.tvTotalPrice.text = "฿ " + totalPrice
        view.imgDeliveryBox.setOnClickListener {
            val intent = Intent(this, SelectAddressActivity::class.java)
            startActivity(intent)
        }
        view.btnPlaceOrder.setOnClickListener {
            val intent = Intent(this, OrderStatusActivity::class.java)
            startActivity(intent)
        }

        view.icBackArrow.setOnClickListener {
            finish()
        }
    }
}