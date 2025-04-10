package com.zhirnovnv.drivenext.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View.INVISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.android.material.datepicker.MaterialDatePicker
import com.zhirnovnv.drivenext.R
import com.zhirnovnv.drivenext.databinding.ActivitySignup3Binding
import com.zhirnovnv.drivenext.database.AppDatabase
import com.zhirnovnv.drivenext.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.core.graphics.scale

class Signup3Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySignup3Binding
    private lateinit var repository: UserRepository

    private var avatarUri: Uri? = null
    private var passportUri: Uri? = null
    private var licenseUri: Uri? = null

    private lateinit var photoChooseLicense: TextView
    private lateinit var photoChoosePassport: TextView

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

                binding.su3AvatarImg.setImageBitmap(compressedBitmap)
            }
        }
    }

    private val pickPassportLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            if (imageUri != null) {
                passportUri = imageUri
                photoChoosePassport = binding.su3UploadPassText
                photoChoosePassport.text = getString(R.string.photo_picked)
            }
        }
    }

    private val pickLicenseLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            if (imageUri != null) {
                licenseUri = imageUri
                photoChooseLicense = binding.su3UploadLicenseText
                photoChooseLicense.text = getString(R.string.photo_picked)
            }
        }
    }

    private fun openGallery(launcher: androidx.activity.result.ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignup3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        val db = AppDatabase.getInstance(this)
        repository = UserRepository(db.userDao())

        binding.su3AddAvatarBtn.setOnClickListener {
            openGallery(pickAvatarLauncher)
            binding.su3AddAvatarBtn.visibility = INVISIBLE
        }

        binding.su3UploadPassImgBtn.setOnClickListener {
            openGallery(pickPassportLauncher)
        }

        binding.su3UploadLicenseImgBtn.setOnClickListener {
            openGallery(pickLicenseLauncher)
        }

        binding.su3DateImgBtn.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.date_pick))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            datePicker.show(supportFragmentManager, "datePicker")
            datePicker.addOnPositiveButtonClickListener {
                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                binding.su3DateText.text = simpleDateFormat.format(Date(it).time)
            }
        }

        binding.su3NextBtn.setOnClickListener {
            val license = binding.su3LicenseFieldET.text.toString().trim()
            if (license.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, заполните все обязательные поля.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val licenseDate = binding.su3DateText.text.toString().trim()
            if (licenseDate.isEmpty()) {
                binding.su3DateText.error = "Введите дату выдачи удостоверения"
                return@setOnClickListener
            }

            if (passportUri == null || licenseUri == null) {
                Toast.makeText(this, "Пожалуйста, загрузите все необходимые фото.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                val token = repository.register(
                    intent.getStringExtra("email") ?: "",
                    intent.getStringExtra("password") ?: "",
                    intent.getStringExtra("lastname") ?: "",
                    intent.getStringExtra("name") ?: "",
                    intent.getStringExtra("surname") ?: "",
                    intent.getStringExtra("birthDate") ?: "",
                    intent.getStringExtra("gender") ?: "",
                    license,
                    licenseUri.toString(),
                    licenseDate,
                    passportUri.toString())
                withContext(Dispatchers.Main) {
                    if (token != null) {
                        // Сохранение токена для автологина
                        prefs.edit() { putString("user_token", token) }
                        startActivity(Intent(this@Signup3Activity, CongratulationActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@Signup3Activity, "Такой пользователь уже существует", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.su3BackBtn.setOnClickListener {
            finish()
        }
    }
}