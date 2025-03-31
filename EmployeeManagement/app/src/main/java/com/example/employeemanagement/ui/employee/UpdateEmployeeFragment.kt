package com.example.employeemanagement.ui.employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.employeemanagement.R
import com.example.employeemanagement.data.AppDatabase
import com.example.employeemanagement.data.employee.Employee
import com.example.employeemanagement.data.employee.EmployeeRepository
import com.example.employeemanagement.data.role.RoleRepository
import com.example.employeemanagement.ui.role.RoleViewModel
import com.example.employeemanagement.ui.role.RoleViewModelFactory

class UpdateEmployeeFragment : Fragment() {
    private val employeeViewModel: EmployeeViewModel by viewModels<EmployeeViewModel> {
        EmployeeViewModelFactory(
            EmployeeRepository(
                AppDatabase.getDatabase(requireContext()).employeeDao()
            )
        )
    }
    private val roleViewModel: RoleViewModel by viewModels<RoleViewModel> {
        RoleViewModelFactory(
            RoleRepository(
                AppDatabase.getDatabase(requireContext()).roleDao()
            )
        )
    }

    private var roleIds: List<Int>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update_employee, container, false)

        val spinner: Spinner = view.findViewById(R.id.spnRolesUpdate)

        roleViewModel.allRoles.observe(viewLifecycleOwner) { roleList ->
            val roleNames = roleList.map { it.role }
            roleIds = roleList.map { it.id }
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, roleNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.setTag(R.id.spnRolesUpdate, roleIds)

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner = view.findViewById<Spinner>(R.id.spnRolesUpdate)

        val employee = arguments?.let {
            UpdateEmployeeFragmentArgs.fromBundle(it).employee
        }
        view.findViewById<EditText>(R.id.edtName).setText(employee?.name)
        view.findViewById<EditText>(R.id.edtHometown).setText(employee?.homeTown)


        roleViewModel.allRoles.observe(viewLifecycleOwner) { roleList ->
            if (roleList.isNotEmpty()) {
                employee?.roleId?.let { roleId ->
                    val rolePosition = roleIds?.indexOf(roleId)
                    if (rolePosition != -1) {
                        spinner.post {
                            if (rolePosition != null) {
                                spinner.setSelection(rolePosition)
                            }
                        }
                    }
                }
            }
        }


        view.findViewById<Button>(R.id.btnUpdate).setOnClickListener {

            val role = spinner.getTag(R.id.spnRolesUpdate) as? List<Int> ?: emptyList()
            val position = spinner.selectedItemPosition

            val name = view.findViewById<EditText>(R.id.edtName).text.toString()
            val homeTown = view.findViewById<EditText>(R.id.edtHometown).text.toString()

            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Cannot be left blank", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (homeTown.isEmpty()) {
                Toast.makeText(requireContext(), "Cannot be left blank", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val roleId = role[position]

            employee?.let {
                employeeViewModel.updateEmployee(
                    Employee(
                        id = employee.id,
                        name = name,
                        homeTown = homeTown,
                        roleId = roleId
                    )
                )
            }
            employeeViewModel.stateUpdate.observe(viewLifecycleOwner) { state ->
                if (state == true) {
                    view.findNavController().popBackStack()
                }
            }
        }
        view.findViewById<Button>(R.id.btnDelete).setOnClickListener {
            employee?.let {
                employeeViewModel.deleteEmployee(employee.id)
            }
            employeeViewModel.stateDelete.observe(viewLifecycleOwner) { state ->
                if (state == true) {
                    view.findNavController().popBackStack()
                }
            }
        }
    }
}