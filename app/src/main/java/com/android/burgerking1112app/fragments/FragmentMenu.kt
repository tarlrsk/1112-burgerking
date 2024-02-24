package com.android.burgerking1112app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.burgerking1112app.BurgerKingMenuActivity
import com.android.burgerking1112app.SignUpActivity
import com.android.burgerking1112app.databinding.FragmentMenuBinding

class FragmentMenu: Fragment() {
    private lateinit var view: FragmentMenuBinding

    // Map UI with fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentMenuBinding.inflate(inflater, container, false)

        view.icBurgerking.setOnClickListener {
            val intent = Intent(requireContext(), BurgerKingMenuActivity::class.java)
            startActivity(intent)
        }
        return view.root
    }

}
