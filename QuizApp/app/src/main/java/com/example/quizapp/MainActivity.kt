package com.example.quizapp



import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnStart: Button = findViewById(R.id.btnStart)
        var et_name: EditText = findViewById(R.id.et_name)


        btnStart.setOnClickListener {
            if (et_name.text.isEmpty()) {
                Toast.makeText(
                    this, "Please Enter Your Name !!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, et_name.text.toString())
                startActivity(intent)
                finish()

            }
        }
    }
}