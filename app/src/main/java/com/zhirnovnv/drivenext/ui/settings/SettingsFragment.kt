package com.zhirnovnv.drivenext.ui.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.zhirnovnv.drivenext.databinding.FragmentSettingsBinding
import com.zhirnovnv.drivenext.activities.SettingsProfileActivity
import com.zhirnovnv.drivenext.activities.UserDataViewModel


class SettingsFragment : Fragment() {

    private lateinit var viewModel: UserDataViewModel
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(UserDataViewModel::class.java)
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSettingsBinding.inflate(layoutInflater)
        val view = binding.root
        val btn = binding.materialButton2
        val nameTv = binding.textView
        val email = binding.textView4
        viewModel.userData.observe(viewLifecycleOwner) { bundle ->
            val namee = bundle.getString("namee")
            val lastname = bundle.getString("lastname")
            val gender = bundle.getString("gender")
            email.text = bundle.getString("email")
            val avatarUri = bundle.getString("avatarUri")
            val fullName = "$namee $lastname".trim()
            nameTv.text = fullName



            btn.setOnClickListener {
                val intent = Intent(activity, SettingsProfileActivity::class.java).apply {
                    putExtra("fullName", fullName)
                    putExtra("email", email.text)
                    putExtra("gender", gender)
                    putExtra("avatarUri", avatarUri)
                }
                startActivity(intent)
            }
        }
        return view
    }


}