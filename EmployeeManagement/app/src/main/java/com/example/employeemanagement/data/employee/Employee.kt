package com.example.employeemanagement.data.employee

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.employeemanagement.data.role.Role
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(
    tableName = "employees",
    foreignKeys = [
        ForeignKey(
            entity = Role::class,          // Entity tham chiếu (Role)
            parentColumns = ["id"],        // Cột trong bảng cha (Role) được tham chiếu
            childColumns = ["roleId"],     // Cột trong bảng con (Employee) tham chiếu đến bảng cha
            onDelete = CASCADE             // Hành động khi bảng cha bị xóa (CASCADE nghĩa là xóa nhân viên khi role bị xóa)
        )
    ],
    indices = [Index(value = ["roleId"])]  // Thêm chỉ mục cho cột roleId để tối ưu hóa truy vấn
)
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val homeTown: String,
    val roleId: Int  // Đây là cột khóa ngoại tham chiếu đến bảng Role
) : Parcelable