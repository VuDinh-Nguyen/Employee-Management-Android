package com.example.employeemanagement.ui.employee

import android.os.Bundle
import android.util.Log
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
import com.example.employeemanagement.MainActivity
import com.example.employeemanagement.R
import com.example.employeemanagement.data.AppDatabase
import com.example.employeemanagement.data.employee.Employee
import com.example.employeemanagement.data.employee.EmployeeRepository
import com.example.employeemanagement.data.role.RoleRepository
import com.example.employeemanagement.ui.role.RoleViewModel
import com.example.employeemanagement.ui.role.RoleViewModelFactory
import java.util.jar.Attributes.Name
import kotlin.math.log

class AddEmployeeFragment : Fragment() {
    private val employeeViewModel: EmployeeViewModel by viewModels<EmployeeViewModel> {
        EmployeeViewModelFactory(
            EmployeeRepository(
                AppDatabase.getDatabase(requireContext()).employeeDao()
            )
        )
    }
    private val roleViewModel: RoleViewModel by viewModels<RoleViewModel> {
        RoleViewModelFactory(RoleRepository(AppDatabase.getDatabase(requireContext()).roleDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_employee, container, false)
        val spinner: Spinner = view.findViewById(R.id.spnRoles)

        roleViewModel.allRoles.observe(viewLifecycleOwner) { roleList ->
            val roleNames = roleList.map { it.role }
            val roleId = roleList.map { it.id }
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, roleNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.setTag(R.id.spnRoles, roleId)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = view.findViewById(R.id.spnRoles)


        view.findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val name = view.findViewById<EditText>(R.id.edtName).text.toString()
            val homeTown = view.findViewById<EditText>(R.id.edtHometown).text.toString()

            if (name.isEmpty()) {
                Toast.makeText(requireContext(),"Cannot be left blank", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (homeTown.isEmpty()) {
                Toast.makeText(requireContext(),"Cannot be left blank", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            val position = spinner.selectedItemPosition

            val rolesId = spinner.getTag(R.id.spnRoles) as List<Int>
            val roleId = rolesId[position]


            val employee = Employee(name = name, homeTown = homeTown, roleId = roleId)
            Log.d("DEBUG", "Adding Employee: $employee")
            employeeViewModel.addEmployee(employee)
        }

        employeeViewModel.stateAdd.observe(viewLifecycleOwner) { state ->
            if (state == true) {
                view.findNavController().popBackStack()
            }
        }
    }
}