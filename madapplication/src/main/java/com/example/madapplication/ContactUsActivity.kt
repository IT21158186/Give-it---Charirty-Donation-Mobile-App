package com.example.madapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.madapplication.databinding.ActivityContactusBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ContactUsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactusBinding
    private lateinit var database: DatabaseReference

    private lateinit var uploadEmail : EditText
    private lateinit var uploadName : EditText
    private lateinit var uploadTopic : EditText
    private lateinit var uploadDesc : EditText
    private lateinit var btnValidate : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uploadEmail = findViewById(R.id.uploadEmail)
        uploadName = findViewById(R.id.uploadName)
        uploadTopic = findViewById(R.id.uploadTopic)
        uploadDesc = findViewById(R.id.uploadDesc)
        btnValidate = findViewById(R.id.sendButton)

        uploadEmail.addTextChangedListener(object:TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(uploadEmail.text.toString()).matches())
                    btnValidate.isEnabled = true
                else {
                    btnValidate.isEnabled = false
                    uploadEmail.setError("Invalid Email")
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.sendButton.setOnClickListener {

            val email = binding.uploadEmail.text.toString().trim()
            val name = binding.uploadName.text.toString().trim()
            val topicName = binding.uploadTopic.text.toString().trim()
            val desc = binding.uploadDesc.text.toString().trim()

            if (email.isEmpty()) {
                uploadEmail.error = "Email Required.."
                return@setOnClickListener
            }else if (name.isEmpty()) {
                uploadName.error = "Name Required.."
                return@setOnClickListener
            }else if (topicName.isEmpty()) {
                uploadTopic.error = "Topic Required.."
                return@setOnClickListener
            }else if (desc.isEmpty()) {
                uploadDesc.error = "Description Required.."
                return@setOnClickListener
            }else {
                Toast.makeText(this,"Success..",Toast.LENGTH_SHORT).show()
            }

            database = FirebaseDatabase.getInstance().getReference("Messages")
            val users = ContactusData(email,name,topicName,desc)
            database.child(name).setValue(users).addOnSuccessListener {

                binding.uploadEmail.text.clear()
                binding.uploadName.text.clear()
                binding.uploadTopic.text.clear()
                binding.uploadDesc.text.clear()

                Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ContactUsActivity, UserMainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}