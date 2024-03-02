package com.android.burgerking1112app

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.burgerking1112app.constant.AddressLabel
import com.android.burgerking1112app.databinding.ActivityAddAdressBinding
import com.android.burgerking1112app.databinding.ActivityUpdateAddressBinding
import com.android.burgerking1112app.models.Address
import com.android.burgerking1112app.models.CartItem
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class AddAdressActivity : AppCompatActivity() {
    private val view: ActivityAddAdressBinding by lazy { ActivityAddAdressBinding.inflate(layoutInflater)}
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

        view.btnWorkAddress.setOnClickListener {
            selectWork()
        }

        view.btnHomeAddress.setOnClickListener {
            selectHome()
        }

        view.btnAddAddress.setOnClickListener {
            val addressRef = database.reference.child("addresses").child(currentUser!!.uid).push()
            val title = view.editAddressName.text.toString()
            val address1 = view.editAddressInfo.text.toString()
            val phoneNo = view.editPhoneNumber.text.toString()
            val remark = view.editNoteToDriver.text.toString()
            val label = label
            val address = Address(addressRef.key,title,address1,phoneNo,remark,label)
            addressRef.setValue(address)
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