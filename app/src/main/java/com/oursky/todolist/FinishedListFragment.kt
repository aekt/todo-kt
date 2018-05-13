package com.oursky.todolist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_finished_list.*

class FinishedListFragment: Fragment() {
    private lateinit var mTodoListViewModel: TodoListViewModel
    private lateinit var mAdapter: FinishedListAdapter
    private lateinit var mSharePreferenceStore: SharedPreferencesStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSharePreferenceStore = SharedPreferencesStore.fromContext(context)

        mAdapter = FinishedListAdapter()

        mTodoListViewModel = ViewModelProviders.of(this).get(TodoListViewModel::class.java)
        mTodoListViewModel.get().observe(this, Observer {
            it?.let {
                mAdapter.setTodos(ArrayList(it.filter { it -> it.finished }))
                mAdapter.notifyDataSetChanged()
            }
        })

        mTodoListViewModel.restoreFromCopy(mSharePreferenceStore.getTodoList())
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_finished_list,
                container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finished_list.adapter = mAdapter
        finished_list.layoutManager = LinearLayoutManager(context)
    }
}