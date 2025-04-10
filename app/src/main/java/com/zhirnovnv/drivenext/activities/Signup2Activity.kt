package com.zhirnovnv.drivenext.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.zhirnovnv.drivenext.R
import com.zhirnovnv.drivenext.databinding.ActivitySignup2Binding
import com.zhirnovnv.drivenext.database.AppDatabase
import com.zhirnovnv.drivenext.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Signup2Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySignup2Binding
    private lateinit var repository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignup2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getInstance(this)
        repository = UserRepository(db.userDao())

        binding.su2BirthImgBtn.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.date_pick))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            datePicker.show(supportFragmentManager, "datePicker")
            datePicker.addOnPositiveButtonClickListener {
                val simpleDateFormat = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
                binding.su2BirthText.text = simpleDateFormat.format(Date(it).time)
            }
        }

        binding.su2NextBtn.setOnClickListener {
            val lastname = binding.su2LastnameFieldET.text.toString().trim()
            val name = binding.su2NameFieldET.text.toString().trim()
            val surname = binding.su2SurnameFieldET.text.toString().trim()
            if (lastname.isEmpty() || name.isEmpty() || surname.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, заполните все обязательные поля.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val birthDate = binding.su2BirthText.text.toString().trim()
            if (birthDate.isEmpty()) {
                binding.su2BirthText.error = "Введите корректную дату рождения."
                return@setOnClickListener
            }
            // Получение выбранного ID радиокнопки из группы
            val selectedGenderId = binding.su2GenderRadio.checkedRadioButtonId
            // Преобразование выбранного ID в строковое представление гендера
            val gender = when (selectedGenderId) {
                binding.su2GenderMale.id -> "male"
                binding.su2GenderFemale.id -> "female"
                else -> ""
            }

            startActivity(Intent(this@Signup2Activity, Signup3Activity::class.java).apply {
                putExtra("lastname", lastname)
                putExtra("name", name)
                putExtra("surname", surname)
                putExtra("email", intent.getStringExtra("email"))
                putExtra("password", intent.getStringExtra("password"))
                putExtra("gender", gender)
                putExtra("birthDate", birthDate)
            })
        }

        binding.su2BackBtn.setOnClickListener {
            finish()
        }
    }
}