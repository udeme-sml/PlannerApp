package com.suos.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.suos.todoapp.ui.theme.ToDoAppTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase

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
        { HomeScreen(navController, taskDAO)}
        composable("AddTaskScreen")
        { AddTaskScreen(navController)}
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