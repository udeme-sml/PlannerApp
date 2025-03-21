package com.suos.todoapp.features.todo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.*
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.*
import androidx.navigation.compose.rememberNavController


@Composable
fun AddTaskScreen(navController: NavController) {
    var showTaskInput by remember { mutableStateOf(false) }  // Track visibility

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Tasks",
                color = Color(0xFF661010),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))

            if (showTaskInput) { // Show the input field if button is clicked
                AddTask()
            }

            Spacer(modifier = Modifier.weight(.8f))

            // Button toggles the input field
            AddTaskButton(
                onClick = { showTaskInput = !showTaskInput },
                modifier = Modifier.size(50.dp).align(Alignment.End)
            )

            Button(onClick = { navController.navigate("HomeScreen") }) {
                Text("Home")
            }
        }
    }
}


@Composable
fun AddTaskButton(onClick: () -> Unit, modifier: Modifier) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text("+", fontSize = 24.sp)
    }
}


@Composable
fun AddTask(){
    var text by remember { mutableStateOf("")}
    
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Add Task")}
    )
}

@Preview(showBackground = true)
@Composable
fun AddTaskPreview(){
    val navController = rememberNavController()
    AddTaskScreen(navController = navController)
}