package com.example.madapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.madapplication.databinding.ActivityDmanagerMainBinding
import com.example.madapplication.databinding.ActivityRmanagerMainBinding

class RManagerMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRmanagerMainBinding
    private lateinit var rManagerRedirectBtn:Button


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRmanagerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.requestUpload.setOnClickListener{
            val intent = Intent(this@RManagerMainActivity, RequestUpload::class.java)
            startActivity(intent)
            finish()
        }

        binding.requestUpdate.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@RManagerMainActivity, RequestUpdate::class.java)
            startActivity(intent)
        })
        binding.requestDelete.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@RManagerMainActivity, RequestAllView::class.java)
            startActivity(intent)
        })

        rManagerRedirectBtn = findViewById<Button>(R.id.rManagerRedirectBtn)

        //If press the My Profile button - redirects to the profile page
        rManagerRedirectBtn.setOnClickListener {
            val intent = Intent(this@RManagerMainActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}