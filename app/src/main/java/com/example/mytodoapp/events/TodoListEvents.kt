package com.example.mytodoapp.events

import com.example.mytodoapp.data.Todo

sealed class TodoListEvents{
    data class OnDeleteTodo(val todo: Todo): TodoListEvents()
    data class OnDoneChange(val todo: Todo, val isDone: Boolean): TodoListEvents()
    object OnUndoDeleteClick: TodoListEvents()
    data class OnTodoClick(val todo: Todo): TodoListEvents()
    object OnAddTodoClick: TodoListEvents()
}
