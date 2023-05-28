package com.example.madapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.madapplication.databinding.ActivityViewDeliveryBinding
import com.google.firebase.database.*

class view_delivery_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityViewDeliveryBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewDeliveryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enable the back button on the taskbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewAllOrders()
    }

    override fun onSupportNavigateUp(): Boolean {
        // Navigate back to the previous activity when the back button is pressed
        onBackPressed()
        return true
    }

    private fun viewAllOrders() {
        database = FirebaseDatabase.getInstance().getReference("Orders")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orders = mutableListOf<DeliveryData>()
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(DeliveryData::class.java)
                    if (order != null) {
                        orders.add(order)
                    }
                }

                // Create a TableLayout and add it to the UI
                val tableLayout = TableLayout(this@view_delivery_Activity)
                binding.root.addView(tableLayout)

                // Create a TableRow for the column headers
                val headerRow = TableRow(this@view_delivery_Activity)
                headerRow.addView(createTextView("No."))
                headerRow.addView(createTextView("Name"))
                headerRow.addView(createTextView("Address"))
                headerRow.addView(createTextView("Number"))
                headerRow.addView(createTextView("Weight"))
                tableLayout.addView(headerRow)

                // Create a TableRow for each order
                for ((index, order) in orders.withIndex()) {
                    val tableRow = TableRow(this@view_delivery_Activity)
                    tableRow.addView(createTextView((index+1).toString()))
                    tableRow.addView(createTextView(order.name))
                    tableRow.addView(createTextView(order.address))
                    tableRow.addView(createTextView(order.number))
                    tableRow.addView(createTextView(order.weight))
                    tableLayout.addView(tableRow)

                    // Add click listener to TableRow
                    tableRow.setOnClickListener {
                        // Create Intent to navigate to new activity
                        val intent = Intent(this@view_delivery_Activity, ViewOrderActivity::class.java)
                        intent.putExtra("orderName", order.name)
                        intent.putExtra("orderAddress", order.address)
                        intent.putExtra("orderNumber", order.number)
                        intent.putExtra("orderWeight", order.weight)
                        startActivity(intent)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@view_delivery_Activity, "Failed to retrieve orders", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Helper function to create a TextView with the given text
    private fun createTextView(text: String?): TextView {
        val textView = TextView(this@view_delivery_Activity)
        textView.text = text
        textView.setPadding(16, 16, 16, 16)
        return textView
    }
}
