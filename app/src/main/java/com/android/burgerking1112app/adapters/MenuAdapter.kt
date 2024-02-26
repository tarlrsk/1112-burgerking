package com.android.burgerking1112app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.burgerking1112app.databinding.VerticalMenuItemBinding
import com.android.burgerking1112app.models.CartItem
import com.android.burgerking1112app.models.MainMenu
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class MenuAdapter(private val context: Context, private val menus: ArrayList<MainMenu>, private val firebaseDatabase: FirebaseDatabase, private val userId:String)
    : RecyclerView.Adapter<MenuAdapter.RecyclerViewHolder>()  {
    class RecyclerViewHolder(val binding: VerticalMenuItemBinding): RecyclerView.ViewHolder(binding.root)
    private val storage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = VerticalMenuItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return RecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = menus.size

    override fun onBindViewHolder(holder: MenuAdapter.RecyclerViewHolder, position: Int) {
        val menu = menus[position]

        val storageRef = storage.reference.child("image/${menu.imgPath}")

        storageRef.downloadUrl.addOnSuccessListener {Uri->
            val imageURL = Uri.toString()
            Glide.with(context)
                .load(imageURL)
                .into(holder.binding.imgMenu)
        }

        Glide.with(context).load(storageRef).into(holder.binding.imgMenu)
        holder.binding.tvMenuName.text = menu.name.toString()
        holder.binding.tvMenuPrice.text = "฿ " + menu.price.toString()

        holder.binding.btnMenuSelect.setOnClickListener  {
//            val itemsSnapshot = firebaseDatabase.reference.child("carts").child(userId).get().result
//            for(itemSnapshot in itemsSnapshot.children){
//                val itemData = itemSnapshot.getValue(CartItem::class.java)
//                if (itemData!!.productId == menu.id){
//                    val cartRef = firebaseDatabase.reference.child("carts").child(userId).child(
//                        itemData.id.toString()
//                    )
//                    cartRef.child("quantity").setValue(3)
//                }
//            }
        val cartRef = firebaseDatabase.reference.child("carts").child(userId).push()
        val item = CartItem(cartRef.key,menu.id,menu.name, menu.price, menu.imgPath, 1)
        cartRef.setValue(item)
        }

    }
}