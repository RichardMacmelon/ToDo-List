package com.example.todolist.presentation.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private val recyclerViewAdapter = RecyclerViewAdapter()

    private val tabIndex: Int by lazy {
        arguments?.getInt("tabIndex") ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupUI()
        observeTasks()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = recyclerViewAdapter
    }

    private fun setupUI() {
        binding.textView.text = when (tabIndex) {
            0 -> getString(R.string.name_first_tab)
            else -> getString(R.string.name_second_tab)
        }

        binding.buttonAdd.setOnClickListener {
            val bundle = bundleOf("tabIndex" to tabIndex)
            findNavController().navigate(R.id.action_mainFragment_to_addNameFragment, bundle)
        }

        binding.lottieAnimationView.setAnimation(
            if (tabIndex == 0) R.raw.animation_to_first_tab else R.raw.animation_to_second_tab
        )
    }

    private fun observeTasks() {
        viewLifecycleOwner.lifecycleScope.launch {
            val tasks = if (tabIndex == 0) viewModel.allPlannedTask else viewModel.allCompletedTask
            tasks.collect { task ->
                viewModel.tasks.value = task
                recyclerViewAdapter.setData(task)
            }
        }

        viewModel.isNoTasks.observe(viewLifecycleOwner) { isEmpty ->
            if (isEmpty) {
                binding.recyclerView.visibility = View.INVISIBLE
                binding.lottieAnimationView.visibility = View.VISIBLE
                binding.title.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.lottieAnimationView.visibility = View.INVISIBLE
                binding.title.visibility = View.INVISIBLE
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}