package com.android.burgerking1112app

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.burgerking1112app.adapters.AddressAdapter
import com.android.burgerking1112app.constant.AddressLabel
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
    private var label: AddressLabel? = null
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
                    if (addressData.label == AddressLabel.HOME){
                        selectHome()
                    }else if (addressData.label == AddressLabel.WORK){
                        selectWork()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        view.btnWorkAddress.setOnClickListener {
            selectWork()
        }

        view.btnHomeAddress.setOnClickListener {
            selectHome()
        }

        view.btnUpdateAddress.setOnClickListener {
            val title = view.editAddressName.text.toString()
            val address1 = view.editAddressInfo.text.toString()
            val phoneNo = view.editPhoneNumber.text.toString()
            val remark = view.editNoteToDriver.text.toString()
            val label = label
            addressRef.setValue(Address(addressId,title,address1,phoneNo,remark,label))
            finish()
        }

    }
    private fun selectWork(){
        if(label == AddressLabel.WORK){
            view.btnWorkAddress.setTextColor(Color.parseColor("#AAAAAA"))
            view.btnWorkAddress.setBackgroundColor(Color.parseColor("#FFFFFF"))

            label = null
        }else{
            view.btnWorkAddress.setTextColor(Color.WHITE)
            view.btnWorkAddress.setBackgroundColor(Color.parseColor("#F9A623"))

            view.btnHomeAddress.setTextColor(Color.parseColor("#AAAAAA"))
            view.btnHomeAddress.setBackgroundColor(Color.parseColor("#FFFFFF"))
            label = AddressLabel.WORK
        }
    }
    private fun selectHome(){
        if(label == AddressLabel.HOME){
            view.btnHomeAddress.setTextColor(Color.parseColor("#AAAAAA"))
            view.btnHomeAddress.setBackgroundColor(Color.parseColor("#FFFFFF"))
            label = null
        }else{
            view.btnHomeAddress.setTextColor(Color.WHITE)
            view.btnHomeAddress.setBackgroundColor(Color.parseColor("#F9A623"))

            view.btnWorkAddress.setTextColor(Color.parseColor("#AAAAAA"))
            view.btnWorkAddress.setBackgroundColor(Color.parseColor("#FFFFFF"))
            label = AddressLabel.HOME
        }
    }
}