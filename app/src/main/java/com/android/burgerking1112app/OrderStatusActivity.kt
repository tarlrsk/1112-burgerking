package com.android.burgerking1112app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.burgerking1112app.adapters.OrderSummaryAdapter
import com.android.burgerking1112app.adapters.PromoAdapter
import com.android.burgerking1112app.databinding.ActivityOrderStatusBinding
import com.android.burgerking1112app.fragments.FragmentMenu
import com.android.burgerking1112app.models.OrderSummary


class OrderStatusActivity : AppCompatActivity() {

    private val view: ActivityOrderStatusBinding by lazy { ActivityOrderStatusBinding.inflate(layoutInflater) }

    private val orderSummary = listOf(
        OrderSummary(1, "Tempura Nugget 9 pcs + Extralong Chicken", 169),
        OrderSummary(2, "Hat Yai Chicken", 69),
        OrderSummary(1, "Flame-grill BBQ Cheese", 169),
        OrderSummary(1, "Small Shake'n Cheese Fries", 49),
        OrderSummary(1, "Mushroom Swiss(Beef)", 258),
        OrderSummary(1, "Whopper Jr. Cheese", 255)


    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        view.rvOrderSummary.adapter = OrderSummaryAdapter(this, orderSummary)
        view.rvOrderSummary.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }


}


