package com.example.employeemanagement.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.employeemanagement.R
import com.example.employeemanagement.data.employee.EmployeeWithRole

class HomeAdapter() :
    ListAdapter<EmployeeWithRole, HomeAdapter.EmployeeViewHolder>(DIFF_CALLBACK) {
    class EmployeeViewHolder(itemView: View) :
        ViewHolder(itemView) {

        var employee: EmployeeWithRole? = null
        init {
            itemView.findViewById<ImageView>(R.id.imgUpdate).setOnClickListener {
                employee?.apply {
                    val action = HomeFragmentDirections.actionNavHomeToNavUpdateEmployee(employee)
                    itemView.findNavController().navigate(action)
                }
            }
        }

        fun bind(employee: EmployeeWithRole) {
            this.employee = employee
            itemView.findViewById<TextView>(R.id.name).text = employee.employee.name
            itemView.findViewById<TextView>(R.id.role).text = employee.role.role
            itemView.findViewById<TextView>(R.id.id).text = employee.role.salary.toString()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EmployeeWithRole>() {
            override fun areItemsTheSame(oldItem: EmployeeWithRole, newItem: EmployeeWithRole): Boolean {
                return oldItem.employee.id == newItem.employee.id
            }

            override fun areContentsTheSame(oldItem: EmployeeWithRole, newItem: EmployeeWithRole): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_employee_adapter, parent, false)
        return EmployeeViewHolder(view)
    }


    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = getItem(position)
        holder.bind(employee)
    }
}