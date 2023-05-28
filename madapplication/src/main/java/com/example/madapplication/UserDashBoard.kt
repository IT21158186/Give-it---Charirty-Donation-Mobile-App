package com.example.madapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.madapplication.databinding.ActivityUserDashBoardBinding

class UserDashBoard : AppCompatActivity() {

    private lateinit var binding: ActivityUserDashBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileBtn.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@UserDashBoard, ProfileActivity::class.java)
            startActivity(intent)
        })

        binding.d1.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@UserDashBoard, UserMainActivity::class.java)
            startActivity(intent)
        })

        binding.d2.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@UserDashBoard, RManagerMainActivity::class.java)
            startActivity(intent)
        })

        binding.d3.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@UserDashBoard, UserMainActivity::class.java)
            startActivity(intent)
        })

        binding.d4.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@UserDashBoard, UserMainActivity::class.java)
            startActivity(intent)
        })

    }
}