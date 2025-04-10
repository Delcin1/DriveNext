package com.zhirnovnv.drivenext.activities

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.zhirnovnv.drivenext.R

const val defaultIndicatorSize = 8

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

        updateIndicators(0)

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
                navigateToGetStartedActivity()
            }
        }

        skipTextView.setOnClickListener {
            navigateToGetStartedActivity()
        }
    }

    private fun updateIndicators(position: Int) {
        val activeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            40f,
            resources.displayMetrics
        ).toInt()
        val inactiveWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            16f,
            resources.displayMetrics
        ).toInt()

        for (i in indicators.indices) {
            val layoutParams = indicators[i].layoutParams
            layoutParams.width = if (i == position) activeWidth else inactiveWidth
            indicators[i].layoutParams = layoutParams
            indicators[i].setBackgroundResource(if (i == position) R.drawable.indicator_active else R.drawable.indicator_inactive)
        }
    }

    private fun navigateToGetStartedActivity() {
        val intent = Intent(this, GettingStartedActivity::class.java)
        startActivity(intent)
        finish()
    }
}