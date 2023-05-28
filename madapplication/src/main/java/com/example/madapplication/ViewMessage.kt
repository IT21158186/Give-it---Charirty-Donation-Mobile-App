package com.example.madapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.madapplication.databinding.ActivityViewMessageBinding
import com.google.android.material.appbar.MaterialToolbar

class ViewMessage : AppCompatActivity() {

    private lateinit var binding: ActivityViewMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve order data from previous activity
        val email = intent.getStringExtra("orderName")
        val name = intent.getStringExtra("orderAddress")
        val topicName = intent.getStringExtra("orderNumber")
        val desc = intent.getStringExtra("orderWeight")

        // Display order data in the UI
        binding.msgEmailTextView.text = email
        binding.msgNameTextView.text = name
        binding.msgTopicTextView.text = topicName
        binding.msgDescTextView.text = desc
    }
}