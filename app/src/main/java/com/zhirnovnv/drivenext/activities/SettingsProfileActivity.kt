package com.zhirnovnv.drivenext.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View.INVISIBLE
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.zhirnovnv.drivenext.databinding.ActivitySettingsBinding
import androidx.core.graphics.scale

class SettingsProfileActivity : CheckTokenActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private var avatarUri: Uri? = null

    private val pickAvatarLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            if (imageUri != null) {
                avatarUri = imageUri
                val inputStream = contentResolver.openInputStream(imageUri)
                val originalBitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                // Ресайзим Bitmap
                val compressedBitmap = originalBitmap.scale(256, 256)

                binding.imageButton.setImageBitmap(compressedBitmap)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fullName = intent.getStringExtra("fullName")
        val email = intent.getStringExtra("email")
        val gender = intent.getStringExtra("gender")
        val intent2 = Intent(this, LoginActivity::class.java)

        binding.textView2.text = fullName
        binding.mailTv.text = email
        binding.genderTv.text = gender

        binding.imageButton.setOnClickListener {
            openGallery(pickAvatarLauncher)
        }

        val btn = binding.exitProfileMb
        btn.setOnClickListener{
            startActivity(intent2)
            finish()
        }
    }
    private fun openGallery(launcher: androidx.activity.result.ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        launcher.launch(intent)
    }
}