package com.oursky.todolist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_todo_list.*

class TodoListFragment: Fragment(), TodoListAdapter.TodoListDelegate {
    private lateinit var mTodoListViewModel: TodoListViewModel
    private lateinit var mAdapter: TodoListAdapter
    private lateinit var mSharePreferenceStore: SharedPreferencesStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSharePreferenceStore = SharedPreferencesStore.fromContext(context)

        mAdapter = TodoListAdapter()
        mAdapter.setDelegate(this@TodoListFragment)

        mTodoListViewModel = ViewModelProviders.of(this).get(TodoListViewModel::class.java)
        mTodoListViewModel.get().observe(this, Observer {
            it?.let {
                mAdapter.setTodos(ArrayList(it.filter { it -> !it.finished }))
                mAdapter.notifyDataSetChanged()
            }
        })

        mTodoListViewModel.restoreFromCopy(mSharePreferenceStore.getTodoList())
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_todo_list,
                container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todo_list.adapter = mAdapter
        todo_list.layoutManager = LinearLayoutManager(context)

        fab.setOnClickListener { _ ->
            AlertDialog.Builder(context).apply {
                setTitle(R.string.create_todo)
                val customView = LayoutInflater.from(context).inflate(R.layout.dialog_create_todo, null)
                val createTodoEditText = customView.findViewById<EditText>(R.id.create_todo_edittext)
                setView(customView)
                setNeutralButton(R.string.phrase_ok, { dialog, _ ->
                    val todoText = createTodoEditText.text.toString()
                    if (todoText.isNotEmpty()) {
                        val todos = mTodoListViewModel.addTodo(TodoModel(mAdapter.itemCount, todoText, false))
                        mSharePreferenceStore.setTodoList(todos)
                    }
                    dialog.dismiss()
                })
            }.create().show()
        }
    }

    override fun onFinished(id: Int, text: String) {
        val todos = mTodoListViewModel.setTodo(TodoModel(id, text, true))
        mSharePreferenceStore.setTodoList(todos)
    }
}