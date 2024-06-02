package com.oursky.todolist

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Instant

@Serializable
class TodoItem(
    val content: String,
    var pending: Boolean,
    val timestamp: Long = Instant.now().epochSecond
) {
    companion object {
        fun format(items: List<TodoItem>): String {
            return Json.encodeToString(items)
        }

        fun tryParse(value: String): List<TodoItem>? {
            return try {
                Json.decodeFromString<List<TodoItem>>(value)
            } catch (_: Exception) {
                null
            }
        }
    }
}
