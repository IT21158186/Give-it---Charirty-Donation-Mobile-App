package com.example.madapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.madapplication.databinding.ActivityDeleteMessageBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteMessage : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteMessageBinding
    private lateinit var database : DatabaseReference

    private lateinit var delName : EditText
    private lateinit var btnValidate : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        delName = findViewById(R.id.deleteName)
        btnValidate = findViewById(R.id.deleteBtn)

        binding.deleteBtn.setOnClickListener {

            var name = binding.deleteName.text.toString().trim()

            if (name.isEmpty()) {
                delName.error = "Name Required.."
                return@setOnClickListener
            }else {
                Toast.makeText(this,"Success..",Toast.LENGTH_SHORT).show()
            }

            if (name.isNotEmpty())
                deleteData(name)
            else
                Toast.makeText(this,"Enter the user name", Toast.LENGTH_SHORT).show()

        }
    }

    private fun deleteData(name: String) {

        database = FirebaseDatabase.getInstance().getReference("Messages")

        database.child(name).removeValue().addOnSuccessListener {

            binding.deleteName.text.clear()
            Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {

            Toast.makeText(this,"Fail", Toast.LENGTH_SHORT).show()

        }
    }
}