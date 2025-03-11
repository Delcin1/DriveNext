package com.zhirnovnv.drivenext.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhirnovnv.drivenext.utils.NetworkUtils

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!NetworkUtils.isInternetAvailable(this)) {
            val intent = Intent(this, NoInternetActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}