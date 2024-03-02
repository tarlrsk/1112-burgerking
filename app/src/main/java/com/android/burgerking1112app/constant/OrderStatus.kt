package com.android.burgerking1112app.constant

enum class OrderStatus(val status:String) {
    ORDER_RECEIVED("Order Received"),
    PREPARING("Preparing Order"),
    PICKING_UP("Pick up"),
    COMPLETED("Completed")
}