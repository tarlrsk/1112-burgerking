package com.android.burgerking1112app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.burgerking1112app.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    private val view: ActivityPaymentBinding by lazy { ActivityPaymentBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        view.icMoreAboutAdress.setOnClickListener {
            val intent = Intent(this, SelectAddressActivity::class.java)
            startActivity(intent)
        }
        view.btnPlaceOrder.setOnClickListener {
            val intent = Intent(this, OrderStatusActivity::class.java)
            startActivity(intent)
        }
    }
}