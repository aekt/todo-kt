package com.oursky.todolist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_todo.view.*


class TodoListAdapter(private val todoList: ArrayList<TodoModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ToDoItemViewHolder(view: View)  : RecyclerView.ViewHolder(view) {
        val checkBox = view.item_finish_checkbox
        val textView = view.item_text
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {

        val toDoItemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_todo, parent, false)
        return ToDoItemViewHolder(toDoItemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val toDoItemViewHolder = holder as ToDoItemViewHolder
        toDoItemViewHolder.textView.text = todoList[position].text
    }

    override fun getItemCount() = todoList.size
}
