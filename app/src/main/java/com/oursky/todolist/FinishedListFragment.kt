package com.oursky.todolist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.oursky.todolist.databinding.FragmentFinishedBinding

class FinishedListFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this).get(FinishedListViewModel::class.java)
        val sharedPref = this.requireActivity().getSharedPreferences("", Context.MODE_PRIVATE)

        viewModel.loadFromSharedPreference(
            this.requireActivity().getSharedPreferences("", Context.MODE_PRIVATE)
        )

        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root = binding.root

        val adapter = TodoItemAdapter(
            { item ->
                run {
                    viewModel.toggle(item)
                    viewModel.saveToSharedPreference(sharedPref)
                }
            },
            { item ->
                run {
                    viewModel.remove(item)
                }
            },
        )

        val recyclerView = binding.todoList
        recyclerView.adapter = adapter

        viewModel.list.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
