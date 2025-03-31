package com.example.employeemanagement.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.employeemanagement.R
import com.example.employeemanagement.data.AppDatabase
import com.example.employeemanagement.data.role.RoleRepository
import com.example.employeemanagement.ui.role.RoleViewModel
import com.example.employeemanagement.ui.role.RoleViewModelFactory

class SettingFragment : Fragment() {

    private val roleViewModel by viewModels<RoleViewModel> {
        RoleViewModelFactory(RoleRepository(AppDatabase.getDatabase(requireContext()).roleDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.btnDeleteData).setOnClickListener {
            roleViewModel.deleteAll()
            Toast.makeText(requireContext(), "Deleted All", Toast.LENGTH_LONG).show()
        }
        view.findViewById<Button>(R.id.btnExit).setOnClickListener {
            activity?.finish()
        }
    }
}