package com.example.madapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madapplication.databinding.ActivityRequestUploadBinding
import com.example.madapplication.databinding.ActivityUpload2Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RequestUpload : AppCompatActivity() {
    private lateinit var binding: ActivityRequestUploadBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.reqSaveButton.setOnClickListener {

            val name = binding.reqName.text.toString()
            val orgname = binding.reqOrgName.text.toString()
            val address = binding.reqAddress.text.toString()
            val number = binding.reqPhone.text.toString()
            val descr = binding.reqDescr.text.toString()

            if (name.isEmpty() ||orgname.isEmpty()|| address.isEmpty() || number.isEmpty() || descr.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (number.length != 10 || !number.all { it.isDigit() }) {
                Toast.makeText(this, "Please enter a valid 10-digit number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            database = FirebaseDatabase.getInstance().getReference("Request")
            database.child(number).get().addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    Toast.makeText(this, "Request already exists. Please update it.", Toast.LENGTH_SHORT).show()
                } else {
            val users = UserData2(name,orgname,address,number,descr)
            database.child(number).setValue(users).addOnSuccessListener {

                binding.reqName.text.clear()
                binding.reqOrgName.text.clear()
                binding.reqAddress.text.clear()
                binding.reqPhone.text.clear()
                binding.reqDescr.text.clear()

                Toast.makeText(this,"Saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@RequestUpload, RManagerMainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }.addOnFailureListener {
                Toast.makeText(this, "Failed to access database", Toast.LENGTH_SHORT).show()
            }
        }
    }
}