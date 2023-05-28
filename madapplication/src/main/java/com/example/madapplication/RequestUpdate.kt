package com.example.madapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.madapplication.databinding.ActivityRequestUpdateBinding
import com.example.madapplication.databinding.ActivityUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RequestUpdate : AppCompatActivity() {
    private lateinit var binding: ActivityRequestUpdateBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.reqDeleteButton.setOnClickListener {
            val rReferencePhone = binding.reqReferencePhone.text.toString()
            if (rReferencePhone.isNotBlank()) {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("Delete Request")
                alertDialogBuilder.setMessage("Are you sure you want to delete this request?")
                alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                    deleteData(rReferencePhone)
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


        binding.reqSearchButton.setOnClickListener {
            val rReferencePhone = binding.reqReferencePhone.text.toString()
            searchRequest(rReferencePhone)
        }

        binding.reqUpdateButton.setOnClickListener {
            val rReferencePhone = binding.reqReferencePhone.text.toString()
            val rUpdateName = binding.reqUpdateName.text.toString()
            val rUpdateOrgName = binding.reqUpdateOrgName.text.toString()
            val rUpdateAddress = binding.reqUpdateAddress.text.toString()
            val rUpdateDescr = binding.reqUpdateDescr.text.toString()
            updateData(rReferencePhone,rUpdateName,rUpdateOrgName,rUpdateAddress,rUpdateDescr)
        }
    }
    private fun deleteData(number: String) {
        database = FirebaseDatabase.getInstance().getReference("Request")
        database.child(number).removeValue().addOnSuccessListener {
            binding.reqReferencePhone.text.clear()
            binding.reqUpdateName.text.clear()
            binding.reqUpdateOrgName.text.clear()
            binding.reqUpdateAddress.text.clear()
            binding.reqUpdateDescr.text.clear()
            Toast.makeText(this,"Successfully deleted request", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to delete request", Toast.LENGTH_SHORT).show()
        }
    }

    private fun searchRequest(number: String) {
        if (number.isBlank()) {
            Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show()
            return
        }

        if (number.length != 10) {
            Toast.makeText(this, "Number should contain 10 digits", Toast.LENGTH_SHORT).show()
            return
        }

        database = FirebaseDatabase.getInstance().getReference("Request")
        database.child(number).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val request = snapshot.value as? Map<String, Any>
                binding.reqUpdateName.setText(request?.get("name").toString())
                binding.reqUpdateOrgName.setText(request?.get("orgname").toString())
                binding.reqUpdateAddress.setText(request?.get("address").toString())
                binding.reqUpdateDescr.setText(request?.get("descr").toString())
                Toast.makeText(this, "Request found", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Request not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to search for Request", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateData(number: String, name: String, orgname: String,address: String, descr: String) {
        if (number.isBlank() || name.isBlank() ||orgname.isBlank()|| address.isBlank() || descr.isBlank()) {
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            return
        }

        if (number.length != 10) {
            Toast.makeText(this, "Number should contain 10 digits", Toast.LENGTH_SHORT).show()
            return
        }

        database = FirebaseDatabase.getInstance().getReference("Request")
        database.child(number).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val user = mapOf<String,String>(
                    "name" to name,
                    "orgname" to orgname,
                    "address" to address,
                    "descr" to descr
        )
            database.child(number).updateChildren(user).addOnSuccessListener {
                clearFields()
                Toast.makeText(this, "Successfully updated Request", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to update Request", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Request not found", Toast.LENGTH_SHORT).show()
        }
    }.addOnFailureListener {
        Toast.makeText(this, "Failed to search for Request", Toast.LENGTH_SHORT).show()
    }
    }

         private fun clearFields() {
             binding.reqReferencePhone.text.clear()
             binding.reqUpdateName.text.clear()
             binding.reqUpdateOrgName.text.clear()
             binding.reqUpdateAddress.text.clear()
             binding.reqUpdateDescr.text.clear()
        }
    }

