package com.android.burgerking1112app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.burgerking1112app.LoginActivity
import com.android.burgerking1112app.databinding.FragmentMoreBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FragmentMore: Fragment() {
    private lateinit var view: FragmentMoreBinding

    private lateinit var auth: FirebaseAuth
    // Map UI with fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentMoreBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        view.btnSignout.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        return view.root

    }


}