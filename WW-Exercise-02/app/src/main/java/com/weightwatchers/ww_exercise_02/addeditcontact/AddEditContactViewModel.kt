package com.weightwatchers.ww_exercise_02.addeditcontact

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weightwatchers.ww_exercise_02.Event
import com.weightwatchers.ww_exercise_02.R
import com.weightwatchers.ww_exercise_02.model.Contact
import com.weightwatchers.ww_exercise_02.model.Result.Success
import com.weightwatchers.ww_exercise_02.model.source.ContactsRepository
import com.weightwatchers.ww_exercise_02.utils.isValidMsisdn
import kotlinx.coroutines.launch

class AddEditContactViewModel @ViewModelInject constructor(
        private val contactsRepository: ContactsRepository
) : ViewModel() {

    private val _contactId = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val name = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val msisdn = MutableLiveData<String>()

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _contactUpdatedEvent = MutableLiveData<Event<Unit>>()
    val contactUpdatedEvent: LiveData<Event<Unit>> = _contactUpdatedEvent

    private var isNewContact: Boolean = false


    fun start(msisdnId: String?) {

        _contactId.value = msisdnId

        if (msisdnId == null) {
            // No need to populate, it's a new contact
            isNewContact = true
            return
        }

        viewModelScope.launch {
            contactsRepository.observeContactById(msisdnId).let {
                if (it is Success) {
                    name.value = it.data.name
                    msisdn.value = it.data.msisdn
                } else {
                    Contact()
                }
            }
        }
    }


    // Called when clicking on fab.
    fun saveContact() {
        val currentName = name.value
        val currentMsisdn = msisdn.value

        if (currentName == null || currentMsisdn == null) {
            _snackbarText.value = Event(R.string.empty_contact_message)
            return
        }
        if (currentName.isEmpty() || currentMsisdn.isEmpty()) {
            _snackbarText.value = Event(R.string.empty_contact_message)
            return
        }

        if (!isValidMsisdn(currentMsisdn)) {
            _snackbarText.value = Event(R.string.invalid_msisdn_message)
            return
        }

        val currentContactId = _contactId.value
        if (isNewContact || currentContactId == null) {
            createContact(Contact(currentName, currentMsisdn))
        } else {
            val contact = Contact(currentName, currentMsisdn)
            updateContact(contact)
        }
    }

    private fun createContact(newContact: Contact) = viewModelScope.launch {
        contactsRepository.saveContact(newContact)
        _contactUpdatedEvent.value = Event(Unit)
    }

    private fun updateContact(contact: Contact) {
        if (isNewContact) {
            throw RuntimeException("updateContact() was called but contact is new.")
        }
        viewModelScope.launch {
            contactsRepository.updateContact(contact)
            _contactUpdatedEvent.value = Event(Unit)
        }
    }
}