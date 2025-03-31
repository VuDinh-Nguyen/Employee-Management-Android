package com.example.employeemanagement.ui.role

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.employeemanagement.data.role.RoleRepository

class RoleViewModelFactory(private val roleRepository: RoleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoleViewModel(roleRepository = roleRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}