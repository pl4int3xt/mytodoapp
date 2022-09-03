package com.example.mytodoapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mytodoapp.data.Todo
import com.example.mytodoapp.events.TodoListEvents
import com.example.mytodoapp.view_models.TodoListViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun TodoItem(
    todo: Todo,
    onEvent: (TodoListEvents) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TodoListViewModel = hiltViewModel()
) {

    val delete = SwipeAction(
        onSwipe = {
            viewModel.onEvent(TodoListEvents.OnDeleteTodo(todo))
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete",
                tint = Color.White
            )
        },
        background = Color.Blue
    )

    SwipeableActionsBox(
        swipeThreshold = 50.dp,
        startActions = listOf(delete),
        endActions = listOf(delete),
        backgroundUntilSwipeThreshold = Color.White
    ) {
        Surface(
            modifier = Modifier.clip(RoundedCornerShape(10.dp))
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
            ){
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = todo.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    todo.description?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = it)
                    }
                }
                Checkbox(
                    checked = todo.isDone,
                    onCheckedChange = { isChecked ->
                        onEvent(TodoListEvents.OnDoneChange(todo, isChecked))
                    }
                )
            }
        }
    }
}