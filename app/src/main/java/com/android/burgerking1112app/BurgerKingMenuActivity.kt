package com.android.burgerking1112app

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.burgerking1112app.adapters.MenuAdapter
import com.android.burgerking1112app.adapters.PromoAdapter
import com.android.burgerking1112app.databinding.ActivityBurgerKingMenuBinding
import com.android.burgerking1112app.models.MainMenu
import com.android.burgerking1112app.models.PromoMenu
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class BurgerKingMenuActivity : AppCompatActivity() {
    private val view: ActivityBurgerKingMenuBinding by lazy { ActivityBurgerKingMenuBinding.inflate(layoutInflater) }
    private val database = Firebase.database("https://burger-king-1112-app-9e090-default-rtdb.asia-southeast1.firebasedatabase.app")
    private lateinit var auth: FirebaseAuth

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

        var promos = arrayListOf<PromoMenu>()
        val promosRef = database.reference.child("promos")

        view.backHome.setOnClickListener {
            finish();
        }

        view.menuProgressBar.visibility = View.VISIBLE

        promosRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                view.menuProgressBar.visibility = View.VISIBLE
                view.rvHorizontalMenu.visibility = View.GONE
                promos.clear()
                if(snapshot.exists()){
                    for(promoSnap in snapshot.children){
                        val promoData = promoSnap.getValue(PromoMenu::class.java)
                        Log.d(TAG, "Some logging "+ promoData?.promoDescription.toString() + promoData?.imgPath.toString());
                        promos.add(promoData!!)
                    }
                }

                view.menuProgressBar.visibility = View.GONE
                view.rvHorizontalMenu.visibility = View.VISIBLE
                view.rvHorizontalMenu.adapter = PromoAdapter(this@BurgerKingMenuActivity, promos, database, currentUser!!.uid)
                view.rvHorizontalMenu.layoutManager = LinearLayoutManager(this@BurgerKingMenuActivity, LinearLayoutManager.HORIZONTAL, false)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        var menus = arrayListOf<MainMenu>()
        var menuRef = database.reference.child("menus")
        menuRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                view.menuProgressBar.visibility = View.VISIBLE
                view.rvVerticalMenu.visibility = View.GONE
                menus.clear()
                if(snapshot.exists()){
                    for(menusSnap in snapshot.children){
                        val menuData = menusSnap.getValue(MainMenu::class.java)
                        Log.d(TAG, "Some logging "+ menuData?.name.toString() + menuData?.imgPath.toString());
                        menus.add(menuData!!)
                    }
                }


                view.menuProgressBar.visibility = View.GONE
                view.rvVerticalMenu.visibility = View.VISIBLE
                view.rvVerticalMenu.adapter = MenuAdapter(this@BurgerKingMenuActivity, menus,database,currentUser!!.uid)
                view.rvVerticalMenu.layoutManager = LinearLayoutManager(this@BurgerKingMenuActivity, LinearLayoutManager.VERTICAL, false)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        view.icShoppingCart.setOnClickListener {
            val intent = Intent(this, ShoppingCartActivity::class.java)
            startActivity(intent)
        }
    }

}