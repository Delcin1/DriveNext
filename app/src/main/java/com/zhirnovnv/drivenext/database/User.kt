package com.zhirnovnv.drivenext.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val email: String,
    val password: String,
    val lastname: String = "",
    val name: String = "",
    val surname: String = "",
    val birthDate: String = "",
    val gender: String = "",
    val license: String = "",
    val licenseUri: String = "",
    val licenseRegDate: String = "",
    val passUri: String = "",
    val avatarUri: String = "",
    val token: String? = null
)