package com.android.burgerking1112app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.burgerking1112app.databinding.ActivityForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val view: ActivityForgetPasswordBinding by lazy { ActivityForgetPasswordBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        view.btnBack.setOnClickListener{
            finish();
        }

        auth = Firebase.auth

        view.btnSubmitEmail.setOnClickListener {
            var email = view.tvResetEmail.text.toString()

            if (email != ""){
                auth.sendPasswordResetEmail(email).addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        Toast.makeText(
                            this,
                            "Email sent successfully to reset your password",
                            Toast.LENGTH_SHORT,
                        )
                        finish()
                    }
                }
            }
        }
    }

}