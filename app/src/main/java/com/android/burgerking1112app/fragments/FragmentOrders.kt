package com.android.burgerking1112app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.burgerking1112app.databinding.FragmentOrdersBinding

class FragmentOrders: Fragment() {
    private lateinit var view: FragmentOrdersBinding

    // Map UI with fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentOrdersBinding.inflate(inflater, container, false)

        return view.root
    }
}