package com.kweekbook

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import android.widget.ImageView

class HomePageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            setContentView(R.layout.activity_home_page)

            // Animations
            val logoImage = findViewById<ImageView>(R.id.logoImage)
            val glowEffect = findViewById<android.view.View>(R.id.glowEffect)
            val enterButton = findViewById<MaterialButton>(R.id.enterButton)

        // Apply entry animation to logo
        val logoEntryAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_entry)
        logoImage.startAnimation(logoEntryAnimation)

        // Apply bounce animation to logo after entry (delayed)
        logoImage.postDelayed({
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            logoImage.startAnimation(bounceAnimation)
        }, 1000)

        // Apply pulse animation to glow
        val pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse)
        glowEffect.startAnimation(pulseAnimation)
        
        // Apply button entry animation
        val buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_entry)
        enterButton.startAnimation(buttonAnimation)

        // Enter button click
        enterButton.setOnClickListener {
            // Check if user is logged in
            val sharedPrefs = getSharedPreferences("KweekBookPrefs", MODE_PRIVATE)
            val isLoggedIn = sharedPrefs.getBoolean("is_logged_in", false)

            val intent = if (isLoggedIn) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
        } catch (e: Exception) {
            e.printStackTrace()
            // Si erreur, aller directement à LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
