package com.app.todolist.models

import java.util.*

data class Todo (
    val id: Long = 0,
    val title: String? = null,
    val description: String? = null,
    val date : Date? = null,
    val category : String? = null,
    val priority : String? = null,
    val isCompleted: Boolean = false
)