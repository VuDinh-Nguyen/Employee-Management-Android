package com.example.employeemanagement.data.employee

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees")
    fun getAllEmployee(): LiveData<List<Employee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEmployee(employee: Employee)

    @Update
    suspend fun updateEmployee(employee: Employee)

    @Query("DELETE FROM employees WHERE id = :id")
    suspend fun deleteEmployee(id: Int)

    @Query("SELECT * FROM employees")
    fun getAllEmployeeWithRole(): LiveData<List<EmployeeWithRole>>

    @Query("SELECT * FROM employees WHERE name LIKE '%' || :name || '%'")
    fun getAllEmployeeWithRoleByName(name: String): LiveData<List<EmployeeWithRole>>

}
