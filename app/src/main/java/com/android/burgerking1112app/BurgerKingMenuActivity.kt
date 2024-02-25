package com.android.burgerking1112app

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.burgerking1112app.adapters.MenuAdapter
import com.android.burgerking1112app.adapters.PromoAdapter
import com.android.burgerking1112app.databinding.ActivityBurgerKingMenuBinding
import com.android.burgerking1112app.models.MainMenu
import com.android.burgerking1112app.models.PromoMenu
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.firestore.firestore

class BurgerKingMenuActivity : AppCompatActivity() {
    private val view: ActivityBurgerKingMenuBinding by lazy { ActivityBurgerKingMenuBinding.inflate(layoutInflater) }
    private val database = Firebase.database("https://burger-king-1112-app-9e090-default-rtdb.asia-southeast1.firebasedatabase.app")

    private val menus = listOf(
        MainMenu("Cheesy Chick'N Crisp", "฿ 79", "https://firebasestorage.googleapis.com/v0/b/burger-king-1112-app-9e090.appspot.com/o/image%2Fcheesy_menu.png?alt=media&token=3307d99d-f453-4edb-bb97-de55bf4656ed"),
        MainMenu("Cheesy Chick'N Crisp", "฿ 79", "https://firebasestorage.googleapis.com/v0/b/burger-king-1112-app-9e090.appspot.com/o/image%2Fcheesy_menu.png?alt=media&token=3307d99d-f453-4edb-bb97-de55bf4656ed"),
        MainMenu("Cheesy Chick'N Crisp", "฿ 79", "https://firebasestorage.googleapis.com/v0/b/burger-king-1112-app-9e090.appspot.com/o/image%2Fcheesy_menu.png?alt=media&token=3307d99d-f453-4edb-bb97-de55bf4656ed")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        var promos = arrayListOf<PromoMenu>()

        val promosRef = database.reference.child("promos")

        promosRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                promos.clear()
                if(snapshot.exists()){
                    for(promoSnap in snapshot.children){
                        val promoData = promoSnap.getValue(PromoMenu::class.java)
                        Log.d(TAG, "Some logging "+ promoData?.promoDescription.toString() + promoData?.imgPath.toString());
                        promos.add(promoData!!)
                    }
                }

                view.rvHorizontalMenu.adapter = PromoAdapter(this@BurgerKingMenuActivity, promos)
                view.rvHorizontalMenu.layoutManager = LinearLayoutManager(this@BurgerKingMenuActivity, LinearLayoutManager.HORIZONTAL, false)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        view.rvVerticalMenu.adapter = MenuAdapter(this, menus)
        view.rvVerticalMenu.layoutManager = LinearLayoutManager(this)

        view.icShoppingCart.setOnClickListener {
            val intent = Intent(this, ShoppingCartActivity::class.java)
            startActivity(intent)
        }
    }

}