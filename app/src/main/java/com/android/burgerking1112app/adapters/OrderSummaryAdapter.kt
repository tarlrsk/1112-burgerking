package com.android.burgerking1112app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.burgerking1112app.databinding.AddressItemBinding
import com.android.burgerking1112app.databinding.OrderSummaryItemBinding
import com.android.burgerking1112app.models.Address
import com.android.burgerking1112app.models.OrderSummary
import com.google.firebase.storage.FirebaseStorage

class OrderSummaryAdapter (private val context: Context, private val menus: List<OrderSummary>)
    : RecyclerView.Adapter<OrderSummaryAdapter.RecyclerViewHolder>()  {
    class RecyclerViewHolder(val binding: OrderSummaryItemBinding): RecyclerView.ViewHolder(binding.root)
    private val storage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderSummaryAdapter.RecyclerViewHolder {
        val binding = OrderSummaryItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return OrderSummaryAdapter.RecyclerViewHolder(binding)
    }
    override fun getItemCount(): Int = menus.size

    override fun onBindViewHolder(holder: OrderSummaryAdapter.RecyclerViewHolder, position: Int) {
        val menu = menus[position]

        holder.binding.tvAmountOfProduct.text = menu.quantity.toString()
        holder.binding.tvMenuName.text = menu.name.toString()
        holder.binding.tvPricePerPiece.text = (menu.price!! * menu.quantity!!).toString()

    }
}