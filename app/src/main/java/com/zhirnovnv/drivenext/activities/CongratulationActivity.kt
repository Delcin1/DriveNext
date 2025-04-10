package com.zhirnovnv.drivenext.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhirnovnv.drivenext.R
import com.zhirnovnv.drivenext.databinding.ActivityCongratulationBinding

class CongratulationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCongratulationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCongratulationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<com.google.android.material.button.MaterialButton>(R.id.gratsNextButton)
            .setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }
}