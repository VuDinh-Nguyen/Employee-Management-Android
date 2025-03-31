package com.example.employeemanagement.ui.role

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
import com.example.employeemanagement.data.role.Role

class RoleAdapter() : ListAdapter<Role, RoleAdapter.RoleViewHolder>(DIFF_CALLBACK) {
    class RoleViewHolder(itemView: View) : ViewHolder(itemView) {
        private var role: Role? = null

        init {
            itemView.findViewById<ImageView>(R.id.imgUpdateRole).setOnClickListener {
                role?.apply {
                    val action = RoleFragmentDirections.actionNavRoleToNavUpdateRole(this)
                    itemView.findNavController().navigate(action)
                }

            }
        }

        fun bind(role: Role) {
            this.role = role
            itemView.findViewById<TextView>(R.id.tvRole).text = role.role
            itemView.findViewById<TextView>(R.id.tvSalary).text = role.salary.toString()
            itemView.findViewById<TextView>(R.id.tvId).text = role.id.toString()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Role>() {
            override fun areItemsTheSame(oldItem: Role, newItem: Role): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Role, newItem: Role): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_role_adapter, parent, false)
        return RoleViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoleViewHolder, position: Int) {
        val role = getItem(position)
        holder.bind(role)
    }
}