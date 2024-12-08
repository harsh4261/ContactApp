package com.example.contactapp.States

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.contactapp.Data.database.Contact

data class ContactState(
    val contacts: List<Contact> = emptyList(),
    val id: MutableState<Int> = mutableIntStateOf(0),
    val name: MutableState<String> = mutableStateOf(""),
    val number: MutableState<String> = mutableStateOf(""),
    val email: MutableState<String> = mutableStateOf(""),
    val dateOfCreation: MutableState<Long> = mutableLongStateOf(0),
    val address: MutableState<String> = mutableStateOf(""),
    val isActive: MutableState<Boolean> = mutableStateOf(true),
    val isFavorite: MutableState<Boolean> = mutableStateOf(false),
    val isBlock: MutableState<Boolean> = mutableStateOf(false),
    val image: MutableState<ByteArray> = mutableStateOf(ByteArray(0))


)