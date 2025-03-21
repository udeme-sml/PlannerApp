package com.suos.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.suos.todoapp.ui.theme.ToDoAppTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.room.Room
import com.suos.todoapp.data.dao.TaskDAO
import com.suos.todoapp.data.database.TaskDatabase
import com.suos.todoapp.features.todo.AddTaskScreen
import com.suos.todoapp.features.todo.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java, "task_database"
        ).build()
        setContent {
            ToDoAppTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController, database.taskDAO())
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, taskDAO: TaskDAO) {
    NavHost(navController = navController, startDestination = "HomeScreen"){
        composable("HomeScreen")
        { HomeScreen(navController, taskDAO) }
        composable("AddTaskScreen")
        { AddTaskScreen(navController) }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoAppTheme {
        val navController = rememberNavController()
        AppNavHost(
            navController = navController,
            taskDAO = TODO()
        )
    }
}