package com.example.madapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.madapplication.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityProfileBinding


    //ActionBar
    private lateinit var actionBar: ActionBar

    //firebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, logout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

        binding.deleteAccBtn.setOnClickListener{
            val user = Firebase.auth.currentUser
            user?.delete()?.addOnCompleteListener{
                if(it.isSuccessful){
                    //account Already deleted
                    Toast.makeText(this,"Account successfully Deleted!", Toast.LENGTH_SHORT).show()
                    //since account is already been deleted we cannot sign  out
                    //so we just start an activity
                    val intent = Intent(this, newLoginActivity::class.java)
                    startActivity(intent)
                    //destroy this activity
                    finish()
                }else{
                    //catch error
                    Log.e("error: ", it.exception.toString())
                }
            }
        }
    }

    private fun checkUser() {
        //check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //user not null, user is logged in, get user info
            val email = firebaseUser.email
            //set to text view
            binding.signupEmail.text = email
        }
        else{
            //user is null, user is not logged in, go to login activity
            startActivity(Intent(this, newLoginActivity::class.java))
            finish()
        }
    }
}