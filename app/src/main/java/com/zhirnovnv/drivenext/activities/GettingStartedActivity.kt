package com.zhirnovnv.drivenext.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhirnovnv.drivenext.R
import com.zhirnovnv.drivenext.databinding.ActivityCongratulationBinding
import com.zhirnovnv.drivenext.databinding.ActivityGettingstartedBinding

class GettingStartedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGettingstartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGettingstartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Переход на экран входа
        findViewById<com.google.android.material.button.MaterialButton>(binding.gsSignInBtn.id)
            .setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }

        // Переход на экран регистрации (Signup1)
        findViewById<com.google.android.material.button.MaterialButton>(binding.gsSignUpBtn.id)
            .setOnClickListener {
                startActivity(Intent(this, Signup1Activity::class.java))
            }
    }
}