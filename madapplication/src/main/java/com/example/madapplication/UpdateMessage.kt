package com.example.madapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.madapplication.databinding.ActivityUpdateMessageBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateMessage : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateMessageBinding
    private lateinit var database : DatabaseReference

    private lateinit var upEmail : EditText
    private lateinit var upName : EditText
    private lateinit var upTopic : EditText
    private lateinit var upDesc : EditText
    private lateinit var btnValidate : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        upEmail = findViewById(R.id.updateEmail)
        upName = findViewById(R.id.updateName)
        upTopic = findViewById(R.id.updateTopic)
        upDesc = findViewById(R.id.updateDesc)
        btnValidate = findViewById(R.id.updateBtn)

        upEmail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(upEmail.text.toString()).matches())
                    btnValidate.isEnabled = true
                else {
                    btnValidate.isEnabled = false
                    upEmail.setError("Invalid Email")
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.updateBtn.setOnClickListener {

            val updateEmail = binding.updateEmail.text.toString().trim()
            val updateName = binding.updateName.text.toString().trim()
            val updateTopic = binding.updateTopic.text.toString().trim()
            val updateDesc = binding.updateDesc.text.toString().trim()

            if (updateEmail.isEmpty()) {
                upEmail.error = "Email Required.."
                return@setOnClickListener
            }else if (updateName.isEmpty()) {
                upName.error = "Name Required.."
                return@setOnClickListener
            }else if (updateTopic.isEmpty()) {
                upTopic.error = "Topic Required.."
                return@setOnClickListener
            }else if (updateDesc.isEmpty()) {
                upDesc.error = "Description Required.."
                return@setOnClickListener
            }else {
            }

            updateData(updateEmail,updateName,updateTopic,updateDesc)

        }

    }

    private fun updateData(updateEmail: String, updateName: String, updateTopic: String, updateDesc: String) {

        database = FirebaseDatabase.getInstance().getReference("Messages")
        database.child(updateName).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val user = mapOf<String, String>(
                    "email" to updateEmail,
                    "topicName" to updateTopic,
                    "desc" to updateDesc
                )
                database.child(updateName).updateChildren(user).addOnSuccessListener {
                    clearFields()
                    Toast.makeText(this, "Successfully updated Message", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to update message", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Name not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to search for message", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        binding.updateEmail.text.clear()
        binding.updateName.text.clear()
        binding.updateTopic.text.clear()
        binding.updateDesc.text.clear()
    }
}