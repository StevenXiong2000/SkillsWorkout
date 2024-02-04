package com.example.skillsworkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainActivityViewModel: ViewModel() {
    private val _permissionGranted = MutableLiveData<Boolean>(false)
    val permissionGranted: LiveData<Boolean> = _permissionGranted

    fun updatePermission(permission:  Boolean) {
        _permissionGranted.value = permission
    }
}
