package com.android.burgerking1112app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.burgerking1112app.adapters.AddressAdapter
import com.android.burgerking1112app.databinding.ActivityUpdateAddressBinding
import com.android.burgerking1112app.models.Address
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class UpdateAddressActivity : AppCompatActivity() {
    private val view: ActivityUpdateAddressBinding by lazy { ActivityUpdateAddressBinding.inflate(layoutInflater)}
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
        view.icBackArrow.setOnClickListener {
            finish()
        }
        val addressId = getIntent().getStringExtra("addressId")

        if (addressId == null){
            finish()
        }

        val addressRef = database.reference.child("addresses").child(currentUser!!.uid).child(addressId!!)

        addressRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val addressData = snapshot.getValue(Address::class.java)
                    view.editPhoneNumber.text = Editable.Factory.getInstance().newEditable(addressData!!.phoneNo.toString())
                    view.editAddressName.text = Editable.Factory.getInstance().newEditable(addressData!!.title.toString())
                    view.editAddressInfo.text = Editable.Factory.getInstance().newEditable(addressData!!.address.toString())
                    view.editNoteToDriver.text = Editable.Factory.getInstance().newEditable(addressData!!.remark.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        view.btnUpdateAddress.setOnClickListener {
            val title = view.editAddressName.text.toString()
            val address1 = view.editAddressInfo.text.toString()
            val phoneNo = view.editPhoneNumber.text.toString()
            val remark = view.editNoteToDriver.text.toString()
            val label = "HOME"
            addressRef.setValue(Address(addressId,title,address1,phoneNo,remark,label))
            finish()
        }

    }
}