package com.android.burgerking1112app.models

data class CartItem(
    val id: String? = null,
    val productId: String? = null,
    val name: String? = null,
    val price: Long? = null,
    val imgPath: String? = null,
    var quantity: Int ? = null
)
