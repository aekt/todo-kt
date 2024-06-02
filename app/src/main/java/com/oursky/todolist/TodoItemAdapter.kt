package com.oursky.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TodoItemAdapter(
    private val onCheck: (TodoItem) -> Unit,
    private val onDelete: (TodoItem) -> Unit
) :
    ListAdapter<TodoItem, TodoItemAdapter.ViewHolder>(DiffCallback) {
    class ViewHolder(
        view: View,
        val onCheck: (TodoItem) -> Unit,
        val onDelete: (TodoItem) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.todo_item_content)
        private val checkBox: CheckBox = view.findViewById(R.id.todo_item_check_box)
        private val deleteButton: ImageButton = view.findViewById(R.id.todo_item_delete_button)

        fun bind(item: TodoItem) {
            textView.text = item.content
            deleteButton.setOnClickListener {
                onDelete(item)
            }
            checkBox.isChecked = !item.pending
            checkBox.setOnCheckedChangeListener { _, b ->
                onCheck(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item, parent, false)
        return ViewHolder(view, onCheck, onDelete)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TodoItem>() {
        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem.content == newItem.content
        }
    }
}
