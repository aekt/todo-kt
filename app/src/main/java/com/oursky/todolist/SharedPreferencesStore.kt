package com.oursky.todolist

import android.content.SharedPreferences

class SharedPreferencesStore(private val sharedPreferences: SharedPreferences) {
    fun get(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    fun set(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }
}
