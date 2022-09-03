package com.example.mytodoapp.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.data.Todo
import com.example.mytodoapp.data.TodoRepository
import com.example.mytodoapp.events.TodoListEvents
import com.example.mytodoapp.util.Routes
import com.example.mytodoapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoRepository: TodoRepository
): ViewModel() {
    val todos = todoRepository.getTodos()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedTodo: Todo? = null

    fun onEvent(todoListEvents: TodoListEvents){
        when(todoListEvents){
            is TodoListEvents.OnDeleteTodo ->{
                viewModelScope.launch {
                    deletedTodo = todoListEvents.todo
                    todoRepository.deleteTodo(
                        todoListEvents.todo
                    )
                    sendUiEvent(UiEvent.ShowSnackBar(
                        message = "Todo Deleted",
                        action = "undo"
                    ))
                }
            }
            is TodoListEvents.OnTodoClick ->{
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${todoListEvents.todo.id}"))
            }
            is TodoListEvents.OnAddTodoClick ->{
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvents.OnDoneChange ->{
                viewModelScope.launch {
                    todoRepository.insertTodo(
                        todoListEvents.todo.copy(
                            isDone = todoListEvents.isDone
                        )
                    )
                }
            }
            is TodoListEvents.OnUndoDeleteClick ->{
                deletedTodo?.let { todo ->
                    viewModelScope.launch {
                        todoRepository.insertTodo(todo)
                    }
                }
            }
        }
    }
    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}