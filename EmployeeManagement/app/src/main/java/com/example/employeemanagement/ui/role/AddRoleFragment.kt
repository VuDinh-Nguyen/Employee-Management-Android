package com.example.employeemanagement.ui.role

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.employeemanagement.R
import com.example.employeemanagement.data.AppDatabase
import com.example.employeemanagement.data.role.Role
import com.example.employeemanagement.data.role.RoleRepository

class AddRoleFragment : Fragment() {
    private val roleViewModel: RoleViewModel by viewModels<RoleViewModel> {
        RoleViewModelFactory(RoleRepository(AppDatabase.getDatabase(requireContext()).roleDao()))
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_role, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btnAddRole).setOnClickListener {
            val role = view.findViewById<EditText>(R.id.edtRole).text.toString()
            val salary = view.findViewById<EditText>(R.id.edtSalary).text.toString()

            if (role.isEmpty()) {
                Toast.makeText(requireContext(),"Cannot be left blank",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (salary.isEmpty()) {
                Toast.makeText(requireContext(),"Cannot be left blank",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val roles = Role(
                role = role,
                salary = salary.toDouble()
            )
            roleViewModel.addEmployee(roles)
            roleViewModel.stateAdd.observe(viewLifecycleOwner) { state ->
                if (state == true) {
                    view.findNavController().popBackStack()
                }
            }
        }
    }
}