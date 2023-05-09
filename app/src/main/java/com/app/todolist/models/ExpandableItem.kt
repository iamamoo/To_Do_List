package com.app.todolist.models

data class ExpandableItem(
    val title : String? = null,
    val description : String? = null,
    var isExpandable : Boolean = false
)