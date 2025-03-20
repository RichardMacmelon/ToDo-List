package com.example.todolist.presentation.addNameFragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.todolist.data.TaskDb
import com.example.todolist.databinding.FragmentAddNameBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime

@AndroidEntryPoint
class AddNameFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddNameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddNameViewModel by viewModels()

    private val tabIndex: Int by lazy {
        arguments?.getInt("tabIndex") ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNameBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeTasks()
    }

    private fun setupUI() {
        with(binding.timePicker) {
            setIs24HourView(true)
            hour = LocalTime.now().hour + 1
            minute = 0
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        binding.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.editText.clearFocus()
                requireActivity().hideKeyboard(binding.editText)
                true
            } else {
                false
            }
        }

        binding.editText.addTextChangedListener {
            viewModel.nameText.value = it.toString()
        }

        binding.buttonSave.setOnClickListener {
            val task = TaskDb(
                taskName = binding.editText.text.toString(),
                taskDate = binding.timePicker.hour.toString() + ":" + binding.timePicker.minute.toString() + 0,
                done = tabIndex == 1
            )
            viewModel.addToCollection(task)
            dismiss()
        }
    }

    private fun observeTasks() {
        viewModel.isButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            binding.buttonSave.isEnabled = isEnabled
            binding.buttonSave.alpha = if (isEnabled) 1.0f else 0.5f
        }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}