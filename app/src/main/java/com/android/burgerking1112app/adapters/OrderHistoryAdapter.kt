package com.android.burgerking1112app.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.burgerking1112app.OrderStatusActivity
import com.android.burgerking1112app.PaymentActivity
import com.android.burgerking1112app.databinding.OrderHistoryItemBinding
import com.android.burgerking1112app.databinding.OrderSummaryItemBinding
import com.android.burgerking1112app.models.Order
import com.android.burgerking1112app.models.OrderHistory
import com.android.burgerking1112app.models.OrderSummary
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OrderHistoryAdapter (private val context: Context, private val orders: List<Order>)
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

        val orderAt = LocalDateTime.parse(order.orderAt)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")

        holder.binding.tvDateTime.text = orderAt.format(formatter)
        holder.binding.tvOrderStatusData.text = order.status!!.status
        holder.binding.tvOrderId.text = order.orderNo.toString()
        holder.binding.tvAddress.text = order.deliveryTo.toString()

        holder.binding.orderBox.setOnClickListener{
            val intent = Intent(context, OrderStatusActivity::class.java)
            intent.putExtra("orderId", order.orderNo)
            context.startActivity(intent)
        }
    }
}