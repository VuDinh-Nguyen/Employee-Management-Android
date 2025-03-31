package com.example.employeemanagement.ui.role

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeemanagement.data.role.Role
import com.example.employeemanagement.data.role.RoleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoleViewModel(private val roleRepository: RoleRepository) : ViewModel() {

    val allRoles = roleRepository.getAllRole()


    val stateAdd = MutableLiveData<Boolean>().apply {
        value = false
    }
    val stateUpdate = MutableLiveData<Boolean>().apply {
        value = false
    }
    val stateDelete = MutableLiveData<Boolean>().apply {
        value = false
    }

    fun addEmployee(role: Role) {
        viewModelScope.launch(Dispatchers.IO) {
            roleRepository.addRole(role = role)
            stateAdd.postValue(true)
        }
    }

    fun updateRole(role: Role) {
        viewModelScope.launch(Dispatchers.IO) {
            roleRepository.updateRole(role)
            stateUpdate.postValue(true)
        }
    }

    fun delete(roleId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            roleRepository.deleteRole(roleId)
            stateDelete.postValue(true)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            roleRepository.deleteAll()
        }
    }
}