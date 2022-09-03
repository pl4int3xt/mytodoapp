package com.example.mytodoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Todo::class],
    version = 1
)
abstract class TodoDatabase: RoomDatabase() {
    abstract val todoDao: TodoDao

    companion object{
        const val DATABASE_NAME = "todo_db"
    }
}