package com.android.burgerking1112app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.burgerking1112app.adapters.AddressAdapter
import com.android.burgerking1112app.adapters.OrderHistoryAdapter
import com.android.burgerking1112app.databinding.FragmentOrdersBinding
import com.android.burgerking1112app.models.Address
import com.android.burgerking1112app.models.OrderHistory

class FragmentOrders: Fragment() {
    private lateinit var view: FragmentOrdersBinding

    private val orders = listOf<OrderHistory>(
        OrderHistory("29 DEC 2023 12:27", "Arrived", "2041986341", "Assumption University")
    )

    // Map UI with fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentOrdersBinding.inflate(inflater, container, false)

        return view.root

        view.rvHistoryOrders.adapter = OrderHistoryAdapter(requireContext(), orders)
        view.rvHistoryOrders.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }
}