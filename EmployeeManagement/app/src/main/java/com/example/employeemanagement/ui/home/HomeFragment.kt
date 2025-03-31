package com.example.employeemanagement.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeemanagement.R
import com.example.employeemanagement.data.AppDatabase
import com.example.employeemanagement.data.employee.EmployeeRepository
import com.example.employeemanagement.databinding.FragmentHomeBinding
import com.example.employeemanagement.ui.employee.EmployeeViewModel
import com.example.employeemanagement.ui.employee.EmployeeViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapterHome by lazy {
        HomeAdapter()
    }
    private val employeeViewModel: EmployeeViewModel by viewModels<EmployeeViewModel> {
        EmployeeViewModelFactory(
            EmployeeRepository(
                AppDatabase.getDatabase(requireContext()).employeeDao()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUpAdapter(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        employeeViewModel.allEmployeesWithRole.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                binding.tvMota.visibility = View.VISIBLE
            } else {
                binding.tvMota.visibility = View.GONE
                adapterHome.submitList(list)
            }
        }
        val searchView = view.findViewById<SearchView>(R.id.searchView1)


        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(name: String?): Boolean {
                    Log.d("SearchView", name.toString())
                    return false
                }

                override fun onQueryTextChange(name: String?): Boolean {
                    if (name != null) {
                        employeeViewModel.filterEmployees(name)
                            .observe(viewLifecycleOwner) { list ->
                                adapterHome.submitList(list)
                            }
                    }
                    return true
                }

            }
        )
    }

    private fun setUpAdapter(binding: FragmentHomeBinding) {
        val recyclerView: RecyclerView = binding.rcvHome
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapterHome
    }


}
