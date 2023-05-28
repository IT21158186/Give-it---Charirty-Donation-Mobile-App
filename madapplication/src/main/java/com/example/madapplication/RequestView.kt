package com.example.madapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madapplication.databinding.ActivityRequestViewBinding
import com.example.madapplication.databinding.ActivityViewOrderBinding

class RequestView : AppCompatActivity() {
    private lateinit var binding: ActivityRequestViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve order data from previous activity
        val rViewName = intent.getStringExtra("Name")
        val rViewOrgName = intent.getStringExtra("Orgname")
        val rViewAddress = intent.getStringExtra("Address")
        val rViewNumber = intent.getStringExtra("Number")
        val rViewDescr = intent.getStringExtra("Descr")

        // Display order data in the UI
        binding.reqNameTextView.text = rViewName
        binding.reqOrgNameTextView.text = rViewOrgName
        binding.reqAddressTextView.text = rViewAddress
        binding.reqNumberTextView.text = rViewNumber
        binding.reqDescrTextView.text = rViewDescr
    }
}