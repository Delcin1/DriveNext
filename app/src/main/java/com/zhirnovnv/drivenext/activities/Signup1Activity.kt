package com.zhirnovnv.drivenext.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.zhirnovnv.drivenext.databinding.ActivitySignup1Binding
import com.zhirnovnv.drivenext.database.AppDatabase
import com.zhirnovnv.drivenext.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Signup1Activity : BaseActivity() {

    private lateinit var binding: ActivitySignup1Binding
    private lateinit var repository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignup1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getInstance(this)
        repository = UserRepository(db.userDao())

        binding.su1PrevBtn.setOnClickListener {
            finish()
        }

        binding.su1NextBtn.setOnClickListener {
            if (!binding.su1Checkbox.isChecked) {
                Toast.makeText(this, "Необходимо согласиться с условиями обслуживания и политикой конфиденциальности.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val email = binding.su1EmailFieldET.text.toString().trim()
            val password = binding.su1PassFieldET.text.toString().trim()
            val repeatPassword = binding.su1RepeatPassFieldET.text.toString().trim()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.su1EmailFieldET.error = "Введите корректный адрес электронной почты."
                return@setOnClickListener
            }
            if (password.length < 6) {
                binding.su1PassFieldET.error = "Пароль должен быть не менее 6 символов"
                return@setOnClickListener
            }
            if (password != repeatPassword) {
                binding.su1RepeatPassFieldET.error = "Пароли не совпадают."
                return@setOnClickListener
            }

            val intent = Intent(this, Signup2Activity::class.java).apply {
                putExtra("email", email)
                putExtra("password", password)
            }
            startActivity(intent)
        }
    }
}