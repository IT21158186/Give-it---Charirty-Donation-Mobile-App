package com.example.madapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.madapplication.databinding.ActivityUpload2Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class upload_delivery_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityUpload2Binding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpload2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.saveButton.setOnClickListener {

            val name = binding.uploadName.text.toString()
            val address = binding.uploadAddress.text.toString()
            val number = binding.uploadPhone.text.toString()
            val weight = binding.uploadWeight.text.toString()

            if (name.isEmpty() || address.isEmpty() || number.isEmpty() || weight.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (number.length != 10 || !number.all { it.isDigit() }) {
                Toast.makeText(this, "Please enter a valid 10-digit number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weightValue = weight.toDoubleOrNull()
            if (weightValue == null) {
                Toast.makeText(this, "Please enter a valid weight", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (weightValue >= 10) {
                Toast.makeText(this, "Weight should be less than 10 Kg", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            database = FirebaseDatabase.getInstance().getReference("Orders")

            // Check if the number already exists in the database
            database.child(number).get().addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    Toast.makeText(this, "Order already exists. Please update it.", Toast.LENGTH_SHORT).show()
                } else {
                    val users = DeliveryData(name, address, number, weight)
                    database.child(number).setValue(users).addOnSuccessListener {

                        binding.uploadName.text.clear()
                        binding.uploadAddress.text.clear()
                        binding.uploadPhone.text.clear()
                        binding.uploadWeight.text.clear()

                        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@upload_delivery_Activity, DManagerMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to access database", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Handle the back button press in the taskbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
