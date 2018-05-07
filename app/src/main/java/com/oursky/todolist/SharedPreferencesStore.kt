package com.oursky.todolist

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SharedPreferencesStore(sharedPreferences: SharedPreferences) {
    companion object {
        private val storeSuffix = ".SharedPreferencesStore"
        fun fromContext(context: Context): SharedPreferencesStore {
            val name = BuildConfig.APPLICATION_ID + storeSuffix
            val sp = context.getSharedPreferences(name, Context.MODE_PRIVATE)
            return SharedPreferencesStore(sp)
        }
    }

    private val mSharedPreferences = sharedPreferences
    private val mKeyTodoList = "todoList"

    fun getTodoList(): ArrayList<TodoModel> {
        val jsonString = mSharedPreferences.getString(mKeyTodoList, "")
        try {
            val output = ArrayList<TodoModel>()
            val jsonArray = JSONArray(jsonString)
            for (i in 0..(jsonArray.length() - 1)) {
                val obj = jsonArray.getJSONObject(i)
                when (obj) {
                    is JSONObject -> {
                        val t = obj.getString("text")
                        val f = obj.getBoolean("finished")
                        output.add(TodoModel(t, f))
                    }
                }
            }
            return output
        } catch (e: JSONException) {
            //
        }

        return ArrayList()
    }

    fun setTodoList(todos: ArrayList<TodoModel>) {
        val jsonArray = JSONArray()
        for (todo in todos) {
            val jo = JSONObject()
            jo.put("text", todo.text)
            jo.put("finished", todo.finished)
            jsonArray.put(jo)
        }
        val jsonString = jsonArray.toString()
        mSharedPreferences.edit().putString(mKeyTodoList, jsonString).apply()
    }

}