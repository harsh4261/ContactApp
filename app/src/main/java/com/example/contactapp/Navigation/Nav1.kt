package com.example.contactapp.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contactapp.Ui_Layer.AddEditScreen
import com.example.contactapp.Ui_Layer.ContactHistoryData
import com.example.contactapp.Ui_Layer.ContactInformation
import com.example.contactapp.ViewModels.ContactViewModel
import com.example.contactapp.Ui_Layer.HomePage
import kotlinx.serialization.Serializable

@Composable
fun Nav1(viewModel: ContactViewModel) {

    val state by viewModel.state.collectAsState()

    val navController = rememberNavController()

    NavHost(navController=navController, startDestination =  HomeScreen) {
        composable<HomeScreen>{
            HomePage(state=state,viewModel=viewModel,navController = navController)

        }
        composable<AddContact> {
            AddEditScreen(state = state, navController = navController){
                viewModel.saveContact()
            }
        }
        composable<contactInformation> {
            ContactInformation(state=state,viewModel=viewModel,navController = navController,)
        }
        composable<contactHistoryData> {
             ContactHistoryData(state=state,viewModel=viewModel,navController = navController)
        }
    }
}

@Serializable
object HomeScreen

@Serializable
object AddContact

@Serializable
object contactInformation

@Serializable
object contactHistoryData



