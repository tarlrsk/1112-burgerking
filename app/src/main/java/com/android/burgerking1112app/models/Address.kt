package com.android.burgerking1112app.models

import com.android.burgerking1112app.constant.AddressLabel

data class Address(
    val id: String? = null,
    val title: String? = null,
    val address: String? = null,
    val phoneNo: String? = null,
    val remark: String? = null,
    val label: AddressLabel? = null,
)
