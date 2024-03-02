package com.android.burgerking1112app.models

data class Order(
    val OrderNo: String? = null,
    val OrderAt: String? = null,
    val Status: String? = null,
    val DeliveryTo: String? = null,
    val Items: List<OrderSummary>? = null,
    val Discount: Long? = null,
)
