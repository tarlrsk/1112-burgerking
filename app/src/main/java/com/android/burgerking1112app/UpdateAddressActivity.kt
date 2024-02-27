package com.android.burgerking1112app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.burgerking1112app.databinding.ActivityUpdateAddressBinding

class UpdateAddressActivity : AppCompatActivity() {
    private val view: ActivityUpdateAddressBinding by lazy { ActivityUpdateAddressBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)
    }
}