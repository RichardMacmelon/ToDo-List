package com.example.todolist.presentation.mainFragment

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.TaskDb
import com.example.todolist.databinding.TaskCardBinding

class RecyclerViewAdapter(private val onClick: (TaskDb) -> Unit) :
    RecyclerView.Adapter<RecyclerViewViewHolder>() {

    private var data = emptyList<TaskDb>()
    fun setData(data: List<TaskDb>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        return RecyclerViewViewHolder(
            TaskCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            textViewTaskName.text = item?.taskName
            textViewTime.text = item?.taskDate

            if(item?.done == true) {
                textViewTaskName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                textViewTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }

            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = item?.done == true
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                item?.let {
                    val updatedItem = item.copy(done = isChecked)
                    onClick(updatedItem)
                }
            }
        }
    }
}