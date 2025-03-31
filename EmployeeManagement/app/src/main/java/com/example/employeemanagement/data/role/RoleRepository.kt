package com.example.employeemanagement.data.role

import androidx.lifecycle.LiveData

class RoleRepository(private val roleDao: RoleDao) : RoleDao {
    override fun getAllRole(): LiveData<List<Role>> {
        return roleDao.getAllRole()
    }

    override suspend fun addRole(role: Role) {
        roleDao.addRole(role)
    }

    override suspend fun getRoleIdByNameRole(role: String): Int {
        return roleDao.getRoleIdByNameRole(role)
    }

    override suspend fun deleteRole(id: Int) {
        return roleDao.deleteRole(id)
    }

    override suspend fun updateRole(role: Role) {
        return roleDao.updateRole(role)
    }

    override suspend fun deleteAll() {
        return roleDao.deleteAll()
    }
}