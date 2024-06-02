package com.oursky.todolist

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class FinishedListViewModel : ViewModel() {

    private val _list = MutableLiveData<List<TodoItem>?>()
    val list: LiveData<List<TodoItem>?> get() = _list.map { data -> data?.filter { item -> !item.pending } }

    fun loadFromSharedPreference(sharedPreferences: SharedPreferences) {
        val store = SharedPreferencesStore(sharedPreferences)
        _list.value = TodoItem.tryParse(store.get("todo-items")) ?: listOf()
    }

    fun saveToSharedPreference(sharedPreferences: SharedPreferences) {
        val store = SharedPreferencesStore(sharedPreferences)
        store.set("todo-items", TodoItem.format(_list.value ?: listOf()))
    }

    fun toggle(item: TodoItem) {
        _list.value = _list.value?.map { v ->
            if (v == item) {
                TodoItem(v.content, !v.pending, v.timestamp)
            } else {
                v
            }
        }
    }

    fun remove(item: TodoItem) {
        _list.value = _list.value?.filter { v -> v != item }
        println()
    }
}
