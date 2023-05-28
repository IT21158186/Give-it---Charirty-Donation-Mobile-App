package com.example.madapplication


import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.madapplication.databinding.ActivityNewLoginBinding
import com.google.firebase.auth.FirebaseAuth

class newLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Handle Click, begin Login
        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (isEmailValid(email) && isPasswordValid(password)) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        when {
                            email == "d.manager223@gmail.com" -> {
                                val intent = Intent(this, DManagerMainActivity::class.java)
                                startActivity(intent)
                            }
                            email == "r.manager223@gmail.com" -> {
                                val intent = Intent(this, RManagerMainActivity::class.java)
                                startActivity(intent)
                            }
                            else -> {
                                val intent = Intent(this, UserDashBoard::class.java)
                                startActivity(intent)
                            }
                        }
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        //forget password
        binding.forgotPassword.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_forgot,null)
            val userEmail = view.findViewById<EditText>(R.id.editBox)

            builder.setView(view)
            val dialog = builder.create()

            view.findViewById<Button>(R.id.btnReset).setOnClickListener {
                compareEmail(userEmail)
                dialog.dismiss()
            }
            view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                dialog.dismiss()
            }
            if (dialog.window !=null){
                dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            dialog.show()
        }

        binding.signupRedirectText.setOnClickListener{
            val signupIntent = Intent(this, newSignupActivity::class.java)
            startActivity(signupIntent)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.loginEmail.error = "Email cannot be empty!"
            false
        } else if (!email.contains("@")) {
            binding.loginEmail.error = "Invalid email address!"
            false
        } else {
            binding.loginEmail.error = null
            true
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return if (password.isEmpty()) {
            binding.loginPassword.error = "Password cannot be empty!"
            false
        } else if (password.length < 6) {
            binding.loginPassword.error = "Password must contain at least 6 characters!"
            false
        } else {
            binding.loginPassword.error = null
            true
        }
    }

    private fun checkUser() {
        //if user is already logged in go to profile activity
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //user is already logged in
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }

    //Outside onCreate
    private fun compareEmail(email: EditText){
        if(email.text.toString().isEmpty()){
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            return
        }
        firebaseAuth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener { task->
            if (task.isSuccessful){
                Toast.makeText(this,"Check your Email", Toast.LENGTH_SHORT).show()
            }
        }
    }
}