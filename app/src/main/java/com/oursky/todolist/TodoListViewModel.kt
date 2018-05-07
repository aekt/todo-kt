package com.oursky.todolist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class TodoListViewModel : ViewModel() {
    private val mData = MutableLiveData<ArrayList<TodoModel>>()
    fun get(): LiveData<ArrayList<TodoModel>> {
        return mData
    }
    fun restoreFromCopy(todos: ArrayList<TodoModel>) {
        mData.value = todos
    }
}