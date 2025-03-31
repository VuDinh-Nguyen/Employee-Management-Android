package com.example.employeemanagement.ui.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeemanagement.data.employee.Employee
import com.example.employeemanagement.data.employee.EmployeeRepository
import com.example.employeemanagement.data.employee.EmployeeWithRole
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeViewModel(private val employeeRepository: EmployeeRepository) : ViewModel() {


    val allEmployeesWithRole = employeeRepository.getAllEmployeeWithRole()

    val stateAdd = MutableLiveData<Boolean>().apply {
        value = false
    }
    val stateUpdate = MutableLiveData<Boolean>().apply {
        value = false
    }
    val stateDelete = MutableLiveData<Boolean>().apply {
        value = false
    }

    fun filterEmployees(name: String): LiveData<List<EmployeeWithRole>> {
        return employeeRepository.getAllEmployeeWithRoleByName(name)
    }

    fun addEmployee(employee: Employee) {
        viewModelScope.launch(Dispatchers.IO) {
            employeeRepository.addEmployee(employee)
            stateAdd.postValue(true)
        }
    }

    fun updateEmployee(employee: Employee) {
        viewModelScope.launch(Dispatchers.IO) {
            employeeRepository.updateEmployee(employee)
            stateUpdate.postValue(true)
        }
    }

    fun deleteEmployee(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            employeeRepository.deleteEmployee(id)
            stateDelete.postValue(true)
        }
    }
}