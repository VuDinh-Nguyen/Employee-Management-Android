package com.example.employeemanagement.ui.employee

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeemanagement.R
import com.example.employeemanagement.data.AppDatabase
import com.example.employeemanagement.data.employee.EmployeeRepository
import com.example.employeemanagement.data.role.RoleRepository
import com.example.employeemanagement.ui.role.RoleViewModel
import com.example.employeemanagement.ui.role.RoleViewModelFactory

class EmployeeFragment : Fragment() {

    private val adapterEmployee by lazy {
        EmployeeAdapter()
    }
    private val employeeViewModel: EmployeeViewModel by viewModels<EmployeeViewModel> {
        EmployeeViewModelFactory(
            EmployeeRepository(
                AppDatabase.getDatabase(requireContext()).employeeDao()
            )
        )
    }
    private val rolesViewModel by viewModels<RoleViewModel> {
        RoleViewModelFactory(RoleRepository(AppDatabase.getDatabase(requireContext()).roleDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_employee, container, false)
        setUpAdapter(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = view.findNavController()
        view.findViewById<Button>(R.id.btnInsert).setOnClickListener {
            rolesViewModel.allRoles.observe(viewLifecycleOwner) { role ->
                Log.d("role", role.toString())
                if (role.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "No role yet, please add a role first.",
                        Toast.LENGTH_LONG
                    ).show()
                    return@observe
                }
                navController.navigate(R.id.nav_add_employee)
            }
        }
        employeeViewModel.allEmployeesWithRole.observe(viewLifecycleOwner) { list ->
            adapterEmployee.submitList(list)
        }

    }

    private fun setUpAdapter(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.rcvEmployee)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapterEmployee

    }


}