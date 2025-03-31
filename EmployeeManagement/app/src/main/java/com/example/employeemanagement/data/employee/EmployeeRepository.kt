package com.example.employeemanagement.data.employee

import androidx.lifecycle.LiveData

class EmployeeRepository(private val employeeDao: EmployeeDao) : EmployeeDao {

    override fun getAllEmployee(): LiveData<List<Employee>> {
        return employeeDao.getAllEmployee()
    }

    override suspend fun addEmployee(employee: Employee) {
        employeeDao.addEmployee(employee)
    }

    override suspend fun updateEmployee(employee: Employee) {
        employeeDao.updateEmployee(employee)
    }

    override suspend fun deleteEmployee(id: Int) {
        employeeDao.deleteEmployee(id)
    }

    override fun getAllEmployeeWithRole(): LiveData<List<EmployeeWithRole>> {
        return employeeDao.getAllEmployeeWithRole()
    }

    override fun getAllEmployeeWithRoleByName(name: String): LiveData<List<EmployeeWithRole>> {
        return employeeDao.getAllEmployeeWithRoleByName(name)
    }
}