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

class UpdateRoleFragment : Fragment() {
    private val roleViewModel by viewModels<RoleViewModel> {
        RoleViewModelFactory(RoleRepository(AppDatabase.getDatabase(requireContext()).roleDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update_role, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val role = arguments?.let {
            UpdateRoleFragmentArgs.fromBundle(it).role
        }


        view.findViewById<EditText>(R.id.edtRole).setText(role?.role)
        view.findViewById<EditText>(R.id.edtSalary).setText(role?.salary.toString())



        view.findViewById<Button>(R.id.btnDeleteRole).setOnClickListener {
            role?.id?.let { it1 -> roleViewModel.delete(it1) }
            roleViewModel.stateDelete.observe(viewLifecycleOwner) { state ->
                if (state == true) {
                    view.findNavController().popBackStack()
                }
            }
        }

        view.findViewById<Button>(R.id.btnUpdateRole).setOnClickListener {
            val roleName = view.findViewById<EditText>(R.id.edtRole).text.toString()
            val salary = view.findViewById<EditText>(R.id.edtSalary).text.toString()

            if (roleName.isEmpty()) {
                Toast.makeText(requireContext(),"Cannot be left blank", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (salary.isEmpty()) {
                Toast.makeText(requireContext(),"Cannot be left blank", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            role?.id?.let {
                roleViewModel.updateRole(
                    Role(role.id, roleName, salary.toDouble())
                )
            }
            roleViewModel.stateUpdate.observe(viewLifecycleOwner) { state ->
                if (state == true) {
                    view.findNavController().popBackStack()
                }
            }
        }

    }
}