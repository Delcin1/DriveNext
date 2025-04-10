package com.zhirnovnv.drivenext.activities


import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserDataViewModel : ViewModel() {
    private val _userData = MutableLiveData<Bundle>()
    val userData: LiveData<Bundle> get() = _userData

    fun setUserData(
        gender: String?,
        email: String?,
        name: String?,
        lastname: String?,
        avatarUri: String?,

    ) {
        val bundle = Bundle().apply {
            putString("gender", gender)
            putString("email", email)
            putString("namee", name)
            putString("lastname", lastname)
            putString("avatarUri", avatarUri)
        }
        _userData.value = bundle
    }
}