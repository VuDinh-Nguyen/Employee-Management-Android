package com.example.employeemanagement.ui.role

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeemanagement.R
import com.example.employeemanagement.data.AppDatabase
import com.example.employeemanagement.data.role.RoleRepository

class RoleFragment : Fragment() {
    private val roleAdapter by lazy {
        RoleAdapter()
    }
    private val roleViewModel: RoleViewModel by viewModels<RoleViewModel> {
        RoleViewModelFactory(RoleRepository(AppDatabase.getDatabase(requireContext()).roleDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_role, container, false)
        setUpAdapter(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnInsertRole).setOnClickListener {
            view.findNavController().navigate(R.id.nav_add_role)
        }

        roleViewModel.allRoles.observe(viewLifecycleOwner) { list ->
            roleAdapter.submitList(list)
        }

    }

    private fun setUpAdapter(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.rcvRole)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = roleAdapter

    }


}