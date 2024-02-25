package com.android.burgerking1112app

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.burgerking1112app.adapters.PromoAdapter
import com.android.burgerking1112app.databinding.ActivityBurgerKingMenuBinding
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


    }

}