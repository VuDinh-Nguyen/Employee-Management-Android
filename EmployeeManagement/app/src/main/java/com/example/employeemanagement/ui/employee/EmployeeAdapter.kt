package com.example.employeemanagement.ui.employee

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.employeemanagement.data.employee.EmployeeWithRole
import com.example.employeemanagement.databinding.ItemEmployeeAdapterBinding

class EmployeeAdapter() :
    ListAdapter<EmployeeWithRole, EmployeeAdapter.EmployeeViewHolder>(DIFF_CALLBACK) {
    class EmployeeViewHolder(private val binding: ItemEmployeeAdapterBinding) :
        ViewHolder(binding.root) {

        var employee: EmployeeWithRole? = null

        init {
            binding.imgUpdate.setOnClickListener {
                employee?.apply {
                    val action =
                        EmployeeFragmentDirections.actionNavEmployeeToNavUpdateEmployee(employee)
                    itemView.findNavController().navigate(action)
                }
            }
        }

        fun bind(employee: EmployeeWithRole) {
            this.employee = employee
            binding.name.text = employee.employee.name
            binding.role.text = employee.role.role
            binding.id.text = employee.role.salary.toString()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EmployeeWithRole>() {
            override fun areItemsTheSame(
                oldItem: EmployeeWithRole,
                newItem: EmployeeWithRole
            ): Boolean {
                return oldItem.employee.id == newItem.employee.id
            }

            override fun areContentsTheSame(
                oldItem: EmployeeWithRole,
                newItem: EmployeeWithRole
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding =
            ItemEmployeeAdapterBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = getItem(position)
        holder.bind(employee)
    }
}
