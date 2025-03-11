package com.zhirnovnv.drivenext.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.zhirnovnv.drivenext.R

class OnboardingActivity : BaseActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var nextButton: Button
    private lateinit var skipTextView: TextView
    private lateinit var indicators: Array<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPager)
        nextButton = findViewById(R.id.nextButton)
        skipTextView = findViewById(R.id.skipTextView)
        indicators = arrayOf(findViewById(R.id.indicator1), findViewById(R.id.indicator2), findViewById(R.id.indicator3))

        val onboardingAdapter = OnboardingAdapter(this)
        viewPager.adapter = onboardingAdapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateIndicators(position)
                if (position == onboardingAdapter.itemCount - 1) {
                    nextButton.text = getString(R.string.lets_go)
                } else {
                    nextButton.text = getString(R.string.next)
                }
            }
        })

        nextButton.setOnClickListener {
            if (viewPager.currentItem < onboardingAdapter.itemCount - 1) {
                viewPager.currentItem += 1
            } else {
                navigateToMainActivity()
            }
        }

        skipTextView.setOnClickListener {
            navigateToMainActivity()
        }
    }

    private fun updateIndicators(position: Int) {
        for (i in indicators.indices) {
            indicators[i].setBackgroundResource(if (i == position) R.drawable.indicator_active else R.drawable.indicator_inactive)
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}