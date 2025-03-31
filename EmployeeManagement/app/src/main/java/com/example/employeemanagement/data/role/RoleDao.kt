package com.example.employeemanagement.data.role

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RoleDao {
    @Query("select * from roles")
    fun getAllRole(): LiveData<List<Role>>

    @Insert
    suspend fun addRole(role: Role)

    @Query("SELECT id FROM roles WHERE role LIKE '%' || :role || '%'")
    suspend fun getRoleIdByNameRole(role: String): Int

    @Query("DELETE FROM roles WHERE id = :id")
    suspend fun deleteRole(id: Int)

    @Update
    suspend fun updateRole(role: Role)

    @Query("delete from roles")
    suspend fun deleteAll()
}