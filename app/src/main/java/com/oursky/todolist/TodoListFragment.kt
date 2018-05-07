package com.oursky.todolist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_todo_list.*

class TodoListFragment: Fragment() {
    private lateinit var mTodoListViewModel: TodoListViewModel
    private var mTodos: ArrayList<TodoModel>? = null
    private var mAdapter: TodoListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mTodoListViewModel = ViewModelProviders.of(this).get(TodoListViewModel::class.java)
        mTodoListViewModel.get().observe(this, Observer {
            it?.let {
                if (mTodos == null) {
                    mTodos = it
                    mAdapter = TodoListAdapter(mTodos!!)
                    todo_list.adapter = mAdapter
                    todo_list.layoutManager = LinearLayoutManager(this.activity)!!
                }
            }
        })
        mTodoListViewModel.restoreFromCopy(
                SharedPreferencesStore.fromContext(this.activity).getTodoList())

        // Inflate the layout for this fragment
        return inflater?.inflate(R.layout.fragment_todo_list,
                container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener { _ ->

        }
    }
}