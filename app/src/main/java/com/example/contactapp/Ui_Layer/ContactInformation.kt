package com.example.contactapp.Ui_Layer

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.contactapp.Navigation.AddContact
import com.example.contactapp.Navigation.HomeScreen
import com.example.contactapp.R
import com.example.contactapp.States.ContactState
import com.example.contactapp.ViewModels.ContactViewModel
import com.example.contactapp.ui.theme.charcoal_Gray
import com.example.contactapp.ui.theme.light_Beige

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactInformation(
    state: ContactState,
    viewModel: ContactViewModel,
    navController: NavHostController,

    ) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "")
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back to home page",
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(30.dp)
                            .clickable {
                                navController.navigateUp()
                            }

                    )
                },
                actions = {
                    var favoriteIcon by remember {
                        mutableIntStateOf(R.drawable.outline_star)
                    }
                    var isFavoriteContact = state.isFavorite.value
                    if (isFavoriteContact) {
                        favoriteIcon = R.drawable.filled_star
                    } else {
                        favoriteIcon = R.drawable.outline_star
                    }

                    //<---------------------Edit Icon------------------->

                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit Button",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                state.id.value = state.id.value
                                state.name.value = state.name.value
                                state.number.value = state.number.value
                                state.address.value = state.address.value
                                state.email.value = state.email.value
                                state.dateOfCreation.value = state.dateOfCreation.value
                                state.isActive.value = state.isActive.value
                                state.isBlock.value = state.isBlock.value
                                state.isFavorite.value = state.isFavorite.value
                                navController.navigate(AddContact)
                            },
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black

                    )

                    Spacer(Modifier.width(25.dp))

                    //<-------------------favoriteIcon--------------------->

                    Icon(
                        painter = painterResource(favoriteIcon),
                        contentDescription = "Favorite",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                if (isFavoriteContact) {

                                    state.id.value = state.id.value
                                    state.name.value = state.name.value
                                    state.number.value = state.number.value
                                    state.address.value = state.address.value
                                    state.email.value = state.email.value
                                    state.dateOfCreation.value = state.dateOfCreation.value
                                    state.isActive.value = state.isActive.value
                                    state.isBlock.value = state.isBlock.value
                                    state.isFavorite.value = false

                                    viewModel.saveContact()
                                    favoriteIcon = R.drawable.outline_star
                                    navController.navigate(HomeScreen)

                                } else {

                                    state.id.value = state.id.value
                                    state.name.value = state.name.value
                                    state.number.value = state.number.value
                                    state.address.value = state.address.value
                                    state.email.value = state.email.value
                                    state.dateOfCreation.value = state.dateOfCreation.value
                                    state.isActive.value = state.isActive.value
                                    state.isBlock.value = state.isBlock.value
                                    state.isFavorite.value = true
                                    viewModel.saveContact()
                                    favoriteIcon = R.drawable.filled_star
                                    navController.navigate(HomeScreen)


                                }
                            },
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black

                    )

                    Spacer(Modifier.width(25.dp))

                    //<-----------------More Option Icon------------>

                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = "More option Button",
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .size(30.dp)
                            .clickable {

                            },
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black

                    )


                }
            )
        },
        containerColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            //<----------------Box for Picture taking------------------>

            Spacer(Modifier.height(50.dp))

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
            var bitmap: Bitmap? = null
            if (state.image != null) {
                bitmap =
                    BitmapFactory.decodeByteArray(state.image.value, 0, state.image.value!!.size)

            }


            Box(
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(shape = RoundedCornerShape(50))
                    .background(randomColor)
                    .clickable {
                        state.id.value = state.id.value
                        state.name.value = state.name.value
                        state.number.value = state.number.value
                        state.address.value = state.address.value
                        state.email.value = state.email.value
                        state.dateOfCreation.value = state.dateOfCreation.value
                        state.isActive.value = state.isActive.value
                        state.isBlock.value = state.isBlock.value
                        state.isFavorite.value = state.isFavorite.value
                        navController.navigate(AddContact)
                    },
                //.border(width = 3.dp, color = Color.Black, shape = RoundedCornerShape(50)),
                contentAlignment = Alignment.Center
            ) {
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "User-image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(50))
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Picture",
                        modifier = Modifier
                            .size(80.dp),
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                }

            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = state.name.value,
                style = TextStyle(
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                )
            )


            Spacer(Modifier.height(40.dp))

            //<------------------- Giving Call,Chat,Video Call options------------>

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // <-----------Call Icon------------------>

                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Icon(
                        imageVector = Icons.Filled.Call,
                        contentDescription = "Call Icon",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_CALL)
                                intent.data = Uri.parse("tel:${state.number.value}")
                                context.startActivity(intent)
                            },
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )



                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = "Call",
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
                            .size(30.dp)
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

                //<---------------------Video Call Icon------------->

                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.video),
                        contentDescription = "Call Icon",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                val phoneNumber = state.number.value

