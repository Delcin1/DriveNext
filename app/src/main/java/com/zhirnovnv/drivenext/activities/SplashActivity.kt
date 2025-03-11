package com.zhirnovnv.drivenext.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.zhirnovnv.drivenext.databinding.SplashScreenLayoutBinding
import com.zhirnovnv.drivenext.utils.NetworkUtils

class SplashActivity : BaseActivity() {

    private lateinit var binding: SplashScreenLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.
    }
}