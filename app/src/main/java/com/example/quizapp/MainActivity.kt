package com.example.quizapp


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2500)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        var btnStart: Button = findViewById(R.id.btnStart)
        var et_name: EditText = findViewById(R.id.et_name)


        btnStart.setOnClickListener {
            if (et_name.text.isEmpty()) {
                Toast.makeText(
                    this, "Please Enter Your Name !!",
                    Toast.LENGTH_SHORT
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