package com.suos.todoapp
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var task: String,
    var isCompleted: Boolean = false
)

