package com.example.employeemanagement.data.employee

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.example.employeemanagement.data.role.Role
import kotlinx.parcelize.Parcelize


@Parcelize
data class EmployeeWithRole(
    @Embedded val employee: Employee,
    @Relation(
        parentColumn = "roleId",
        entityColumn = "id"
    )
    val role: Role
) : Parcelable