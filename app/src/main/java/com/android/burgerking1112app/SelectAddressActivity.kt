package com.android.burgerking1112app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.burgerking1112app.adapters.AddressAdapter
import com.android.burgerking1112app.adapters.MenuAdapter
import com.android.burgerking1112app.adapters.ShoppingCartAdapter
import com.android.burgerking1112app.databinding.ActivitySelectAddressBinding
import com.android.burgerking1112app.models.Address
import com.android.burgerking1112app.models.CartItem
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlin.math.roundToLong

class SelectAddressActivity : AppCompatActivity() {
    private val view: ActivitySelectAddressBinding by lazy { ActivitySelectAddressBinding.inflate(layoutInflater)}
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

        view.tvAddAddress.setOnClickListener {
            val intent = Intent(this, AddAdressActivity::class.java)
            startActivity(intent)
        }

        view.icBackArrow.setOnClickListener {
            finish()
        }

        getAddresses(currentUser!!.uid)
    }

    private fun getAddresses(userId: String){
        var addressList = arrayListOf<Address>()
        var itemsRef = database.reference.child("addresses").child(userId)

        itemsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                addressList.clear()
                if(snapshot.exists()){
                    for(addressSnap in snapshot.children){
                        val addressData = addressSnap.getValue(Address::class.java)
                        addressList.add(addressData!!)
                        Log.d("Address", addressData.toString())
                    }
                }
                view.rvAddresses.adapter = AddressAdapter(this@SelectAddressActivity, addressList)
                view.rvAddresses.layoutManager =
                    LinearLayoutManager(this@SelectAddressActivity, LinearLayoutManager.VERTICAL, false)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
        
}