//                                val intent = Intent(Intent.ACTION_CALL)
//                                intent.data = Uri.parse("tel:${ state.number.value}")
//                                context.startActivity(intent)

//                                val intent = Intent("com.android.phone.videocall").apply {
//                                    data = Uri.parse("tel:$phoneNumber") // This opens WhatsApp with the number
//                                    putExtra("videoCall", true)
//                                }
//
//                                if (intent.resolveActivity(context.packageManager) != null) {
//                                    context.startActivity(intent)
//                                } else {
//                                    Toast.makeText(context, "No application available to handle video calls.", Toast.LENGTH_SHORT).show()
//                                }
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

            }

            Spacer(Modifier.height(30.dp))

            //<------------------- Giving  Number, Address and Email details ------------>


            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(20.dp),
                colors = CardColors(
                    containerColor = if (isSystemInDarkTheme()) charcoal_Gray else light_Beige,
                    contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    disabledContentColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    disabledContainerColor = if (isSystemInDarkTheme()) charcoal_Gray else light_Beige,
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 5.dp,
                    disabledElevation = 0.dp,
                    focusedElevation = 5.dp,
                    hoveredElevation = 5.dp,
                    draggedElevation = 5.dp,
                )

            ) {
                Spacer(Modifier.height(20.dp))

                //<---------------------- Number ---------------------->

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),

                    ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector = Icons.Filled.Call,
                            contentDescription = "Number of Contact",
                            modifier = Modifier
                                .padding(start = 25.dp, end = 20.dp)
                                .size(30.dp)

                        )
                    }

                    Spacer(Modifier.width(10.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.9f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start

                    ) {

                        Text(
                            text = state.number.value,
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                            )
                        )
                    }


                }

                Spacer(Modifier.height(20.dp))


                //<---------------------- Email ---------------------->

                if (state.email.value == "") {
                    OutlinedButton(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(0.8f)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            state.id.value = state.id.value
                            state.name.value = state.name.value
                            state.number.value = state.number.value
                            state.address.value = state.address.value
                            state.email.value = state.email.value
                            state.dateOfCreation.value = state.dateOfCreation.value
                            state.isActive.value = state.isActive.value
                            state.isBlock.value = state.isBlock.value
                            state.isFavorite.value = state.isFavorite.value
                            navController.navigate(AddContact)
                        }
                    ) {
                        Text(text = "Add Email")
                    }
                } else {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = "Share Contact",
                                modifier = Modifier
                                    .padding(start = 25.dp, end = 20.dp)
                                    .size(30.dp)

                            )
                        }

                        Spacer(Modifier.width(10.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.9f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start

                        ) {

                            Text(
                                text = state.email.value,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                                )
                            )
                        }


                    }
                }

                Spacer(Modifier.height(20.dp))

                //<---------------------- Address ---------------------->

                if (state.address.value == "") {
                    OutlinedButton(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(0.8f)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            state.id.value = state.id.value
                            state.name.value = state.name.value
                            state.number.value = state.number.value
                            state.address.value = state.address.value
                            state.email.value = state.email.value
                            state.dateOfCreation.value = state.dateOfCreation.value
                            state.isActive.value = state.isActive.value
                            state.isBlock.value = state.isBlock.value
                            state.isFavorite.value = state.isFavorite.value
                            navController.navigate(AddContact)
                        }
                    ) {
                        Text(text = "Add  Address")
                    }
                } else {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "Location of  Contact",
                                modifier = Modifier
                                    .padding(start = 25.dp, end = 20.dp)
                                    .size(30.dp)

                            )
                        }

                        Spacer(Modifier.width(10.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.9f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start

                        ) {

                            Text(
                                text = state.address.value,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Left,
                                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                                )
                            )
                        }


                    }
                }

                Spacer(Modifier.height(20.dp))


            }

            Spacer(Modifier.height(30.dp))

            //<-----------------Sharing Option--------------->

            // Create a message combining both name and phone number
            val message = "Name: ${state.name.value}\nPhone Number: ${state.number.value}"
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain" // Set the type to plain text
                        intent.putExtra(
                            Intent.EXTRA_TEXT,
                            message
                        ) // Add the combined message as extra text

