package com.zhirnovnv.drivenext.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.zhirnovnv.drivenext.databinding.ActivityLoginBinding
import com.zhirnovnv.drivenext.database.AppDatabase
import com.zhirnovnv.drivenext.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.content.edit

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var repository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        val db = AppDatabase.getInstance(this)
        repository = UserRepository(db.userDao())

        binding.loginSignUpText.setOnClickListener {
            val intent = Intent(this, Signup1Activity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginSignInBtn.setOnClickListener {
            val email = binding.loginMailField.text.toString().trim()
            val password = binding.loginPassField.text.toString().trim()

            // Валидация ввода
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.loginMailField.error = "Неверный email"
                return@setOnClickListener
            }
            if (password.length < 6) {
                binding.loginPassField.error = "Минимальная длина пароля 6 символов"
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                val token = repository.login(email, password)
                withContext(Dispatchers.Main) {
                    if (token != null) {
                        // Сохранение токена для автологина
                        prefs.edit() { putString("user_token", token) }
                        prefs.edit() { putString("email", email) }
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Неверные данные", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}