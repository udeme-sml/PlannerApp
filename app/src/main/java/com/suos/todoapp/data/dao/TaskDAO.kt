package com.suos.todoapp.data.dao
import androidx.room.*
import com.suos.todoapp.data.model.Task

@Dao
interface TaskDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAllTasks(): kotlinx.coroutines.flow.Flow<List<Task>>

    @Update
    suspend fun updateTask(task: Task)
}