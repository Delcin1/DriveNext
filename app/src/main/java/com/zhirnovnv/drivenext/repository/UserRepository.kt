package com.zhirnovnv.drivenext.repository

import com.zhirnovnv.drivenext.database.User
import com.zhirnovnv.drivenext.database.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun login(email: String, password: String): String? {
        val user = userDao.getUserByEmail(email)
        return if (user != null && user.password == password) {
            val updatedUser = user.copy(token = "test_token")
            userDao.updateUser(updatedUser)
            "test_token"
        } else {
            null
        }
    }

    suspend fun register(
        email: String, password: String, lastname: String, name: String, surname: String,
        birthDate: String, gender: String, license: String, licenseUri: String,
        licenseRegDate: String, passUri: String
): String? {
        val existingUser = userDao.getUserByEmail(email)
        if (existingUser != null) return null
        val newUser = User(email = email, password = password, lastname = lastname, name = name,
            surname = surname, birthDate = birthDate, gender = gender, license = license,
            licenseUri = licenseUri, licenseRegDate = licenseRegDate, passUri = passUri,
            token = "test_token")
        userDao.insertUser(newUser)
        return "test_token"
    }
}