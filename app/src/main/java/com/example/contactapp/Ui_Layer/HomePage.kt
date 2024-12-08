package com.example.contactapp.Ui_Layer

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.contactapp.Navigation.AddContact
import com.example.contactapp.States.ContactState
import com.example.contactapp.ViewModels.ContactViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavHostController, viewModel: ContactViewModel, state: ContactState) {

    var searchContact by remember {
        mutableStateOf("")
    }

    val bottomNavItemsList = listOf(
        BottomNavItems("Home", Icons.Default.Home),
        BottomNavItems("Favorite", Icons.Default.Favorite),
    )

    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    Scaffold(
        topBar = {
            var expended by remember {
                mutableStateOf(false)
            }
            TopAppBar(
                modifier = Modifier
                    .padding(top = 16.dp),

                title = {
                    OutlinedTextField(
                        value = searchContact,
                        onValueChange = {
                            searchContact = it
                        },
                        textStyle = TextStyle(
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif,
                        ),
                        modifier = Modifier
                            .height(76.dp)
                            .fillMaxWidth()
                            .padding(10.dp)
                            .border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp)),
                        placeholder = {
                            Text("Search Contacts")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search contacts",
                                modifier = Modifier
                                    .padding( start = 10.dp)
                            )

                        },
                        trailingIcon = {

                            if(searchContact=="") {
                                IconButton(
                                    onClick = {
                                        expended = !expended
                                    },
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.MoreVert,
                                        contentDescription = "More options",

                                        )
                                }

                                DropdownMenu(
                                    expanded = expended,
                                    onDismissRequest = { expended = false },
                                    modifier = Modifier
                                        .padding(end = 20.dp)
                                ) {
                                    DropdownMenuItem(
                                        text = {
                                            Text(text = "Option A")
                                        },
                                        onClick = {
                                            expended = !expended
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = {
                                            Text(text = "Option B")
                                        },
                                        onClick = {
                                            expended = !expended
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = {
                                            Text(text = "Sorting by Date")
                                        },
                                        onClick = {
                                            viewModel.doSortingByDate()
                                            expended = !expended
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = {
                                            Text(text = "Sorting by  Name")
                                        },
                                        onClick = {
                                            viewModel.doSortingByName()
                                            expended = !expended
                                        }
                                    )
                                }
                            }else{
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear search value",
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                        .clickable {
                                            searchContact=""
                                        }

                                )
                            }
                        },

                        shape = RoundedCornerShape(20.dp)

                        )
                },


                )
        },
        containerColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    state.id.value = 0
                    state.name.value = ""
                    state.number.value = ""
                    state.address.value = ""
                    state.email.value = ""
                    state.dateOfCreation.value = 0
                    state.isActive.value = true
                    state.isBlock.value = false
                    state.isFavorite.value = false
                    state.image.value=ByteArray(0)
                    navController.navigate(AddContact)
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add new contact",
                    )
            }

        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            BottomAppBar {
                bottomNavItemsList.forEachIndexed { index, bottomNavItems ->

                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        icon = {
                            Icon(imageVector = bottomNavItems.icon, contentDescription = "")
                        },
                        label = {
                            Text(text = bottomNavItems.label)
                        }
                    )
                }
            }
        }

    ) {paddingValues ->

        when(selectedItem){
            0-> AllContacts(state=state,viewModel=viewModel,navController = navController,paddingValues = paddingValues,searchContact=searchContact)
            1->  FavoriteContacts(state=state,viewModel=viewModel,navController = navController,paddingValues = paddingValues,searchContact=searchContact)
        }

    }

}

data class BottomNavItems(
    val label: String,
    val icon: ImageVector,
)