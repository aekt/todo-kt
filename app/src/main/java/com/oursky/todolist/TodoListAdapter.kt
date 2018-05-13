package com.oursky.todolist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import kotlinx.android.synthetic.main.item_todo.view.*


class TodoListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface TodoListDelegate {
        fun onFinished(id: Int, text: String)
    }

    private var mTodos = ArrayList<TodoModel>()
    private var mDelegate: TodoListDelegate? = null

    class ToDoItemViewHolder(view: View, adapter: TodoListAdapter) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.item_finish_checkbox
        val textView: TextView = view.item_text
        init {
            checkBox.setOnClickListener {
                adapter.notifyFinished(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {

        val toDoItemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_todo, parent, false)
        return ToDoItemViewHolder(toDoItemView, this)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val toDoItemViewHolder = holder as ToDoItemViewHolder
        toDoItemViewHolder.textView.text = mTodos[position].text
        toDoItemViewHolder.checkBox.isChecked = mTodos[position].finished
    }

    override fun getItemCount() = mTodos.size

    fun setTodos(todos: ArrayList<TodoModel>) {
        mTodos = todos
    }

    fun setDelegate(delegate: TodoListDelegate) {
        mDelegate = delegate
    }

    fun notifyFinished(pos: Int) {
        val todo = mTodos[pos]
        mDelegate?.onFinished(todo.id, todo.text)
    }
}
