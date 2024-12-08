package com.example.contactapp.Ui_Layer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.contactapp.Navigation.HomeScreen
import com.example.contactapp.R
import com.example.contactapp.States.ContactState
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    state: ContactState,
    navController: NavController,
    onEvent: () -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri : Uri ?->
//        Log.d("URI",uri.toString())
        if(uri != null){
            val inputStream : InputStream ? = context.contentResolver.openInputStream(uri)
            val byteArray : ByteArray ? = inputStream?.readBytes()
            if(byteArray != null){
                state.image.value = byteArray
            }
            inputStream?.close()
        }

    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add New Contact",
                        modifier = Modifier
                            .padding(start = 15.dp)
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back to home page",
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(30.dp)
                            .clickable {
                                navController.navigate(HomeScreen)
                            }

                    )
                }
            )
        },
        containerColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                //<------------------------Button for saving the details------------------------>

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth(0.8f),
                        onClick = {
                            if (state.name.value != "" && state.number.value != "") {
                                onEvent.invoke()
                                state.name.value = ""
                                state.number.value = ""
                                state.email.value = ""
                                state.address.value=""
                                state.image.value=ByteArray(0)
                                navController.navigate(HomeScreen)

                            }
                        }
                    ) {
                        Text(
                            text = "Save",
                            style = TextStyle(
                                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                                fontSize = 16.sp,
                                fontFamily = FontFamily.SansSerif,

                                )
                        )
                    }
                }
            }
        }

    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            //<----------------Box for Picture taking------------------>

            Spacer(Modifier.height(20.dp))

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

            var bitmap : Bitmap?=null
            if(state.image != null){
                bitmap = BitmapFactory.decodeByteArray( state.image.value,0, state.image.value!!.size)

            }

            Box(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(shape = RoundedCornerShape(50))
                    .background(randomColor)
                    .clickable {
                        launcher.launch("image/*")
                    },
                //.border(width = 3.dp, color = Color.Black, shape = RoundedCornerShape(50)),
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
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Picture",
                        modifier = Modifier
                            .size(50.dp),
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                }

            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Add picture",
                modifier = Modifier
                    .clickable {
                        launcher.launch("image/*")
                    },
                style = TextStyle(
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                )
            )

            Spacer(Modifier.height(40.dp))

            //<-------------TextField for taking user name---------------->

            OutlinedTextField(
                value = state.name.value,
                onValueChange = {
                    state.name.value = it
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                label = {
                    Text(text = "Name")
                },
                placeholder = {
                    Text(text = "Enter Name")
                },
                maxLines = 1,
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Name"
                    )
                },
                trailingIcon = {
                    if (state.name.value != "") {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Name",
                            modifier = Modifier
                                .clickable {
                                    state.name.value = ""
                                }
                        )
                    }

                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )

            Spacer(Modifier.height(20.dp))

            //<-----------------Taking Phone-Number-------------------->

            OutlinedTextField(
                value = state.number.value,
                onValueChange = {
                    state.number.value = it
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                label = {
                    Text(text = "Number")
                },
                placeholder = {
                    Text(text = "Enter Phone Number")
                },
                maxLines = 1,
                singleLine = true,
                leadingIcon = {

                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "number"
                    )
                },
                trailingIcon = {
                    if (state.number.value != "") {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "number clear",
                            modifier = Modifier
                                .clickable {
                                    state.number.value = ""
                                }
                        )
                    }

                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(Modifier.height(20.dp))

            //<--------------Taking Email---------------------->

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center

            ) {

                var emailInput by remember {
                    mutableStateOf(true)
                }
                if (state.email.value!=""){
                    emailInput=false
                }

                if (emailInput) {
                    if (state.email.value == "" || emailInput) {
                        OutlinedButton(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth(0.8f)
                                .padding(top = 8.dp),
                            onClick = {
                                emailInput = !emailInput
                            }
                        ) {
                            Text(text = "Add Email")
                        }
                    }
                } else {

                    OutlinedTextField(
                        value = state.email.value,
                        onValueChange = {
                            state.email.value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        label = {
                            Text(text = "Email")
                        },
                        placeholder = {
                            Text(text = "Enter Email")
                        },
                        maxLines = 1,
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "email"
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.remove),
                                contentDescription = "Remove textFiled",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        state.email.value = ""
                                        emailInput = !emailInput
                                    },
                                // tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        )
                    )

                }

            }

            Spacer(Modifier.height(20.dp))

            //<--------------Taking  Address---------------------->

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                var addressInput by remember {
                    mutableStateOf(true)
                }
                if (state.address.value!=""){
                    addressInput=false
                }
                if (addressInput) {
                    if (state.address.value == "" || addressInput) {
                        OutlinedButton(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth(0.8f)
                                .padding(top = 8.dp),
                            onClick = {
                                addressInput = !addressInput
                            }
                        ) {
                            Text(text = "Add Address")
                        }
                    }
                } else {
                    OutlinedTextField(
                        value = state.address.value,
                        onValueChange = {
                            state.address.value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        label = {
                            Text(text = "Address")
                        },
                        placeholder = {
                            Text(text = "Enter Address")
                        },

                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Address"
                            )
                        },
                        trailingIcon = {

                            Icon(
                                painter = painterResource(R.drawable.remove),
                                contentDescription = "Remove textFiled",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        state.address.value = ""
                                        addressInput = !addressInput
                                    },
                                // tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                            )
                        },
                        maxLines = 5,
                    )


                }
            }


        }
    }
}