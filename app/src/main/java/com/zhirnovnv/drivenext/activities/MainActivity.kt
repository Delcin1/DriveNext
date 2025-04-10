package com.zhirnovnv.drivenext.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.zhirnovnv.drivenext.R
import com.zhirnovnv.drivenext.database.AppDatabase
import com.zhirnovnv.drivenext.database.User
import com.zhirnovnv.drivenext.databinding.ActivityNavigationBinding
import com.zhirnovnv.drivenext.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : CheckTokenActivity() {

    private lateinit var binding: ActivityNavigationBinding
    private lateinit var viewModel: UserDataViewModel
    private lateinit var repository: UserRepository
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getInstance(this)
        repository = UserRepository(db.userDao())


        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val email = prefs.getString("email", null)

        viewModel = ViewModelProvider(this)[UserDataViewModel::class.java]
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val controller = navHostFragment.navController

        CoroutineScope(Dispatchers.IO).launch {
            user = repository.getUserByEmail(email?:"")
            withContext(Dispatchers.Main) {
                if (user == null) {
                    Toast.makeText(this@MainActivity, "Пользователь не найден", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@MainActivity, GettingStartedActivity::class.java))
                    finish()
                }

                val gender = user?.gender
                val namee = user?.name
                val lastname = user?.lastname
                val avatarUri = user?.avatarUri


                viewModel.setUserData(gender, email, namee, lastname, avatarUri)

                val btnNavView = binding.bottomNavigationView2
                btnNavView.setupWithNavController(controller)
            }
        }
    }
}