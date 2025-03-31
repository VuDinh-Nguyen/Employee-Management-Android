package com.example.employeemanagement.data.role

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "roles")
@Parcelize
data class Role(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val role: String,
    val salary: Double

) : Parcelable