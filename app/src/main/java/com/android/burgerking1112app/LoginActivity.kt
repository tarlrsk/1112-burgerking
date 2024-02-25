package com.android.burgerking1112app

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.burgerking1112app.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val view: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        view.btnSignUpPage.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        auth = Firebase.auth

//        view.btnLogin.setOnClickListener {
//            val email = view.tvSigninEmail.text.toString()
//            val password = view.tvPassword.text.toString()
//            if (email != "" && password != "") {
//                auth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success")
//                            val intent = Intent(this, MainActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.exception)
//                            Toast.makeText(
//                                baseContext,
//                                "Authentication failed.",
//                                Toast.LENGTH_SHORT,
//                            ).show()
//                        }
//                    }
//            }
//
//        }

        val intent = Intent(this, ButtomNavigationActivity::class.java)
        startActivity(intent)

    }
}