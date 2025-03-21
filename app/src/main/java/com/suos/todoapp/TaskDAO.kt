package com.suos.todoapp
import androidx.room.*

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