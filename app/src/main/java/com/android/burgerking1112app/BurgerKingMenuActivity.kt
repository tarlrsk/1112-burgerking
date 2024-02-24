package com.android.burgerking1112app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.HorizontalScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.burgerking1112app.adapters.PromoAdapter
import com.android.burgerking1112app.databinding.ActivityBurgerKingMenuBinding
import com.android.burgerking1112app.models.PromoMenu

class BurgerKingMenuActivity : AppCompatActivity() {
    private val view: ActivityBurgerKingMenuBinding by lazy { ActivityBurgerKingMenuBinding.inflate(layoutInflater) }
    private val promos = listOf(
        PromoMenu("Buy 1 Get 1 Free : Mushroom Swiss (Beef) + Regular French Fries", "฿ 258", "https://firebasestorage.googleapis.com/v0/b/burgerking1112app.appspot.com/o/hatyai_promo.png?alt=media&token=46b27b8c-919e-4f0a-8f0a-e4d5875cf140"),
        PromoMenu("Hat Yai Chicken King 2 pcs + 1 sticky Rice + Drink 16 oz 1 cup", "฿ 129", "https://firebasestorage.googleapis.com/v0/b/burgerking1112app.appspot.com/o/mushroom_promo.png?alt=media&token=1cf41754-b678-4ce3-9b83-f7b1eeba9e6d"),
        PromoMenu("Hat Yai Chicken King 2 pcs + 1 sticky Rice + Drink 16 oz 1 cup", "฿ 129", "https://firebasestorage.googleapis.com/v0/b/burgerking1112app.appspot.com/o/mushroom_promo.png?alt=media&token=1cf41754-b678-4ce3-9b83-f7b1eeba9e6d")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)


        view.rvHorizontalMenu.adapter = PromoAdapter(this, promos)
        view.rvHorizontalMenu.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
    }
}