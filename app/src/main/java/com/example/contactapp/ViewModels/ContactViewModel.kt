package com.example.contactapp.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactapp.Data.database.Contact
import com.example.contactapp.Data.database.ContactAppDatabase
import com.example.contactapp.States.ContactState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(var database: ContactAppDatabase) : ViewModel() {

    private var isSortedByName = MutableStateFlow(true)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val contacts =  isSortedByName.flatMapLatest {
        if (it){
            database.getDao().getContactsSortedByName()
        }else{
            database.getDao().getContactsSortedByDate()
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = emptyList())

    val _state = MutableStateFlow(ContactState())

    val state = combine(_state,contacts,isSortedByName){_state,contacts,isSortedByName->
        _state.copy(
            contacts = contacts,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())

    fun saveContact(){
        val contact = Contact(
            id = _state.value.id.value,
            name = _state.value.name.value,
            number = _state.value.number.value,
            email = _state.value.email.value,
            dateOfCreation = System.currentTimeMillis().toLong(),
            isActive =  _state.value.isActive.value,
            isFavorite =  _state.value.isFavorite.value,
            isBlocked =  _state.value.isBlock.value,
            address = _state.value.address.value,
            image = _state.value.image.value

        )

        viewModelScope.launch(Dispatchers.IO) {
            database.getDao().upsertContact(contact)
        }

        _state.value.id.value=0
        _state.value.name.value=""
        _state.value.number.value=""
        _state.value.email.value=""
        _state.value.dateOfCreation.value=0
        _state.value.isActive.value=true
        _state.value.isFavorite.value=false
        _state.value.isBlock.value=false
        _state.value.address.value=""
        _state.value.image.value=ByteArray(0)


    }

    fun deleteContact(){
        val contact = Contact(
            id = _state.value.id.value,
            name = _state.value.name.value,
            number = _state.value.number.value,
            email = _state.value.email.value,
            dateOfCreation =  _state.value.dateOfCreation.value,
            isActive =  _state.value.isActive.value,
            isFavorite =  _state.value.isFavorite.value,
            isBlocked =  _state.value.isBlock.value,
            address = _state.value.address.value,
            image = _state.value.image.value

            )

        viewModelScope.launch(Dispatchers.IO) {
            database.getDao().deleteContact(contact=contact)
        }

        _state.value.id.value=0
        _state.value.name.value=""
        _state.value.number.value=""
        _state.value.email.value=""
        _state.value.dateOfCreation.value=0
        _state.value.isActive.value=true
        _state.value.isFavorite.value=false
        _state.value.isBlock.value=false
        _state.value.address.value=""
        _state.value.image.value=ByteArray(0)


    }

    fun doSortingByDate(){
        isSortedByName.value= false
    }
    fun doSortingByName(){
        isSortedByName.value= true
    }




}