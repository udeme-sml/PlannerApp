package com.suos.todoapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.*
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavHostController
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost

@Composable
fun HomeScreen(navController: NavController, taskDAO: TaskDAO) {
    val viewModelFactory = TaskViewModelFactory(taskDAO)
    val taskViewModel: TaskViewModel = viewModel(factory = viewModelFactory)
    var showDialog by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }
    val tasks by taskViewModel.tasks.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    taskToEdit = null
                    showDialog = true
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Ensure proper spacing
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Home Screen",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(tasks) { task ->
                    TaskDisplay(
                        task = task,
                        onTaskClick = { taskViewModel.toggleTaskCompletion(task) },
                        onEdit = {
                            taskToEdit = task
                            showDialog = true
                        },
                        onDelete = { taskViewModel.removeTask(task) }
                    )
                }
            }
        }
    }

    if (showDialog) {
        AddTaskDialog(
            onDismiss = { showDialog = false },
            taskViewModel = taskViewModel,
            taskToEdit = taskToEdit
        )
    }
}

@Composable
fun AddTaskDialog(onDismiss: () -> Unit, taskViewModel: TaskViewModel, taskToEdit: Task?) {
    var taskText by remember { mutableStateOf(taskToEdit?.task ?: "") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = if (taskToEdit == null) "Add New Task" else "Edit Task") },
        text = {
            Column {
                OutlinedTextField(
                    value = taskText,
                    onValueChange = { taskText = it },
                    label = { Text("Task Name") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (taskToEdit == null) {
                        taskViewModel.addTask(taskText)
                    } else {
                        taskViewModel.editTask(taskToEdit, taskText)
                    }
                    onDismiss()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}


@Composable
fun TaskDisplay(task: Task, onTaskClick: () -> Unit, onEdit: () -> Unit, onDelete: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(100.dp)
            .border(
                2.dp,
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onTaskClick() }
            )

            Text(
                text = task.task,
                fontSize = 16.sp,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                maxLines = 1, // Ensures truncation
                overflow = TextOverflow.Ellipsis, // Adds "..." if text is too long
                modifier = Modifier.weight(1f) // Pushes buttons to the right
            )

            IconButton(onClick = onEdit) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Task")
            }

            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Task")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview(){
    val navController = rememberNavController()
    HomeScreen(
        navController = navController,
        taskDAO = TODO(),
    )
}