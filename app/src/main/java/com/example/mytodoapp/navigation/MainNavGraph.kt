package com.example.mytodoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mytodoapp.data.Todo
import com.example.mytodoapp.screens.AddEditScreen
import com.example.mytodoapp.screens.AnimatedSplashScreen
import com.example.mytodoapp.screens.TodoListScreen
import com.example.mytodoapp.util.Routes

@Composable
fun MainNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.SPLASH
    ) {
        composable(route = Routes.SPLASH){
            AnimatedSplashScreen(navHostController)
        }
        composable(route = Routes.TODO_LIST){
            TodoListScreen(onNavigate = {
                navHostController.navigate(it.route)
            })
        }
        composable(
            route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
            arguments = listOf(
                navArgument(name = "todoId"){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            AddEditScreen(
                onPopBackStack = {
                    navHostController.popBackStack()
                })
        }
    }
}