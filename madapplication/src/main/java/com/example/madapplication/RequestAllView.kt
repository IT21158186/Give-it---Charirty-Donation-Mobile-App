package com.example.madapplication

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.madapplication.databinding.ActivityRequestAllViewBinding
import com.google.firebase.database.*

class RequestAllView : AppCompatActivity() {
    private lateinit var binding: ActivityRequestAllViewBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestAllViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewAllRequest()
    }

    private fun viewAllRequest() {
        database = FirebaseDatabase.getInstance().getReference("Request")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val requests = mutableListOf<Map<String, Any>>()
                for (requestSnapshot in snapshot.children) {
                    val request = requestSnapshot.value as? Map<String, Any>
                    if (request != null) {
                        requests.add(request)
                    }
                }

                val adapter = ArrayAdapter<Map<String, Any>>(
                    this@RequestAllView,
                    R.layout.simple_list_item_1,
                    requests
                )
                binding.listView.adapter = adapter

                // Add click listener to listView items
                binding.listView.setOnItemClickListener { _, _, position, _ ->
                    val selectedRequest = requests[position]
                    val rViewName = selectedRequest["name"] as String
                    val rViewOrgName = selectedRequest["orgname"] as String
                    val rViewAddress = selectedRequest["address"] as String
                    val rViewNumber = selectedRequest["number"] as String
                    val rViewDescr = selectedRequest["descr"] as String

                    // Create Intent to navigate to new activity
                    val intent = Intent(this@RequestAllView, RequestView::class.java)
                    intent.putExtra("Name", rViewName)
                    intent.putExtra("OrgName", rViewOrgName)
                    intent.putExtra("Address", rViewAddress)
                    intent.putExtra("Number", rViewNumber)
                    intent.putExtra("Descr", rViewDescr)
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@RequestAllView, "Failed to retrieve orders", Toast.LENGTH_SHORT).show()
            }
        })
    }
}