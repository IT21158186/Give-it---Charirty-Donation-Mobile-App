package com.example.madapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.madapplication.databinding.ActivityUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class update_delivery_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.deleteButton.setOnClickListener {
            val referencePhone = binding.referencePhone.text.toString()
            if (referencePhone.isNotBlank()) {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("Delete Order")
                alertDialogBuilder.setMessage("Are you sure you want to delete this order?")
                alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                    deleteData(referencePhone)
                }
                alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            } else {
                Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show()
            }
        }


        binding.searchButton.setOnClickListener {
            val referencePhone = binding.referencePhone.text.toString()
            searchOrder(referencePhone)
        }

        binding.updateButton.setOnClickListener {
            val referencePhone = binding.referencePhone.text.toString()
            val updateName = binding.updateName.text.toString()
            val updateAddress = binding.updateAddress.text.toString()
            val updateWeight = binding.updateWeight.text.toString()
            updateData(referencePhone,updateName,updateAddress,updateWeight)
        }
    }
    private fun deleteData(number: String) {
        database = FirebaseDatabase.getInstance().getReference("Orders")
        database.child(number).removeValue().addOnSuccessListener {
            binding.referencePhone.text.clear()
            binding.updateName.text.clear()
            binding.updateAddress.text.clear()
            binding.updateWeight.text.clear()
            Toast.makeText(this,"Successfully deleted order",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to delete order",Toast.LENGTH_SHORT).show()
        }
    }

    private fun searchOrder(number: String) {
        if (number.isBlank()) {
            Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show()
            return
        }

        if (number.length != 10) {
            Toast.makeText(this, "Number should contain 10 digits", Toast.LENGTH_SHORT).show()
            return
        }

        database = FirebaseDatabase.getInstance().getReference("Orders")
        database.child(number).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val order = snapshot.value as? Map<String, Any>
                binding.updateName.setText(order?.get("name").toString())
                binding.updateAddress.setText(order?.get("address").toString())
                binding.updateWeight.setText(order?.get("weight").toString())
                Toast.makeText(this, "Order found", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Order not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to search for order",Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateData(number: String, name: String, address: String, weight: String) {
        if (number.isBlank() || name.isBlank() || address.isBlank() || weight.isBlank()) {
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            return
        }

        if (number.length != 10) {
            Toast.makeText(this, "Number should contain 10 digits", Toast.LENGTH_SHORT).show()
            return
        }

        database = FirebaseDatabase.getInstance().getReference("Orders")
        database.child(number).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val user = mapOf<String, String>(
                    "name" to name,
                    "address" to address,
                    "weight" to weight
                )
                database.child(number).updateChildren(user).addOnSuccessListener {
                    clearFields()
                    Toast.makeText(this, "Successfully updated order", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to update order", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Order not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to search for order", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        binding.referencePhone.text.clear()
        binding.updateName.text.clear()
        binding.updateAddress.text.clear()
        binding.updateWeight.text.clear()
    }
}