package com.app.todolist.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.todolist.models.TodoItem

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItem(todoItem: TodoItem)

    @Update
    suspend fun updateTodoItem(todoItem: TodoItem)

    @Delete
    suspend fun deleteTodoItem(todoItem: TodoItem)

    @Query(value = "SELECT * FROM todo_items")
    suspend fun getAllTodoItems(): List<TodoItem>

    @Query("SELECT * FROM todo_items ORDER BY CASE priority WHEN 'High' THEN 1 WHEN 'Medium' THEN 2 WHEN 'Low' THEN 3 END")
    suspend fun getAccordingToPriority() : List<TodoItem>

}