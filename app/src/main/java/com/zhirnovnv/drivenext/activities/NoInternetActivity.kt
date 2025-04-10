package com.zhirnovnv.drivenext.activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.zhirnovnv.drivenext.R
import com.zhirnovnv.drivenext.databinding.ActivityLoginBinding
import com.zhirnovnv.drivenext.databinding.ActivityMainBinding
import com.zhirnovnv.drivenext.databinding.NoInternetLayoutBinding

class NoInternetActivity : AppCompatActivity() {

    private lateinit var binding: NoInternetLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NoInternetLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retryButton: Button = findViewById(binding.retryButton.id)
        retryButton.setOnClickListener {
            if (isInternetAvailable(this)) {
                finish()
            }
        }
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetwork?.let { network ->
            connectivityManager.getNetworkCapabilities(network)?.let { capabilities ->
                return when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
        return false
    }
}