package com.example.mytodoapp.events

sealed class AddEditTodoEvents{
    data class OnTitleChange(val title: String): AddEditTodoEvents()
    data class OnDescriptionChange(val description: String): AddEditTodoEvents()
    object OnSaveTodoClick: AddEditTodoEvents()
}
