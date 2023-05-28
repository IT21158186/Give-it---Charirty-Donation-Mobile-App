package com.example.madapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.madapplication.databinding.ActivityNewSignupBinding
import com.google.firebase.auth.FirebaseAuth

class newSignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewSignupBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener{
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val confirmPassword = binding.signupConfirm.text.toString()

            if (isEmailValid(email) && isPasswordValid(password) && isConfirmPasswordValid(password, confirmPassword)){
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this, newLoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.loginRedirectText.setOnClickListener {
            val loginIntent = Intent(this, newLoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.signupEmail.error = "Email cannot be empty!"
            false
        } else if (!email.contains("@")) {
            binding.signupEmail.error = "Invalid email address!"
            false
        } else {
            binding.signupEmail.error = null
            true
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return if (password.isEmpty()) {
            binding.signupPassword.error = "Password cannot be empty!"
            false
        } else if (password.length < 6) {
            binding.signupPassword.error = "Password must contain at least 6 characters!"
            false
        } else {
            binding.signupPassword.error = null
            true
        }
    }

    private fun isConfirmPasswordValid(password: String, confirmPassword: String): Boolean {
        return if (confirmPassword.isEmpty()) {
            binding.signupConfirm.error = "Confirm password cannot be empty!"
            false
        } else if (confirmPassword != password) {
            binding.signupConfirm.error = "Password and confirm password do not match!"
            false
        } else {
            binding.signupConfirm.error = null
            true
        }
    }
}