package com.android.burgerking1112app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.burgerking1112app.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : AppCompatActivity() {
    private val view: ActivityForgetPasswordBinding by lazy { ActivityForgetPasswordBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        view.btnBack.setOnClickListener{
            finish();
        }

        view.btnSubmitEmail.setOnClickListener {
            var email = view.tvResetEmail.text
        }
    }

}