// Check if there's an app available to handle the intent
                        if (intent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(
                                Intent.createChooser(
                                    intent,
                                    "Share Contact Info"
                                )
                            )
                        } else {
                            // Handle the case where no app is available to share the number
                            Toast
                                .makeText(
                                    context,
                                    "No app available to share contact info",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Share Contact",
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .size(30.dp),
                    tint = if (isSystemInDarkTheme()) Color.White else Color.Black,
                )

                Spacer(Modifier.width(10.dp))

                Text(
                    text = "Share Contact",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    )
                )
            }

            Spacer(Modifier.height(20.dp))

            //<-----------------Update Option--------------->

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .clickable {
                        state.id.value = state.id.value
                        state.name.value = state.name.value
                        state.number.value = state.number.value
                        state.address.value = state.address.value
                        state.email.value = state.email.value
                        state.dateOfCreation.value = state.dateOfCreation.value
                        state.isActive.value = state.isActive.value
                        state.isBlock.value = state.isBlock.value
                        state.isFavorite.value = state.isFavorite.value
                        navController.navigate(AddContact)
                    },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Update Contact",
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .size(30.dp),
                    tint = if (isSystemInDarkTheme()) Color.White else Color.Black,

                    )
                Spacer(Modifier.width(10.dp))

                Text(
                    text = "Update",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    )
                )
            }

            Spacer(Modifier.height(20.dp))

            //<----------------- Block Option--------------->

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .clickable {
                        if (state.isBlock.value == false) {
                            state.id.value = state.id.value
                            state.name.value = state.name.value
                            state.number.value = state.number.value
                            state.address.value = state.address.value
                            state.email.value = state.email.value
                            state.dateOfCreation.value = state.dateOfCreation.value
                            state.isActive.value = state.isActive.value
                            state.isBlock.value = true
                            state.isFavorite.value = state.isFavorite.value

                            viewModel.saveContact()
                            navController.navigate(HomeScreen)
                        } else {
                            state.id.value = state.id.value
                            state.name.value = state.name.value
                            state.number.value = state.number.value
                            state.address.value = state.address.value
                            state.email.value = state.email.value
                            state.dateOfCreation.value = state.dateOfCreation.value
                            state.isActive.value = state.isActive.value
                            state.isBlock.value = false
                            state.isFavorite.value = state.isFavorite.value

                            viewModel.saveContact()
                            navController.navigate(HomeScreen)
                        }
                    },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.block),
                    contentDescription = "Block Contact",
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .size(30.dp),
                    tint = if (state.isBlock.value) {
                        Color.Red
                    } else {
                        if (isSystemInDarkTheme()) Color.White else Color.Black
                    },
                )

                Spacer(Modifier.width(10.dp))

                var blockNumberText by remember {
                    mutableStateOf("Block Number")
                }
                if (state.isBlock.value) {
                    blockNumberText = "Unblock Number"
                } else {
                    blockNumberText = "Block Number"
                }

                Text(
                    text = blockNumberText,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = if (state.isBlock.value) {
                            Color.Red
                        } else {
                            if (isSystemInDarkTheme()) Color.White else Color.Black
                        },
                    )
                )
            }

            Spacer(Modifier.height(20.dp))

            //<----------------- Delete Option--------------->

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .clickable {

                        state.id.value = state.id.value
                        state.name.value = state.name.value
                        state.number.value = state.number.value
                        state.address.value = state.address.value
                        state.email.value = state.email.value
                        state.dateOfCreation.value = state.dateOfCreation.value
                        state.isActive.value = state.isActive.value
                        state.isBlock.value = state.isBlock.value
                        state.isFavorite.value = state.isFavorite.value

                        viewModel.deleteContact()
                        navController.navigate(HomeScreen)
                    },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Contact",
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .size(30.dp)

                )
                Spacer(Modifier.width(10.dp))

                Text(
                    text = "Delete Number",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    )
                )
            }


        }
    }

}