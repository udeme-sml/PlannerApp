package com.suos.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suos.todoapp.data.dao.TaskDAO
import com.suos.todoapp.data.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDAO: TaskDAO) : ViewModel() {

    // MutableStateFlow to observe database changes
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks // Read-only version

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            taskDAO.getAllTasks().collect { taskList ->
                _tasks.value = taskList
            }
        }
    }

    fun addTask(taskText: String) {
        viewModelScope.launch {
            taskDAO.addTask(Task(id = 0, task = taskText))
        }
    }

    fun removeTask(task: Task) {
        viewModelScope.launch {
            taskDAO.deleteTask(task)
        }
    }

    fun editTask(task: Task, newTask: String) {
        viewModelScope.launch {
            taskDAO.updateTask(task.copy(task = newTask))
        }
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            taskDAO.updateTask(task.copy(isCompleted = !task.isCompleted))
        }
    }
}
