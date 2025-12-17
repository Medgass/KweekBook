package com.kweekbook

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private var isSignUpMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Set logo
        val logoImage = findViewById<ImageView>(R.id.logoImage)
        logoImage?.setImageResource(R.drawable.logo_kweekbook)

        val nameInputLayout = findViewById<TextInputLayout>(R.id.nameInputLayout)
        val nameInput = findViewById<TextInputEditText>(R.id.nameInput)
        val emailInput = findViewById<TextInputEditText>(R.id.emailInput)
        val passwordInput = findViewById<TextInputEditText>(R.id.passwordInput)
        val loginButton = findViewById<MaterialButton>(R.id.loginButton)
        val toggleAuthText = findViewById<TextView>(R.id.toggleAuthText)
        val loginSubtitle = findViewById<TextView>(R.id.loginSubtitle)

        // Toggle between login and sign up
        toggleAuthText.setOnClickListener {
            isSignUpMode = !isSignUpMode
            updateUIForMode()
        }

        // Login/Sign Up button
        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val name = nameInput.text.toString().trim()

            // Validation
            var hasError = false
            
            if (email.isEmpty()) {
                emailInput.error = "Email requis"
                hasError = true
            }
            
            if (password.isEmpty()) {
                passwordInput.error = "Mot de passe requis"
                hasError = true
            }
            
            if (isSignUpMode && name.isEmpty()) {
                nameInput.error = "Nom requis"
                hasError = true
            }
            
            if (hasError) return@setOnClickListener

            // Save user info
            val sharedPrefs = getSharedPreferences("KweekBookPrefs", MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putBoolean("is_logged_in", true)
            editor.putString("user_name", if (isSignUpMode) name else email.substringBefore("@"))
            editor.putString("user_email", email)
            editor.apply()

            // Navigate to main activity
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }

    private fun updateUIForMode() {
        val nameInputLayout = findViewById<TextInputLayout>(R.id.nameInputLayout)
        val loginButton = findViewById<MaterialButton>(R.id.loginButton)
        val toggleAuthText = findViewById<TextView>(R.id.toggleAuthText)
        val loginSubtitle = findViewById<TextView>(R.id.loginSubtitle)

        if (isSignUpMode) {
            nameInputLayout.visibility = View.VISIBLE
            loginButton.text = getString(R.string.sign_up)
            toggleAuthText.text = getString(R.string.have_account_login)
            loginSubtitle.text = getString(R.string.signup_subtitle)
        } else {
            nameInputLayout.visibility = View.GONE
            loginButton.text = getString(R.string.login)
            toggleAuthText.text = getString(R.string.no_account_sign_up)
            loginSubtitle.text = getString(R.string.login_subtitle)
        }
    }
}
