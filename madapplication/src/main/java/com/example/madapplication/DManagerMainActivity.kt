package com.example.madapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.madapplication.databinding.ActivityDmanagerMainBinding


class DManagerMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDmanagerMainBinding
    private lateinit var dManagerRedirectBtn:Button

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDmanagerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.mainUpload.setOnClickListener{
            val intent = Intent(this@DManagerMainActivity, upload_delivery_Activity::class.java)
            startActivity(intent)
            finish()
        }

        binding.mainUpdate.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@DManagerMainActivity, update_delivery_Activity::class.java)
            startActivity(intent)
        })
        binding.mainDelete.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@DManagerMainActivity, view_delivery_Activity::class.java)
            startActivity(intent)
        })

        dManagerRedirectBtn = findViewById<Button>(R.id.dManagerRedirectBtn)

        //If press the My Profile button - redirects to the profile page
        dManagerRedirectBtn.setOnClickListener {
            val intent = Intent(this@DManagerMainActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}