package com.android.burgerking1112app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.burgerking1112app.adapters.AddressAdapter
import com.android.burgerking1112app.adapters.MenuAdapter
import com.android.burgerking1112app.databinding.ActivitySelectAddressBinding
import com.android.burgerking1112app.models.Address

class SelectAddressActivity : AppCompatActivity() {
    private val view: ActivitySelectAddressBinding by lazy { ActivitySelectAddressBinding.inflate(layoutInflater)}
    private val address = listOf<Address>(
        Address("Assumption University", "Bang Sao Thong, Bang Sao Thong District, Samut Prakan 10540"),
        Address("Airport", "999, Nong Prue, Bang Phli District, Samut Prakan 10540")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        view.rvAddresses.adapter = AddressAdapter(this, address)
        view.rvAddresses.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
        
}