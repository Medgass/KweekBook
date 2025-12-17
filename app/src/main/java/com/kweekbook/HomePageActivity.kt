package com.kweekbook

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import android.widget.ImageView
import androidx.core.content.ContextCompat

class HomePageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val enterButton = findViewById<MaterialButton>(R.id.enterButton)
        enterButton?.setOnClickListener {
            try {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        
    }
}
