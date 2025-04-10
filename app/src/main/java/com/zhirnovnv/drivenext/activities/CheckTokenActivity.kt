package com.zhirnovnv.drivenext.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhirnovnv.drivenext.database.AppDatabase
import com.zhirnovnv.drivenext.utils.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class CheckTokenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val savedToken = prefs.getString("user_token", null)
        if (savedToken == null) {
            val intent = Intent(this@CheckTokenActivity, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
            return
        }
        if (savedToken != "test_token") {
            val intent = Intent(this@CheckTokenActivity, GettingStartedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}