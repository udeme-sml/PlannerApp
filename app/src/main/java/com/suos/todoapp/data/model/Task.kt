package com.suos.todoapp.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var task: String,
    var isCompleted: Boolean = false,
    var priorityLevel: PriorityLevel? = null,
    var TaskType: TaskType? = null,
    var dueDate: Long? = null, // Store timestamp for date
    var subtasks: List<String>? = null, // Simple text-based subtasks
    var recurrence: Recurrence? = null
)

enum class PriorityLevel { LOW, MEDIUM, HIGH }
enum class TaskType { ROUTINE, TASK, EVENT }
enum class Recurrence { NONE, DAILY, WEEKLY, MONTHLY }

