package com.android.burgerking1112app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.burgerking1112app.databinding.OrderHistoryItemBinding
import com.android.burgerking1112app.databinding.OrderSummaryItemBinding
import com.android.burgerking1112app.models.OrderHistory
import com.android.burgerking1112app.models.OrderSummary
import com.google.firebase.storage.FirebaseStorage

class OrderHistoryAdapter (private val context: Context, private val orders: List<OrderHistory>)
    : RecyclerView.Adapter<OrderHistoryAdapter.RecyclerViewHolder>()   {
    class RecyclerViewHolder(val binding: OrderHistoryItemBinding): RecyclerView.ViewHolder(binding.root)
    private val storage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryAdapter.RecyclerViewHolder {
        val binding = OrderHistoryItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return OrderHistoryAdapter.RecyclerViewHolder(binding)
    }
    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: OrderHistoryAdapter.RecyclerViewHolder, position: Int) {
        val order = orders[position]

        holder.binding.tvDateTime.text = order.date.toString()
        holder.binding.tvOrderStatusData.text = order.status.toString()
        holder.binding.tvOrderId.text = order.orderId.toString()
        holder.binding.tvAddress.text = order.address.toString()

    }
}