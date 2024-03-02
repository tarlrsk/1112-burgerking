package com.android.burgerking1112app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.burgerking1112app.databinding.HorizonPromoMenuItemBinding
import com.android.burgerking1112app.models.CartItem
import com.android.burgerking1112app.models.PromoMenu
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage




class PromoAdapter(private val context: Context, private val promos: List<PromoMenu>,private val firebaseDatabase: FirebaseDatabase, private val userId: String,private val existingCartItem: ArrayList<CartItem>)
    : RecyclerView.Adapter<PromoAdapter.RecyclerViewHolder>() {
    class RecyclerViewHolder(val binding: HorizonPromoMenuItemBinding): RecyclerView.ViewHolder(binding.root)

    private val storage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = HorizonPromoMenuItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return RecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = promos.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val promo = promos[position]

        val storageRef = storage.reference.child("image/${promo.imgPath}")

        storageRef.downloadUrl.addOnSuccessListener {Uri->
            val imageURL = Uri.toString()
            Glide.with(context)
                .load(imageURL)
                .into(holder.binding.imgPromo)
        }

        Glide.with(context).load(storageRef).into(holder.binding.imgPromo)
        holder.binding.tvPromoDescription.text = promo.promoDescription.toString()
        holder.binding.tvPromoPrice.text = "฿ " + promo.promoPrice.toString()

        holder.binding.btnPromoSelect.setOnClickListener  {
            val cartRef = firebaseDatabase.reference.child("carts").child(userId)
            var isFound = false
            for (cartItem in existingCartItem) {
                if (cartItem.productId == promo.id) {
                    cartItem.quantity = cartItem.quantity?.plus(1)
                    cartRef.child(cartItem.id.toString()).setValue(cartItem)
                    isFound = true
                    break
                }
            }
            if (!isFound) {
                val newCartRef = cartRef.push()
                val item =
                    CartItem(newCartRef.key, promo.id, promo.promoDescription, promo.promoPrice, promo.imgPath, 1)
                newCartRef.setValue(item)
            }
        }
    }
}