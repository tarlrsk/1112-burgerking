package com.android.burgerking1112app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.burgerking1112app.databinding.ShoppingCartListItemBinding
import com.android.burgerking1112app.models.CartItem
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class ShoppingCartAdapter (private val context: Context, private val items: List<CartItem>, private val firebaseDatabase: FirebaseDatabase, private val userId: String)
    : RecyclerView.Adapter<ShoppingCartAdapter.RecyclerViewHolder>()  {
    class RecyclerViewHolder(val binding: ShoppingCartListItemBinding): RecyclerView.ViewHolder(binding.root)
    private val storage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = ShoppingCartListItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return RecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ShoppingCartAdapter.RecyclerViewHolder, position: Int) {
        val item = items[position]

        val storageRef = storage.reference.child("image/${item.imgPath}")

        storageRef.downloadUrl.addOnSuccessListener {Uri->
            val imageURL = Uri.toString()
            Glide.with(context)
                .load(imageURL)
                .into(holder.binding.imgMenuCart)
        }

        Glide.with(context).load(storageRef).into(holder.binding.imgMenuCart)
        holder.binding.tvMenuName.text = item.name.toString()
        holder.binding.tvQuantity.text = item.quantity.toString()
        holder.binding.tvItemPrice.text = "฿ " + (item.quantity?.times(item.price!!)).toString()

        holder.binding.btnPlus.setOnClickListener {
            val cartRef = firebaseDatabase.reference.child("carts").child(userId).child(item.id!!)
            item.quantity = item.quantity?.plus(1)
            cartRef.setValue(item)
        }
        holder.binding.btnMinus.setOnClickListener {
            val cartRef = firebaseDatabase.reference.child("carts").child(userId).child(item.id!!)
            item.quantity = item.quantity?.minus(1)
            if (item.quantity != 0){
                cartRef.setValue(item)
            }else{
                cartRef.removeValue()
            }
        }

    }

}