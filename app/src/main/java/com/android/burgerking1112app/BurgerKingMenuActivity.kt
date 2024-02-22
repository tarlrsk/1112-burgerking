package com.android.burgerking1112app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.burgerking1112app.databinding.ActivityBurgerKingMenuBinding

class BurgerKingMenuActivity : AppCompatActivity() {
    private val view: ActivityBurgerKingMenuBinding by lazy { ActivityBurgerKingMenuBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)
    }
}