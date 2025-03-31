package com.example.employeemanagement.ui.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.employeemanagement.data.employee.EmployeeRepository

class EmployeeViewModelFactory(private val employeeRepository: EmployeeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmployeeViewModel(employeeRepository = employeeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}