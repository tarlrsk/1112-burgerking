package com.android.burgerking1112app.models

import com.android.burgerking1112app.constant.OrderStatus
import java.time.LocalDateTime

data class Order(
    val orderNo: String? = null,
    val orderAt: String? = null,
    val status: OrderStatus? = null,
    val deliveryTo: String? = null,
    val totalDiscount: Double? = null,
    val deliveryFee: Long? = null,
    val subTotal: Double? = null,
    val totalPrice: Double? = null,
)
