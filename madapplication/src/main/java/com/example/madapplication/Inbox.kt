package com.example.madapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import com.example.madapplication.databinding.ActivityInboxBinding
import com.google.firebase.database.*

class Inbox : AppCompatActivity() {

    private lateinit var binding: ActivityInboxBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)

        binding = ActivityInboxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewAllMessages()

    }

    private fun viewAllMessages() {

        database = FirebaseDatabase.getInstance().getReference("Messages")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = mutableListOf<ContactusData>()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(ContactusData::class.java)
                    if (message != null) {
                        messages.add(message)
                    }
                }

                // Create a TableLayout and add it to the UI
                val tableLayout = TableLayout(this@Inbox)
                binding.root.addView(tableLayout)

                // Create a TableRow for the column headers
                val headerRow = TableRow(this@Inbox)
                headerRow.addView(createTextView("No."))
                headerRow.addView(createTextView("Email"))
                headerRow.addView(createTextView("Name"))
                headerRow.addView(createTextView("Topic"))
                headerRow.addView(createTextView("Description"))
                tableLayout.addView(headerRow)

                // Create a TableRow for each message
                for ((index, message) in messages.withIndex()) {
                    val tableRow = TableRow(this@Inbox)
                    tableRow.addView(createTextView((index+1).toString()))
                    tableRow.addView(createTextView(message.email))
                    tableRow.addView(createTextView(message.name))
                    tableRow.addView(createTextView(message.topicName))
                    tableRow.addView(createTextView(message.desc))
                    tableLayout.addView(tableRow)

                    // Add click listener to TableRow
                    tableRow.setOnClickListener {
                        // Create Intent to navigate to new activity
                        val intent = Intent(this@Inbox, ViewMessage::class.java)
                        intent.putExtra("messageName", message.email)
                        intent.putExtra("messageAddress", message.name)
                        intent.putExtra("messageNumber", message.topicName)
                        intent.putExtra("messageWeight", message.desc)
                        startActivity(intent)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Inbox, "Failed to retrieve messages", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Helper function to create a TextView with the given text
    private fun createTextView(text: String?): TextView {
        val textView = TextView(this@Inbox)
        textView.text = text
        textView.setPadding(16, 16, 16, 16)
        return textView
    }
}
