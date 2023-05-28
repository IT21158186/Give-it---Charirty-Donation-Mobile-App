package com.example.madapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madapplication.databinding.ActivityViewOrderBinding

class ViewOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve order data from previous activity
        val orderName = intent.getStringExtra("orderName")
        val orderAddress = intent.getStringExtra("orderAddress")
        val orderNumber = intent.getStringExtra("orderNumber")
        val orderWeight = intent.getStringExtra("orderWeight")

        // Display order data in the UI
        binding.orderNameTextView.text = orderName
        binding.orderAddressTextView.text = orderAddress
        binding.orderNumberTextView.text = orderNumber
        binding.orderWeightTextView.text = orderWeight
    }
}
