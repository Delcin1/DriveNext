package com.zhirnovnv.drivenext.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhirnovnv.drivenext.R

class OnboardingAdapter(private val context: Context) : RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    private val titles = arrayOf(
        context.getString(R.string.onboarding_title_1),
        context.getString(R.string.onboarding_title_2),
        context.getString(R.string.onboarding_title_3)
    )

    private val descriptions = arrayOf(
        context.getString(R.string.onboarding_description_1),
        context.getString(R.string.onboarding_description_2),
        context.getString(R.string.onboarding_description_3)
    )

    private val images = arrayOf(
        R.drawable.onboarding_image_1,
        R.drawable.onboarding_image_2,
        R.drawable.onboarding_image_3
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_onboarding_page, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(images[position], titles[position], descriptions[position])
    }

    override fun getItemCount(): Int = titles.size

    inner class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

        fun bind(imageRes: Int, title: String, description: String) {
            imageView.setImageResource(imageRes)
            titleTextView.text = title
            descriptionTextView.text = description
        }
    }
}