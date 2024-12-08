package com.example.contactapp.Ui_Layer

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.contactapp.Data.database.Contact
import com.example.contactapp.Navigation.AddContact
import com.example.contactapp.Navigation.contactHistoryData
import com.example.contactapp.Navigation.contactInformation
import com.example.contactapp.R
import com.example.contactapp.States.ContactState
import com.example.contactapp.ViewModels.ContactViewModel
import com.example.contactapp.ui.theme.charcoal_Gray
import com.example.contactapp.ui.theme.light_Beige
import java.util.Locale

@Composable
fun AllContacts(
    navController: NavHostController,
    viewModel: ContactViewModel,
    state: ContactState,
    paddingValues: PaddingValues,
    searchContact: String
){
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        // Create variable for filtering
        val filterList: List<Contact> = if (searchContact.isEmpty()) {
            state.contacts
        } else {
            state.contacts.filter { contact ->
                contact.name.lowercase(Locale.getDefault()).contains(searchContact.lowercase(Locale.getDefault()))
            }
        }

        items(filterList) {
            var bitmap : Bitmap?=null
            if(it.image != null){
                bitmap = BitmapFactory.decodeByteArray(it.image,0,it.image!!.size)
            }

            var clickedContact by remember {
                mutableStateOf(false)
            }


            val transition = updateTransition(
                targetState = clickedContact,
                label = "transition"
            )

            val boxHeight by transition.animateInt(
                transitionSpec = { tween(200) },
                label = "BoxHeight",
                targetValueByState = { if (it) 200 else 100 }
            )

            val profileWidth by transition.animateInt(
                transitionSpec = { tween(200) },
                label = "BoxHeight",
                targetValueByState = { if (it) 100 else 80 }
            )

            Column(
                modifier = Modifier
                    .height(boxHeight.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(10))
                    .background(if (isSystemInDarkTheme()) charcoal_Gray else light_Beige)

                    .clickable {
                        clickedContact = !clickedContact
                    },
            ) {

                Row(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    val lightColorList = listOf(
                        Color(0xFFFFC0CB), // Light Pink
                        Color(0xFFADD8E6), // Light Blue
                        Color(0xFFFFE4B5), // Light Goldenrod Yellow
                        Color(0xFFFFDAB9), // Peach Puff
                        Color(0xFFE6E6FA), // Lavender
                        Color(0xFFFFB6C1), // Light Salmon
                        Color(0xFFB0E0E6), // Powder Blue
                        Color(0xFFFFEFD5), // Papaya Whip
                    )
                    val randomIndex = (lightColorList.indices).random()
                    val randomColor by remember {
                        mutableStateOf(lightColorList[randomIndex])
                    }

                    Box(
                        modifier = Modifier
                            .width(profileWidth.dp)
                            .height(80.dp)
                            .padding(start = 20.dp)
                            .clip(shape = RoundedCornerShape(50))
                            .background(randomColor)
                            .padding(10.dp)
                            .clickable {

                                state.id.value=it.id
                                state.name.value=it.name
                                state.number.value=it.number
                                state.address.value=it.address
                                state.email.value=it.email
                                state.dateOfCreation.value=it.dateOfCreation
                                state.isActive.value=it.isActive
                                state.isBlock.value=it.isBlocked
                                state.isFavorite.value=it.isFavorite
                                state.image.value= it.image!!
                                navController.navigate(contactInformation)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if(bitmap != null){
                            Image(
                                bitmap=bitmap.asImageBitmap(),
                                contentDescription = "User-image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(50))
                            )
                        }else {
                            Text(
                                text = "" + it.name[0],
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = if (clickedContact) 20.sp else 16.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .fillMaxHeight()
                            .fillMaxWidth(0.8f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = it.name,

                            style = TextStyle(
                                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                                fontSize = if (clickedContact) 18.sp else 14.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = if (clickedContact) FontWeight.Bold else FontWeight.Normal
                            )
                        )

                        if (clickedContact) {
                            Text(
                                text = it.number,
                                modifier = Modifier
                                    .padding(top = 8.dp),
                                style = TextStyle(
                                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                                    fontSize = if (clickedContact) 16.sp else 14.sp,
                                    fontFamily = FontFamily.SansSerif,


                                    )
                            )

                        }
                    }

                    if (clickedContact) {
                        Icon(
                            imageVector = Icons.Filled.Call,
                            contentDescription = "Calling button",
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(30.dp)
                                .clickable {
                                    val intent = Intent(Intent.ACTION_CALL)
                                    intent.data = Uri.parse("tel:${it.number}")
                                    context.startActivity(intent)
                                },
                            tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )
                    }

                }

                if (clickedContact) {
                    Row(
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        //<----------------Favorite Button---------------------->
                        Column(
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            var favoriteIcon by remember {
                                mutableStateOf(R.drawable.outline_star)
                            }
                            var isFavoriteContact = it.isFavorite
                            if (isFavoriteContact) {
                                favoriteIcon = R.drawable.filled_star
                            } else {
                                favoriteIcon = R.drawable.outline_star
                            }
                            Icon(
                                painter = painterResource(favoriteIcon),
                                contentDescription = "Favorite",
                                modifier = Modifier
                                    .size(25.dp)
                                    .clickable {
                                        if (isFavoriteContact) {

                                            state.id.value=it.id
                                            state.name.value=it.name
                                            state.number.value=it.number
                                            state.address.value=it.address
                                            state.email.value=it.email
                                            state.dateOfCreation.value=it.dateOfCreation
                                            state.isActive.value=it.isActive
                                            state.isBlock.value=it.isBlocked
                                            state.image.value=it.image!!
                                            state.isFavorite.value=false

                                            viewModel.saveContact()
                                            favoriteIcon = R.drawable.outline_star

                                        } else {

                                            state.id.value=it.id
                                            state.name.value=it.name
                                            state.number.value=it.number
                                            state.address.value=it.address
                                            state.email.value=it.email
                                            state.dateOfCreation.value=it.dateOfCreation
                                            state.isActive.value=it.isActive
                                            state.isBlock.value=it.isBlocked
                                            state.image.value=it.image!!
                                            state.isFavorite.value=true

                                            viewModel.saveContact()
                                            favoriteIcon = R.drawable.filled_star

                                        }
                                    },
                                tint = if (isSystemInDarkTheme()) Color.White else Color.Black

                            )
                            Spacer(Modifier.height(5.dp))
                            Text(
                                text = "Favorite",
                                style = TextStyle(
                                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                                )
                            )
                        }

                        //<----------------Chat Button---------------------->

                        Column(
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.chat),
                                contentDescription = "Chat",
                                modifier = Modifier
                                    .size(25.dp)
                                    .clickable {
                                        val intent = Intent(Intent.ACTION_SENDTO)
                                        intent.data = Uri.parse("smsto:${state.number.value}")
                                        context.startActivity(intent)
                                    },
                                tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                            )
                            Spacer(Modifier.height(5.dp))
                            Text(
                                text = "Chat",
                                style = TextStyle(
                                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                                )
                            )
                        }

                        //<----------------History Button---------------------->


                        Column(
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.pen),
                                contentDescription = "History Button",
                                modifier = Modifier
                                    .size(25.dp)
                                    .clickable {
                                        navController.navigate(contactHistoryData)
                                    },
                                tint = if (isSystemInDarkTheme()) Color.White else Color.Black

                            )
                            Spacer(Modifier.height(5.dp))
                            Text(
                                text = "History",
                                style = TextStyle(
                                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                                )
                            )
                        }

                        //<----------------Update Button---------------------->


                        Column(
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Refresh,
                                contentDescription = "Edit Button",
                                modifier = Modifier
                                    .size(25.dp)
                                    .clickable {

                                        state.id.value=it.id
                                        state.name.value=it.name
                                        state.number.value=it.number
                                        state.address.value=it.address
                                        state.email.value=it.email
                                        state.dateOfCreation.value=it.dateOfCreation
                                        state.isActive.value=it.isActive
                                        state.isBlock.value=it.isBlocked
                                        state.isFavorite.value=it.isFavorite
                                        state.image.value=it.image!!

                                        navController.navigate(AddContact)

//
                                    },
                                tint = if (isSystemInDarkTheme()) Color.White else Color.Black

                            )
                            Spacer(Modifier.height(5.dp))
                            Text(
                                text = "Edit",
                                style = TextStyle(
                                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                                )
                            )
                        }
                    }
                }
            }


        }

    }

}