package com.example.mytodoapp.di

import android.app.Application
import androidx.room.Room
import com.example.mytodoapp.data.TodoDatabase
import com.example.mytodoapp.data.TodoRepository
import com.example.mytodoapp.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideTodoDatabase(application: Application): TodoDatabase{
        return Room.databaseBuilder(
            application,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideTodoRepository(todoDatabase: TodoDatabase): TodoRepository{
        return TodoRepositoryImpl(todoDatabase.todoDao)
    }
}