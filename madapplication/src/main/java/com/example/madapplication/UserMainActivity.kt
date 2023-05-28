package com.example.madapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.madapplication.databinding.ActivityUserMainBinding

class UserMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainSend.setOnClickListener{
            val intent = Intent(this@UserMainActivity, ContactUsActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.mainUpdate.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@UserMainActivity, UpdateMessage::class.java)
            startActivity(intent)
        })
        binding.mainDelete.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@UserMainActivity, DeleteMessage::class.java)
            startActivity(intent)
        })
        binding.mainInbox.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@UserMainActivity, Inbox::class.java)
            startActivity(intent)
        })

        val userRedirectBtn = findViewById<Button>(R.id.userRedirectBtn)

        userRedirectBtn.setOnClickListener{

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}