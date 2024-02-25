package com.android.burgerking1112app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.burgerking1112app.databinding.HorizonPromoMenuItemBinding
import com.android.burgerking1112app.databinding.VerticalMenuItemBinding
import com.android.burgerking1112app.models.MainMenu
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage

class MenuAdapter(private val context: Context, private val menus: List<MainMenu>)
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
    }
}